package net.praqma.prqa.parsers;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule implements Serializable {

	private String ruleNumber;
	private int ruleTotalViolations;
	private static final Pattern DIGITS = Pattern.compile("\\d+");

	public Rule() {
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public void setRuleTotalViolations(int ruleTotalViolations) {
		this.ruleTotalViolations = ruleTotalViolations;
	}

	public String getRuleID() {
		return ruleNumber;
	}

	/**
	 * Returns the rule ID of the current Rule to be able to compare against a threshold.
	 * @return 0 if there is no number in the rule ID. Otherwise, return the first number in the rule ID.
     */
	public int getRuleNumber() {
		Matcher matcher = DIGITS.matcher(ruleNumber);
		return matcher.find() ? Integer.parseInt(matcher.group()) : 0;
	}

	public int getRuleTotalViolations() {
		return ruleTotalViolations;
	}

	@Override
	public int hashCode() {
		return (Integer.parseInt(ruleNumber) + 2) * 2 + ruleTotalViolations;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rule) {
			Rule ruleObj = (Rule) obj;
			if (ruleNumber == ruleObj.getRuleID() && ruleTotalViolations == ruleObj.getRuleTotalViolations())
				return true;
		}
		return false;
	}
}
