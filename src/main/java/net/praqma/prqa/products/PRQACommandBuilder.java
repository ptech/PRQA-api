/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.Serializable;
import java.util.LinkedList;
import net.praqma.prqa.PRQA;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Praqma
 */
public class PRQACommandBuilder implements Serializable{
    private String executable;
    private LinkedList<String> arguments = new LinkedList<String>();
      
    public PRQACommandBuilder(String executable) {
        this.executable = executable;
    }
    
    public PRQACommandBuilder appendArgument(String argument) {
        arguments.addLast(argument);
        return this;
    }
    
    public PRQACommandBuilder prependArgument(String argument) {
        arguments.addFirst(argument);
        return this;
    }
    
    public String getCommand() {
        String out = "";
        out+=executable + " ";
        
        for(String s : arguments) {
            out+= s+ " ";
        }       
        return out;       
    }
     
    public static String getCmaf(String path, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format("-cmaf \"%s\"", path.replace(" ", "\\ "));
        return String.format("-cmaf \"%s\"", path.replace(" ", " "));
    }
    
    public static String getCmaf(String path) {
        return PRQACommandBuilder.getCmaf(path, false);
    }
    
    public static String getMaseq(String commandSequence, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format(commandSequence.replace(" ", "\\ "), commandSequence);
        return String.format("-maseq \"%s\"", commandSequence);
    }
    
    public static String getVcsXmlString(String vcsXmlPath) {
        return String.format("-po qav::prqavcs=\\\"%s\\\"", vcsXmlPath);
    }
    
    /**
     * Embedding QAR using QAW. Analysis will be performed and the analysis results used as a base for our report.
     * 
     * Constructs a valid secondary analysis command parameter for PRQA. Use this to wrap all your secondary analysis commands.  
     */ 
    public static String getMaseq(String commmandSequence) {
        return String.format("-maseq \"%s\"", commmandSequence);
    }
    
    public static String getReportFormatParameter(String reportFormat) {
        return PRQACommandBuilder.getReportFormatParameter(reportFormat,false);
    }
    
    public static String getReportFormatParameter(String reportFormat, boolean escapeinInputParameterWhiteSpace) {
        if(escapeinInputParameterWhiteSpace)
            return String.format("-po qar::report_format=%s", reportFormat.replace(" ", "\\ "));
        return String.format("-po qar::report_format=%s", reportFormat);
    }
    
    public static String getReportTypeParameter(String reportType) {
        return String.format("-po qar::report_type=%s\\ Report", reportType);
    }
    
    public static String getReportTypeParameter(String reportType, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format("-po qar::report_type=%s\\ Report", reportType.replace(" ", "\\ "));
        return String.format("-po qar::report_type=%s\\ Report", reportType);
    }
    
    public static String getOutputPathParameter(String outpath) {
        return PRQACommandBuilder.getOutputPathParameter(outpath, false);        
    }
    
    public static String getOutputPathParameter(String outpath, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format("-po qar::output_path=%s", outpath.replace(" ", "\\ "));        
        return String.format("-po qar::output_path=%s", outpath);        
    }
    
    public static String getQavOutPathParameter(String outpath) {
        return getQavOutPathParameter(outpath, false);
    }
    
    public static String getQavOutPathParameter(String outpath, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format("-po qav::outputh=%s", outpath.replace(" ", "\\ "));        
        return String.format("-po qav::output=%s", outpath);    
    }
    
    /**
     * Uses the project names as specfied in the project file
     * @return 
     */
    public static String getProjectName() {
        return "-po qar::project_name=%J";
    }
    
    public static String getViewingProgram(String program) {
        return PRQACommandBuilder.getViewingProgram(program, false);
    }
    
    public static String getViewingProgram(String program, boolean escapeInputParameterWhiteSpace) {
        if(escapeInputParameterWhiteSpace)
            return String.format("-po qar::viewing_program=%s", program.replace(" ", "\\ "));
        return  String.format("-po qar::viewing_program=%s",program);
    }
    
    public static String getListReportFiles(String list){
        return String.format("-list \"%s\"", list);
    }
    
    public static String getProjectFile(String file) {
        return String.format("\"%s\"", file);
    }

    public static String getProduct(PRQA product) {
        return product.toString();
    }
    
    public static String getHost(String hostname) {
        return String.format("-host %s", hostname);
    }
    
    public static String getUser(String user) {
        return String.format("-user %s", user);
    }
    
    public static String getPassword(String password) {
        return String.format("-pass %s", password);
    }
    
    public static String getProjectDatabase(String databaseName) {
        return String.format("-db %s", databaseName);
    }
    
    public static String getSingle(boolean useSingleSnapshotMode) {
        if(useSingleSnapshotMode) 
            return "-single";
        return "";
    }
    
    public static String getSnapshotName(String snapshotName) {
        if(StringUtils.isNotBlank(snapshotName)) {
            return String.format("-ssname %s", snapshotName);
        }
        return "";
    }
    
    public static String getCodeAll() {
        return "-po qav::code=all";
    }
    
    /**
     * 
     * @param string
     * @return 
     */
    
    public static String escapeWhitespace(String string) {
        return String.format("%s", string.replace(" ", "\\ "));
    }
    
    /**
     * 
     * @param string
     * @return 
     */
    
    public static String wrapInQuotationMarks(String string) {
        return String.format("\""+"%s"+"\"", string);
    }
}
