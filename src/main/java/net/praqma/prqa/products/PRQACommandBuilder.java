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
      
    public static String getCmaf(String path) {
        return String.format("-cmaf \"%s\"", path);
    }
    
    public static String getReportTypeParameter(String reportType) {
        return String.format("-po qar::report_type=\"%s Report\"", reportType);
    }
    
    public static String getOutputPathParameter(String outpath) {
        return String.format("-po qar::output_path=\"%s\"", outpath);        
    }
    
    public static String getUserReportsDirectoryDirParameter(String dir) {
        return String.format("-po qar::user_reports_dir=\"%s\"", dir);
    }
    
    public static String getListReportFiles(String list){
        return String.format("-list \"%s\"", list);
    }
    
    public static String getProduct(String product) {
        return product;
    }
}
