package net.praqma.prqa;

import net.praqma.prqa.utils.ComparableVersion;

import java.io.Serializable;

public class QaFrameworkVersion implements Serializable {

    private ComparableVersion qaFrameworkVersion;

    public QaFrameworkVersion(String qaFrameworkVersionString) {
        // example: "PRQA Framework version 2.2.0.9151-qax" will be cut to "2.2.0.9151-qax"
        String version = qaFrameworkVersionString.substring(qaFrameworkVersionString.lastIndexOf(" ") + 1).trim();
        this.qaFrameworkVersion = new ComparableVersion(version);
    }

    public String getQaFrameworkVersion() {
        return qaFrameworkVersion.toString();
    }

    public String getQaFrameworkShortVersion() {
        String value = qaFrameworkVersion.toString();
        return value.length() > 5 ? value.substring(0, 5) : value;
    }

    public boolean isVersionPriorTo104() {
        return qaFrameworkVersion.compareTo(new ComparableVersion("1.0.4")) < 0;
    }

    public boolean isVersionPriorTo210() {
        return qaFrameworkVersion.compareTo(new ComparableVersion("2.1.0")) < 0;
    }

    public boolean isVersionSupported() {
        return qaFrameworkVersion != null &&
                qaFrameworkVersion.compareTo(new ComparableVersion("1.0.0")) > 0;
    }
}
