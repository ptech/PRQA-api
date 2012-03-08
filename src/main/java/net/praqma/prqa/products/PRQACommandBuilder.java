/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.Serializable;
import java.util.LinkedList;

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
    
    /**
     * Embedding QAR using QAW. Analysis will be performed and the analysis results used as a base for our report. 
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
    
    public static String getProduct(String product) {
        return product;
    }
}
