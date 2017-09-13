package net.praqma.prqa;

import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.Serializable;

public class QaFrameworkVersion implements Serializable {

    public static final String MINOR_SUPPORTED_VERSION = "2.2.0";

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

    public boolean isVersionSupported() {
        return qaFrameworkVersion.compareTo(new ComparableVersion(MINOR_SUPPORTED_VERSION)) > -1;
    }
}
