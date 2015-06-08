package net.praqma.prqa.parsers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import net.praqma.prqa.QaFrameworkVersion;

public class ResultsDataParser {

	private String filePath;
	private int rootLevel = 1;
    private int count = 0;
    private QaFrameworkVersion qaFrameworkVersion;

	public ResultsDataParser(String filePath) {
		this.filePath = filePath;
	}
    public void setQaFrameworkVersion(QaFrameworkVersion qaFrameworkVersion) {
        this.qaFrameworkVersion = qaFrameworkVersion;
    }

	public List<MessageGroup> parseResultsData() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream fileis = new FileInputStream(filePath);
        XMLStreamReader reader = factory.createXMLStreamReader(fileis);
		return beginFileParse(reader);
	}
 
    public int getdiagnosticCount() throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream fileis = new FileInputStream(filePath);
        XMLStreamReader reader = factory.createXMLStreamReader(fileis);
        return diagnosticCount(reader);
    }
    private int diagnosticCount(XMLStreamReader reader) throws Exception {
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.END_DOCUMENT) {
                reader.close();
                /*                break;*/
            }
            else if (event == XMLStreamConstants.START_ELEMENT) {
                if ((reader.getLocalName().equals("Folder")) && (reader.getAttributeValue(0).equals("NoInfo"))) {
                    count += Integer.parseInt(reader.getAttributeValue(3));
				}
			}
		}
		return count;
    }

	private List<MessageGroup> beginFileParse(XMLStreamReader reader) throws Exception {
		List<MessageGroup> messagesGroups = new ArrayList<MessageGroup>();
		while (reader.hasNext()) {
			int event = reader.next();
            
            if (event == XMLStreamConstants.START_ELEMENT) {
                if ((reader.getLocalName().equals("dataroot")) && (reader.getAttributeValue(0).equals("project"))) {
                    parseProjectDataroot(reader, messagesGroups);
                    return messagesGroups;
				}
			}
            else if (event == XMLStreamConstants.END_ELEMENT) {
                reader.close();
                break;
            }
		}
		return messagesGroups;
	}

	private void parseProjectDataroot(XMLStreamReader reader, List<MessageGroup> messagesGroups) throws Exception {
        boolean PRIOR_QAF104 = (qaFrameworkVersion.isQaFrameworkVersionPriorToVersion4());
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (reader.getLocalName().equals("tree") && (reader.getAttributeValue(0).equals("rules"))) {
                    if (PRIOR_QAF104 == false)
                    {
                        parseRuleGroup(reader, messagesGroups);
                    }
                    else
                    {
                        parseRulesTree(reader, messagesGroups);
                    }
                    return;
				}
            }
            else if (event == XMLStreamConstants.END_DOCUMENT) {
                if (rootLevel != 1) {
                    return;
                    }
                reader.close();
            }
		}
	}

	private List<MessageGroup> parseRulesTree(XMLStreamReader reader, List<MessageGroup> messagesGroups) throws Exception {
		MessageGroup messageGroup;
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
				if ((reader.getLocalName().equals("item")) && (reader.getAttributeValue(0).equals("FolderItem")) && rootLevel == 1) {
					rootLevel++;
					messageGroup = createMessageGroup(reader);
					parseViolatedRules(reader, messageGroup);
					messagesGroups.add(messageGroup);
				}
            }
            else if (event == XMLStreamConstants.END_DOCUMENT) {
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
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (rootLevel == 2) {
                    messageGroup.addViolatedRule(createViolatedRule(reader));
				}
				rootLevel++;
            }	
            else if (event == XMLStreamConstants.END_DOCUMENT) {
				rootLevel--;
				if (rootLevel == 1) {
					return;
				}
				//break;
			}
		}
	}
    
	private MessageGroup createMessageGroup(XMLStreamReader reader) throws Exception {
		MessageGroup messageGroup = new MessageGroup();
		int attributeCount = reader.getAttributeCount();
		QName name;
		for (int i = 0; i < attributeCount; i++) {
			name = reader.getAttributeName(i);
			if ((name.getLocalPart().equals("active"))) {
				messageGroup.setTotalViolations(Integer.parseInt(reader.getAttributeValue(i)));
			}
			if ((name.getLocalPart().equals("name"))) {
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
			if ((name.getLocalPart().equals("active"))) {
				violatedRule.setRuleTotalViolations((Integer.parseInt(reader.getAttributeValue(i))));
			}
			if ((name.getLocalPart().equals("data"))) {
				violatedRule.setRuleNumber(Integer.parseInt(reader.getAttributeValue(i)));
			}
		}
		return violatedRule;
	}
    	private List<MessageGroup> parseRuleGroup(XMLStreamReader reader, List<MessageGroup> messagesGroups) throws Exception {
		MessageGroup messageGroup = new MessageGroup();
		QName name;
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
				if ((reader.getLocalName().equals("RuleGroup"))) {
                    int attributeCount = reader.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        name = reader.getAttributeName(i);
                        if ((name.getLocalPart().equals("active"))) {
                            messageGroup.setTotalViolations(Integer.parseInt(reader.getAttributeValue(i)));
                        }
                        if ((name.getLocalPart().equals("name"))) {
                            messageGroup.setMessageGroupName(reader.getAttributeValue(i));
                        }
                    }                    
					messagesGroups.add(messageGroup);
                    //parseRule(reader, messagesGroups);
				}
            }
            else if (event == XMLStreamConstants.END_ELEMENT) {
                    reader.close();
            }
        }
        return messagesGroups;
        }
        
        
        private void parseRuleViolation(XMLStreamReader reader, MessageGroup messageGroup) throws Exception {
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (rootLevel == 2) {
                    messageGroup.addViolatedRule(newViolatedRule(reader));
				}
				rootLevel++;
            }	
            else if (event == XMLStreamConstants.END_ELEMENT) {
				rootLevel--;
				if (rootLevel == 1) {
					return;
				}
				//break;
			}
		}
	}
            
	private Rule newViolatedRule(XMLStreamReader reader) throws Exception {
		Rule violatedRule = new Rule();
 		QName name;
		while (reader.hasNext()) {
			int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
				if ((reader.getLocalName().equals("Rule"))) {
                    int attributeCount = reader.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        name = reader.getAttributeName(i);
                        if ((name.getLocalPart().equals("active"))) {
                            violatedRule.setRuleTotalViolations(Integer.parseInt(reader.getAttributeValue(i)));
                        }
                        if ((name.getLocalPart().equals("id"))) {
                            violatedRule.setRuleNumber(Integer.parseInt(reader.getAttributeValue(i)));
                        }
                    }
				}
            }
        }
        return violatedRule;
        }        

}
