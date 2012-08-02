/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.jenkins.plugin.prqa.PrqaException;
import net.praqma.jenkins.plugin.prqa.PrqaException.PrqaUploadException;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.util.execute.CmdResult;

/**
 *
 * @author Praqma
 */
public class QAV extends PRQA {
    private String host;
    private String password;
    private String user;
    private String chosenSnapshotName;
    private String uploadProjectName;
    private String vcsXml;
    private boolean useSingleSnapshotMode;
    private Integer port;
    private String projectFile;
    private String product;
    private String importProgram;
    private String uploadProgram;
 
    public QAV(String host, String password, String user, Integer port, String chosenSnapshotName, String vcsXml, boolean useSingleSnapshotMode, String uploadProjectName, String projectFile, String product) {
        this.host = host;
        this.password = password;
        this.user = user;
        this.port = port;
        this.chosenSnapshotName = chosenSnapshotName;
        this.useSingleSnapshotMode = useSingleSnapshotMode;
        this.vcsXml = vcsXml;
        this.uploadProjectName = uploadProjectName;
        this.projectFile = projectFile;
        this.product = product;
    }
    
    public QAV() { }

    @Override
    public String getProductVersion() {
        return "Unknown version";
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the chosenSnapshotName
     */
    public String getChosenSnapshotName() {
        return chosenSnapshotName;
    }

    /**
     * @param chosenSnapshotName the chosenSnapshotName to set
     */
    public void setChosenSnapshotName(String chosenSnapshotName) {
        this.chosenSnapshotName = chosenSnapshotName;
    }

    /**
     * @return the vcsXml
     */
    public String getVcsXml() {
        return vcsXml;
    }

    /**
     * @param vcsXml the vcsXml to set
     */
    public void setVcsXml(String vcsXml) {
        this.vcsXml = vcsXml;
    }

    /**
     * @return the useSingleSnapshotMode
     */
    public boolean isUseSingleSnapshotMode() {
        return useSingleSnapshotMode;
    }

    /**
     * @param useSingleSnapshotMode the useSingleSnapshotMode to set
     */
    public void setUseSingleSnapshotMode(boolean useSingleSnapshotMode) {
        this.useSingleSnapshotMode = useSingleSnapshotMode;
    }
    
    public String qavUpload(String path) throws PrqaUploadException {
        String uploadOperation ="";

        String program = PRQACommandBuilder.wrapInQuotationMarks(uploadProgram);
        program+=" " + PRQACommandBuilder.getHost(host)+ " " +PRQACommandBuilder.getUser(user) + " " + PRQACommandBuilder.getPassword(password) + " " + PRQACommandBuilder.getProjectDatabase(uploadProjectName)+ " " + path + " " + PRQACommandBuilder.getSingle(useSingleSnapshotMode);       
        program+= " " + PRQACommandBuilder.getSnapshotName(chosenSnapshotName);
        uploadOperation+=" "+program;
        try {
            PRQACommandLineUtility.run(uploadOperation, new File(path));
        } catch (Exception ex) {
            throw new PrqaException.PrqaUploadException("Upload filed in upload phase!");
        }

        return uploadOperation;
    }
    
    public String qavImport(String path) throws PrqaException {
        String outpath = PRQACommandBuilder.getQavOutPathParameter(path);
        String importCommand ="QAW " + product +" "+ PRQACommandBuilder.getProjectFile(projectFile);
        
        String maseqSection = PRQACommandBuilder.escapeWhitespace(importProgram);//"C:\\Program\\ Files\\ (x86)\\PRQA\\QA\\ Verify\\ 1.3\\Client\\qaimport ";
        maseqSection += " "+"%Q %L+ -sop %D ";
        maseqSection += PRQACommandBuilder.getVcsXmlString(vcsXml);
        maseqSection += " " + PRQACommandBuilder.getCodeAll(); 
        maseqSection +=  " "+ outpath+" %P+";
        importCommand += " "+ PRQACommandBuilder.getMaseq(maseqSection, false);
        
        try {
             PRQACommandLineUtility.run(importCommand, new File(path));
        } catch (Exception ex) {
            throw new PrqaException.PrqaUploadException("Upload filed in import phase!");
        }

        return importCommand;
    }

    /**
     * @return the uploadProjectName
     */
    public String getUploadProjectName() {
        return uploadProjectName;
    }

    /**
     * @param uploadProjectName the uploadProjectName to set
     */
    public void setUploadProjectName(String uploadProjectName) {
        this.uploadProjectName = uploadProjectName;
    }

    /**
     * @return the projectFile
     */
    public String getProjectFile() {
        return projectFile;
    }

    /**
     * @param projectFile the projectFile to set
     */
    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the importProgram
     */
    public String getImportProgram() {
        return importProgram;
    }

    /**
     * @param importProgram the importProgram to set
     */
    public void setImportProgram(String importProgram) {
        this.importProgram = importProgram;
    }

    /**
     * @return the uploadProgram
     */
    public String getUploadProgram() {
        return uploadProgram;
    }

    /**
     * @param uploadProgram the uploadProgram to set
     */
    public void setUploadProgram(String uploadProgram) {
        this.uploadProgram = uploadProgram;
    }
}
