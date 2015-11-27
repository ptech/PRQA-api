package net.praqma.prqa.parsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageGroup implements Serializable {

	private String messageGroupName;
	private int totalViolations;
	private List<Rule> violatedRules;
	private int messagesWithinTreshold;

	public MessageGroup() {
		this.violatedRules  =new ArrayList<Rule>();
	}

	public String getMessageGroupName() {
		return messageGroupName;
	}

	public int getTotalViolations() {
		return totalViolations;
	}

	public void setMessageGroupName(String messageGroupName) {
		this.messageGroupName = messageGroupName;
	}

	public void setTotalViolations(int totalViolations) {
		this.totalViolations = totalViolations;
	}

	public void setViolatedRules(List<Rule> violatedRules) {
		this.violatedRules = violatedRules;
	}

	public List<Rule> getViolatedRules() {
		return violatedRules;
	}

	public void addViolatedRule(Rule rule) {
		this.violatedRules.add(rule);
	}

	public int getMessagesWithinTreshold() {
		return messagesWithinTreshold;
	}

	public void setMessagesWithinTreshold(int messagesWithinTreshold) {
		this.messagesWithinTreshold = messagesWithinTreshold;
	}
}
