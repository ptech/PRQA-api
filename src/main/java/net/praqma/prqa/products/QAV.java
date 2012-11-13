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
    
    private String qavUpload(String importPartCommand, String path, boolean cmaEnabled) throws PrqaException {
        logger.entering(this.getClass().getSimpleName(), "qavUpload(String importPartCommand, String path, boolean addSfba)", new Object[] { importPartCommand,path});
        String command = "qaw "+getProduct()+ " "+PRQACommandBuilder.wrapInQuotationMarks(getProjectFile());
        command += " "+ PRQACommandBuilder.getSfbaOption(true)+" ";
        command += " " + PRQACommandBuilder.getDependencyModeParameter(true) + " ";
        String uploadPartCommand = "#";
        uploadPartCommand +="upload %P+ " + "-prqavcs "+PRQACommandBuilder.wrapInEscapedQuotationMarks(getVcsXml());
        uploadPartCommand +=" "+PRQACommandBuilder.getHost(host);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getPort(port);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getUser(user);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getPassword(password);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getProjectDatabase(uploadProjectName);
        
        uploadPartCommand +=" "+PRQACommandBuilder.getProd(useSingleSnapshotMode);
               
        uploadPartCommand +=" "+PRQACommandBuilder.getLogFilePathParameter(path+Config.QAV_UPLOAD_LOG);
        
        uploadPartCommand +=" "+PRQACommandBuilder.wrapInEscapedQuotationMarks(path);
                
        String commandfinal = command + PRQACommandBuilder.getMaseq(PRQACommandBuilder.getCrossModuleAnalysisParameter(cmaEnabled)+importPartCommand+uploadPartCommand);
        return commandfinal;
    }
    public boolean generateUpload(String command, String path) throws PrqaUploadException {
        boolean res = false;
        
        try {
            logger.finest(String.format("QAV upload path argument: %s", path));
            logger.finest(String.format("QAV upload command: ", command));
            PRQACommandLineUtility.run(command, new File(path));
        } catch (AbnormalProcessTerminationException abnormex) {
            logger.finest(String.format("Failed QAV Upload with AbnormalProcessTerminationException"));
            logger.finest(abnormex.toString());
            throw new PrqaUploadException("Failed QAV upload", abnormex);
        } catch (CommandLineException cliex) {
            logger.finest("Failed QAV Upload with CommandLineException");
            logger.finest(cliex.toString());
            throw new PrqaUploadException("Failed QAV upload", cliex);
        } catch (Exception ex) {
            logger.finest("Failed QAV Upload with Exception");
            logger.finest(ex.toString());
            throw new PrqaUploadException("Failed QAV upload", ex);
        }
        
        return res;
    }
    
    public String qavUpload(String path, boolean cmaEnabled) throws PrqaException {
        logger.finest(String.format("In method qavUpload(String path) called with parameter %s", path));
        String uploadOperation ="";
        uploadOperation = qavUpload(qavImport(path), path, cmaEnabled);
        return uploadOperation;
    }
    
    private String qavImport(String path) throws PrqaException {
        logger.entering(this.getClass().getName(), "qavImport", path);
    
        /**
         * Construct the import part of the command. If sourceTopLevelDir is left blank, Jenkins workspace root is used in the command.
         */
        
        
        String maseqSection = PRQACommandBuilder.escapeWhitespace("qaimport");
        
        int availableProcessors = Runtime.getRuntime().availableProcessors(); 
        
        maseqSection += " "+"%Q %P+ %L+ "+PRQACommandBuilder.getNumberOfThreads(availableProcessors)+" "+PRQACommandBuilder.getSop(StringUtils.isBlank(sourceTopLevelDir) ? path : sourceTopLevelDir) + " ";
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
        logger.entering(this.getClass().toString(), "setUploadProjectName(String uploadProjectName)", uploadProjectName);
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
        logger.entering(this.getClass().toString(), "setProjectFile(String projectFile)", projectFile);
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
        logger.entering(this.getClass().toString(), "setProduct(String product)", product);
        this.product = product;
        logger.exiting(this.getClass().toString(), "setProduct(String product)");
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
