package net.praqma.prqa.parsers;

public class Rule {

	private int ruleNumber;
	private int ruleTotalViolations;

	public Rule() {
	}

	public void setRuleNumber(int ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public void setRuleTotalViolations(int ruleTotalViolations) {
		this.ruleTotalViolations = ruleTotalViolations;
	}

	public int getRuleID() {
		return ruleNumber;
	}

	public int getRuleTotalViolations() {
		return ruleTotalViolations;
	}

	@Override
	public int hashCode() {
		return (ruleNumber + 2) * 2 + ruleTotalViolations;
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
