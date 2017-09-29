package net.praqma.prqa;

import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.Serializable;

public class QaFrameworkVersion
        implements Serializable {

    public static final String MINIMUM_SUPPORTED_VERSION = "2.2.0";

    private String qaFrameworkVersion;

    public QaFrameworkVersion(String qaFrameworkVersionString) {
        // example: "PRQA Framework version 2.2.0.9151-qax" will be cut to "2.2.0.9151-qax"
        this.qaFrameworkVersion = qaFrameworkVersionString.
                                                                  substring(
                                                                          qaFrameworkVersionString.lastIndexOf(" ") + 1)
                                                          .trim();
    }

    public String getQaFrameworkVersion() {
        return qaFrameworkVersion;
    }

    public boolean isVersionSupported() {
        return new ComparableVersion(qaFrameworkVersion).compareTo(
                new ComparableVersion(MINIMUM_SUPPORTED_VERSION)) > -1;
    }
}
