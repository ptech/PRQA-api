package net.praqma.prqa;

import java.io.PrintStream;

public class QaFrameworkVersion {

	private String qaFrameworkVersion;

	public QaFrameworkVersion(String qaFrameworkVersion) {
		this.qaFrameworkVersion = qaFrameworkVersion;
	}

	public String getQaFrameworkVersion() {
		return qaFrameworkVersion;
	}

	public boolean isQAFVersionSupported() {
		if (qaFrameworkVersion == null || qaFrameworkVersion.length() == 0) {
			return false;
		}
		return !isAnOlderVersionThanSupported();
	}

	private boolean isAnOlderVersionThanSupported() {

		String shortVersion = getVersionShortFormat();
		String lockedVersion = "1.0.0";
		boolean isAnOlderVersion = false;
		if (shortVersion.equals(lockedVersion)) {
			isAnOlderVersion = true;
		}
		return isAnOlderVersion;
	}

	public boolean isQaFrameworkVersionPriorToVersion4() {

		String shortVersion = getVersionShortFormat();
		if (Integer.parseInt(shortVersion.substring(shortVersion.lastIndexOf(".") + 1, shortVersion.length())) < 4) {
//			"Is before 0.4: ";
			return true;
		}
		return false;
	}

	private String getVersionShortFormat() {
		return qaFrameworkVersion.substring(0, qaFrameworkVersion.lastIndexOf("."));
	}
}
