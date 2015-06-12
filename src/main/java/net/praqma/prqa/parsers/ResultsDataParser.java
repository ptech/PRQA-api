package net.praqma.prqa.parsers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import net.praqma.prqa.QaFrameworkVersion;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ResultsDataParser {

	private final String filePath;
	private int rootLevel = 1;
    /* private int count = 0; */
    private QaFrameworkVersion qaFrameworkVersion;
    

	public ResultsDataParser(String filePath) {
		this.filePath = filePath;
	}
    public void setQaFrameworkVersion(QaFrameworkVersion qaFrameworkVersion) {
        this.qaFrameworkVersion = qaFrameworkVersion;
    }

	public List<MessageGroup> parseResultsData() throws Exception {
        boolean PRIOR_QAF104 = (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion4());
		
        FileInputStream fileis = new FileInputStream(filePath);
        
        /**
         * Dom Parsing - Temporary fix to read the xml file. will be replaced.
         */
         
        if (PRIOR_QAF104 == true) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(fileis);
            return beginFileParse(reader);
        }
        else {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileis);
            return beginFileParsing(document);
        }
	}
    
    /*This method is to return count.
    public int getdiagnosticCount() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        FileInputStream inputFile = new FileInputStream(filePath);
        Document doc = dBuilder.parse(inputFile);
        return diagnosticCount(doc);
    }
    private int diagnosticCount(Document document) throws Exception {
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Folder");
        if ("NoInfo".equals(nList.item(0).getAttributes().getNamedItem("status").getNodeValue()))
        {  
            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node node = nList.item(temp);
                if (node instanceof Element) {
                    count += Integer.parseInt(node.getAttributes().getNamedItem("active").getNodeValue());
                }
            }
        }
		return count;
    }
    */
    
    private List<MessageGroup> beginFileParsing(Document document) throws Exception {
        List<MessageGroup> messagesGroups = new ArrayList<MessageGroup>();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("dataroot");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if ( node.getNodeType() == Node.ELEMENT_NODE ){
                Element pElement = (Element) node;
                NodeList pNodeList = pElement.getElementsByTagName("tree");
                for (temp = 0; temp < pNodeList.getLength(); temp++) {
                    Node cNode = pNodeList.item(temp);
                    if ( cNode.getNodeType() == Node.ELEMENT_NODE ){
                        if(cNode.getAttributes().getNamedItem("type").getNodeValue().equals("rules")){
                            Element cElement = (Element) cNode;
                            NodeList cNodeList = cElement.getElementsByTagName("RuleGroup");
                            for (temp = 0; temp < cNodeList.getLength(); temp++) {
                                Node ccNode = cNodeList.item(temp);
                                MessageGroup messageGroup = new MessageGroup();
                                messageGroup.setTotalViolations(Integer.parseInt(ccNode.getAttributes().getNamedItem("active").getNodeValue()));
                                messageGroup.setMessageGroupName(ccNode.getAttributes().getNamedItem("name").getNodeValue());
                                messagesGroups.add(messageGroup);
                                Element ccElement = (Element) ccNode;
                                NodeList ccNodeList = ccElement.getChildNodes();
                                for (temp = 0; temp < ccNodeList.getLength(); temp++){
                                    if (ccNodeList.item(temp).getNodeType() == Node.ELEMENT_NODE){
                                        Node cccNode = ccNodeList.item(temp);
                                        Rule violatedRule = new Rule();
                                        violatedRule.setRuleTotalViolations((Integer.parseInt(cccNode.getAttributes().getNamedItem("active").getNodeValue())));
                                        violatedRule.setRuleNumber(cccNode.getAttributes().getNamedItem("id").getNodeValue());
                                        messageGroup.addViolatedRule(violatedRule);                                       
                                    } 
                                }
                            }
                        }
                    }
                }
            } 
        }
        return messagesGroups;
    }
    ///////////////////////////////OLD FRAMEWORK////////////////////////////////////////////
		private List<MessageGroup> beginFileParse(XMLStreamReader reader) throws Exception {
		List<MessageGroup> messagesGroups = new ArrayList<MessageGroup>();
        
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			// parse only <dataroot type="project">
			case XMLStreamConstants.START_ELEMENT:
				if ("dataroot".equals(reader.getLocalName()) && "project".equals(reader.getAttributeValue(0))) {
					parseProjectDataroot(reader, messagesGroups);
					return messagesGroups;
				}
			}
		}
		return messagesGroups;
	}

	private void parseProjectDataroot(XMLStreamReader reader, List<MessageGroup> messagesGroups) throws Exception {
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			// parse only <tree type="rules">
			case XMLStreamConstants.START_ELEMENT:
				if ("tree".equals(reader.getLocalName()) && "rules".equals(reader.getAttributeValue(0))) {
					parseRulesTree(reader, messagesGroups);
					return;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (rootLevel != 1) {
					return;
				}
				break;
			}
		}
	}

	private List<MessageGroup> parseRulesTree(XMLStreamReader reader, List<MessageGroup> messagesGroups) throws Exception {
		MessageGroup messageGroup;
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			// Each "Message Groups" is a ROOT LEVEL 1. Any child has a higher
			// level
			// Start of ROOT LEVEL 1
			case XMLStreamConstants.START_ELEMENT:
				if ("item".equals(reader.getLocalName()) && "FolderItem".equals(reader.getAttributeValue(0)) && rootLevel == 1) {
					rootLevel++;
					messageGroup = createMessageGroup(reader);
					parseViolatedRules(reader, messageGroup);
					messagesGroups.add(messageGroup);
				}
				break;
			// END OF ROOT LEVEL 1
			case XMLStreamConstants.END_ELEMENT:

				if (rootLevel == 1) {
					return messagesGroups;
				}
				rootLevel--;
				break;
			}
		}
		return messagesGroups;
	}

	private void parseViolatedRules(XMLStreamReader reader, MessageGroup messageGroup) throws Exception {
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if (rootLevel == 2) {
					messageGroup.addViolatedRule(createViolatedRule(reader));
				}
				rootLevel++;
				break;
			case XMLStreamConstants.END_ELEMENT:
				rootLevel--;
				if (rootLevel == 1) {
					return;
				}
				break;
			}
		}

	}

	private MessageGroup createMessageGroup(XMLStreamReader reader) throws Exception {
		MessageGroup messageGroup = new MessageGroup();
		int attributeCount = reader.getAttributeCount();
		QName name;
		for (int i = 0; i < attributeCount; i++) {
			name = reader.getAttributeName(i);
			if ("active".equals(name.getLocalPart())) {
				messageGroup.setTotalViolations(Integer.parseInt(reader.getAttributeValue(i)));
			}
			if ("name".equals(name.getLocalPart())) {
				messageGroup.setMessageGroupName(reader.getAttributeValue(i));
			}
		}
		return messageGroup;

	}

	private Rule createViolatedRule(XMLStreamReader reader) throws Exception {
		Rule violatedRule = new Rule();
		int attributeCount = reader.getAttributeCount();
		QName name;
		for (int i = 0; i < attributeCount; i++) {
			name = reader.getAttributeName(i);
			if ("active".equals(name.getLocalPart())) {
				violatedRule.setRuleTotalViolations((Integer.parseInt(reader.getAttributeValue(i))));
			}
			if ("data".equals(name.getLocalPart())) {
				violatedRule.setRuleNumber(reader.getAttributeValue(i));
			}
		}
		return violatedRule;
	}
}