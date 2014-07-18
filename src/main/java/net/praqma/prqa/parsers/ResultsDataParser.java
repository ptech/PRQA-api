package net.praqma.prqa.parsers;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class ResultsDataParser {

	private String filePath;
	private int rootLevel = 1;

	public ResultsDataParser(String filePath) {
		this.filePath = filePath;
	}

	public List<MessageGroup> parseResultsData() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));
		return beginFileParse(reader);
	}

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
				violatedRule.setRuleNumber(Integer.parseInt(reader.getAttributeValue(i)));
			}
		}
		return violatedRule;
	}
}
