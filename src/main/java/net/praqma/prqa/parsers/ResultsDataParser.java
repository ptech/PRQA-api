package net.praqma.prqa.parsers;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ResultsDataParser implements Serializable{

    private String filePath;
    private int rootLevel = 1;
    private QaFrameworkVersion qaFrameworkVersion;

    public ResultsDataParser(String filePath) {
        this.filePath = filePath;
    }

    public void setQaFrameworkVersion(QaFrameworkVersion qaFrameworkVersion) {
        this.qaFrameworkVersion = qaFrameworkVersion;
    }

    public List<MessageGroup> parseResultsData() throws Exception {       
        FileInputStream fileis = new FileInputStream(filePath);

        // TODO: Dom Parsing - Temporary fix to read the xml file. will be replaced.
        if (qaFrameworkVersion.isVersionPriorTo104()) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(fileis);
            return beginFileParse(reader);
        } else {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fileis);
            return beginFileParsing(document);
        }
    }

    private Map<String, Integer> getMessages(Element node)
    {
        Map<String, Integer> map = new HashMap<>();
        NodeList nList = node.getElementsByTagName("Message");
        for (int i = 0; i < nList.getLength(); ++i) {
            Node message = nList.item(i);
            map.put(message.getAttributes().getNamedItem("guid").getNodeValue(), Integer.parseInt(message.getAttributes().getNamedItem("active").getNodeValue()));
        }
        return map;
    }

    private List<MessageGroup> beginFileParsing(Document document) throws Exception {
        List<MessageGroup> messagesGroups = new ArrayList<>();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("dataroot");
        for (int varDataRoot = 0; varDataRoot < nList.getLength(); varDataRoot++) {
            Node node = nList.item(varDataRoot);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getAttributes().getNamedItem("type").getNodeValue().equals("project")) {
                    Element pElement = (Element) node;
                    NodeList pNodeList = pElement.getElementsByTagName("tree");
                    for (int varTree = 0; varTree < pNodeList.getLength(); varTree++) {
                        Node cNode = pNodeList.item(varTree);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            if (cNode.getAttributes().getNamedItem("type").getNodeValue().equals("rules")) {
                                Element cElement = (Element) cNode;
                                NodeList cNodeList = cElement.getElementsByTagName("RuleGroup");
                                for (int varRGroup = 0; varRGroup < cNodeList.getLength(); varRGroup++) {
                                    Element ccNode = (Element) cNodeList.item(varRGroup);
                                    MessageGroup messageGroup = new MessageGroup(ccNode.getAttributes().getNamedItem("name").getNodeValue());
                                    NodeList ccNodeList = ccNode.getChildNodes();
                                    for (int varRule = 0; varRule < ccNodeList.getLength(); varRule++) {
                                        if (ccNodeList.item(varRule).getNodeType() != Node.ELEMENT_NODE){
                                            continue;
                                        }
                                        Element cccNode = (Element)ccNodeList.item(varRule);
                                        if (!"Rule".equals(cccNode.getTagName() )) {
                                            continue;
                                        }
                                        Rule violatedRule = new Rule(cccNode.getAttributes().getNamedItem("id").getNodeValue(), getMessages(cccNode));
                                        messageGroup.addViolatedRule(violatedRule);
                                    }
                                    messagesGroups.add(messageGroup);
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
        List<MessageGroup> messagesGroups = new ArrayList<>();

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
        return new MessageGroup(reader.getAttributeValue("", "name"));
    }

    private Rule createViolatedRule(XMLStreamReader reader) throws Exception {
        return new Rule(reader.getAttributeValue("", "data"), Integer.parseInt(reader.getAttributeValue("", "active")));
    }
}
