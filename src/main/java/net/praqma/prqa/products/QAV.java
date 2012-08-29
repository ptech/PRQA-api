/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prga.excetions.PrqaUploadException;
import net.praqma.prqa.CodeUploadSetting;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.logging.Config;
import net.praqma.util.execute.AbnormalProcessTerminationException;
import net.praqma.util.execute.CommandLineException;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Praqma
 */
public class QAV extends PRQA {
    private String host;
    private String password;
    private String user;
    private String uploadProjectName;
    private String vcsXml;
    private boolean useSingleSnapshotMode;
    private Integer port;
    private String projectFile;
    private String product;
    private String sourceTopLevelDir;
    private CodeUploadSetting codeUploadSettings;
    
    
    public QAV(String host, String password, String user, Integer port, String vcsXml, boolean useSingleSnapshotMode, 
            String uploadProjectName, String projectFile, String product, CodeUploadSetting codeUploadSettings, String sourceTopLevelDir) {
        this.host = host;
        this.password = password;
        this.user = user;
        this.port = port;
        this.useSingleSnapshotMode = useSingleSnapshotMode;
        this.vcsXml = vcsXml;
        this.uploadProjectName = uploadProjectName;
        this.projectFile = projectFile;
        this.product = product;        
        this.codeUploadSettings = codeUploadSettings;
        this.sourceTopLevelDir = sourceTopLevelDir;
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
        logger.finest(String.format("In method setHost(String host) called with parameter %s",host));
        
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
        logger.finest(String.format("In method setPassword(String password) called with parameter %s",password));
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
        logger.finest(String.format("In method setPort(Integer port) called with parameter %s",port));
        this.port = port;
        logger.exiting(this.getClass().toString(), "setPort");
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
        logger.finest(String.format("In method setVcsXml(String vcsXml) called with parameter %s", vcsXml));
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
        logger.finest(String.format("In method setUseSingleSnapshotMode(boolean useSingleSnapshotMode) called with parameter %s", useSingleSnapshotMode));
        this.useSingleSnapshotMode = useSingleSnapshotMode;
    }
    
    private String qavUpload(String importPartCommand, String path, boolean addSfba) throws PrqaException {        
        String command = "qaw "+getProduct()+ " "+PRQACommandBuilder.wrapInQuotationMarks(getProjectFile());
        command += " "+ PRQACommandBuilder.getSfbaOption(addSfba)+" ";
        String uploadPartCommand = "#";
        uploadPartCommand +="upload %P+ " + "-prqavcs "+PRQACommandBuilder.wrapInEscapedQuotationMarks(getVcsXml());
        uploadPartCommand +=" "+PRQACommandBuilder.getHost(host);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getPort(port);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getUser(user);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getPassword(password);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getProjectDatabase(uploadProjectName);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getProd(useSingleSnapshotMode);
               
        uploadPartCommand +=" "+PRQACommandBuilder.getLogFilePathParameter(path+Config.QAV_UPLOAD_LOG);
        
        uploadPartCommand +=" "+path;
        
        //Final command. This is the one to execute
        String commandfinal = command + PRQACommandBuilder.getMaseq(importPartCommand+uploadPartCommand); 
        try {
            logger.finest(String.format("QAV upload path argument: %s", path));
            logger.finest(String.format("QAV upload command: ", commandfinal));
            PRQACommandLineUtility.run(commandfinal, new File(path));
        } catch (AbnormalProcessTerminationException abnormex) {
            logger.finest(String.format("Failed QAV Upload with exception"));
            logger.finest(abnormex.toString());
            throw new PrqaUploadException("Failed QAV upload", abnormex);
        } catch (CommandLineException cliex) {
            logger.finest("Failed QAV Upload with exception");
            logger.finest(cliex.toString());
            throw new PrqaUploadException("Failed QAV upload", cliex);
        }

        return commandfinal;
    }
    
    public String qavUpload(String path, boolean skip) throws PrqaException {
        logger.finest(String.format("In method qavUpload(String path) called with parameter %s", path));
        String uploadOperation ="";
        try {
            uploadOperation = qavUpload(qavImport(path), path, skip);
        } catch (PrqaException ex) {
            logger.finest("qavUpload(path, skip) caught exception with cause (Propagating exception): "+ ex.getCause() != null ? ex.getCause().toString() : "no cause specified");
            throw ex;
        }
        
        return uploadOperation;
    }
    
    private String qavImport(String path) throws PrqaException {
        logger.entering(this.getClass().getName(), "qavImport", path);
        String outpath = PRQACommandBuilder.getQavOutPathParameter(path);
        
        /**
         * Construct the import part of the command. If sourceTopLevelDir is left blank, Jenkins workspace root is used in the command.
         */
        String maseqSection = PRQACommandBuilder.escapeWhitespace("qaimport.exe");
        maseqSection += " "+"%Q &P+ %L+ "+PRQACommandBuilder.getNumberOfThreads(3)+" "+PRQACommandBuilder.getSop(StringUtils.isBlank(sourceTopLevelDir) ? path : sourceTopLevelDir) + " ";
        maseqSection += PRQACommandBuilder.getPrqaVcs(codeUploadSettings, vcsXml)+" ";
        maseqSection += PRQACommandBuilder.getQavOutPathParameter(path)+" ";
        maseqSection += PRQACommandBuilder.getImportLogFilePathParameter(path+Config.QAV_IMPORT_LOG)+" ";
        maseqSection += PRQACommandBuilder.getCodeAll(codeUploadSettings);
        
        logger.exiting(this.getClass().toString(), "qavImport", maseqSection);

        return maseqSection;
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
        logger.entering(this.getClass().toString(), "setUploadProjectName", uploadProjectName);
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
        logger.entering(this.getClass().toString(), "setProjectFile", projectFile);
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
        logger.entering(this.getClass().toString(), "setProduct", product);
        this.product = product;
        logger.exiting(this.getClass().toString(), "setProduct");
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
     * @return the sourceTopLevelDir
     */
    public String getSourceTopLevelDir() {
        return sourceTopLevelDir;
    }

    /**
     * @param sourceTopLevelDir the sourceTopLevelDir to set
     */
    public void setSourceTopLevelDir(String sourceTopLevelDir) {
        this.sourceTopLevelDir = sourceTopLevelDir;
    }
}
