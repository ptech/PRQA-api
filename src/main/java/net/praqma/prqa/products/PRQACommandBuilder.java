/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.CodeUploadSetting;
import net.praqma.prqa.PRQA;
import net.praqma.prqa.logging.Config;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Praqma
 */
public class PRQACommandBuilder implements Serializable {

    private String executable;
    private LinkedList<String> arguments = new LinkedList<String>();
    private static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public PRQACommandBuilder(String executable) {
    	logger.finest(String.format("Constructor called for class PRQACommandBuilder"));
    	logger.finest(String.format("Input parameter executable type: %s; value: %s", executable.getClass(), executable));

    	this.executable = executable;
        
        logger.finest(String.format("Ending execution of constructor - PRQACommandBuilder"));
    }

    public PRQACommandBuilder appendArgument(String argument) {
        logger.finest(String.format("Starting execution of method - appendArgument"));
        logger.finest(String.format("Input parameter argument type: %s; value: %s", argument.getClass(), argument));

        arguments.addLast(argument);

        logger.finest(String.format("Returning %s", this));

        return this;
    }

    public PRQACommandBuilder prependArgument(String argument) {
        logger.finest(String.format("Starting execution of method - prependArgument"));
        logger.finest(String.format("Input parameter argument type: %s; value: %s", argument.getClass(), argument));

        arguments.addFirst(argument);

        logger.finest(String.format("Returning %s", this));

        return this;
    }

    public String getCommand() {
        logger.finest(String.format("Starting execution of method - getCommand"));

        String output = "";
        output += executable + " ";

        for (String s : arguments) {
            output += s + " ";
        }

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getCmaf(String path, boolean escapeInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getCmaf(String path, boolean escapeInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter path type: %s; value: %s", path.getClass(), path));
        logger.finest(String.format("Input parameter escapeInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeInputParameterWhiteSpace));

        if (escapeInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));
            
            String output = String.format("-cmaf \"%s\"", path.replace(" ", "\\ "));

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-cmaf \"%s\"", path);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getCmaf(String path) {
        logger.finest(String.format("Starting execution of method - getCmaf(String path)"));
        logger.finest(String.format("Input parameter path type: %s; value: %s", path.getClass(), path));

        String output = PRQACommandBuilder.getCmaf(path, false);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getMaseq(String commandSequence, boolean escapeInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getMaseq(String commandSequence, boolean escapeInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter commandSequence type: %s; value: %s", commandSequence.getClass(), commandSequence));
        logger.finest(String.format("Input parameter escapeInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeInputParameterWhiteSpace));

        if (escapeInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));

            String output = String.format(commandSequence.replace(" ", "\\ "), commandSequence);

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-maseq \"%s\"", commandSequence);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    /**
     * Embedding QAR using QAW. Analysis will be performed and the analysis results used as a base for our report.
     *
     * Constructs a valid secondary analysis command parameter for PRQA. Use this to wrap all your secondary analysis commands.
     */
    public static String getMaseq(String commandSequence) {
        logger.finest(String.format("Starting execution of method - getMaseq(String commandSequence)"));
        logger.finest(String.format("Input parameter commandSequence type: %s; value: %s", commandSequence.getClass(), commandSequence));

        String output = String.format("-maseq \"%s\"", commandSequence);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getReportFormatParameter(String reportFormat) {
        logger.finest(String.format("Starting execution of method - getReportFormatParameter(String reportFormat)"));
        logger.finest(String.format("Input parameter reportFormat type: %s; value: %s", reportFormat.getClass(), reportFormat));

        String output = PRQACommandBuilder.getReportFormatParameter(reportFormat, false);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getReportFormatParameter(String reportFormat, boolean escapeinInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getReportFormatParameter(String reportFormat, boolean escapeinInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter reportFormat type: %s; value: %s", reportFormat.getClass(), reportFormat));
        logger.finest(String.format("Input parameter escapeinInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeinInputParameterWhiteSpace));

        if (escapeinInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));

            String output = String.format("-po qar::report_format=%s", reportFormat.replace(" ", "\\ "));

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-po qar::report_format=%s", reportFormat);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getReportTypeParameter(String reportType) {
        logger.finest(String.format("Starting execution of method - getReportTypeParameter(String reportType)"));
        logger.finest(String.format("Input parameter reportType type: %s; value: %s", reportType.getClass(), reportType));

        String output = String.format("-po qar::report_type=%s\\ Report", reportType);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getReportTypeParameter(String reportType, boolean escapeInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getReportTypeParameter(String reportType, boolean escapeInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter reportType type: %s; value: %s", reportType.getClass(), reportType));
        logger.finest(String.format("Input parameter escapeInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeInputParameterWhiteSpace));

        if (escapeInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));

            String output = String.format("-po qar::report_type=%s\\ Report", reportType.replace(" ", "\\ "));

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-po qar::report_type=%s\\ Report", reportType);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getOutputPathParameter(String outpath) {
        logger.finest(String.format("Starting execution of method - getOutputPathParameter(String outpath)"));
        logger.finest(String.format("Input parameter outpath type: %s; value: %s", outpath.getClass(), outpath));

        String output = PRQACommandBuilder.getOutputPathParameter(outpath, false);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getOutputPathParameter(String outpath, boolean escapeInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getOutputPathParameter(String outpath, boolean escapeInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter outpath type: %s; value: %s", outpath.getClass(), outpath));
        logger.finest(String.format("Input parameter escapeInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeInputParameterWhiteSpace));


        if (escapeInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));

            String output = String.format("-po qar::output_path=%s", outpath.replace(" ", "\\ "));

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-po qar::output_path=%s", outpath);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    /**
     * Uses the project names as specified in the project file
     *
     * @return
     */
    public static String getProjectName() {
        logger.finest(String.format("Starting execution of method - getProjectName"));

        String output = "-po qar::project_name=%J";

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getViewingProgram(String program) {
        logger.finest(String.format("Starting execution of method - getViewingProgram(String program)"));
        logger.finest(String.format("Input parameter program type: %s; value: %s", program.getClass(), program));

        String output = PRQACommandBuilder.getViewingProgram(program, false);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getViewingProgram(String program, boolean escapeInputParameterWhiteSpace) {
        logger.finest(String.format("Starting execution of method - getViewingProgram(String program, boolean escapeInputParameterWhiteSpace)"));
        logger.finest(String.format("Input parameter program type: %s; value: %s", program.getClass(), program));
        logger.finest(String.format("Input parameter escapeInputParameterWhiteSpace type: %s; value: %s", "boolean", escapeInputParameterWhiteSpace));

        if (escapeInputParameterWhiteSpace) {
            logger.finest(String.format("Replacing spaces with \"\\ \""));

            String output = String.format("-po qar::viewing_program=%s", program.replace(" ", "\\ "));

            logger.finest(String.format("Returning value: %s", output));

            return output;
        }

        String output = String.format("-po qar::viewing_program=%s", program);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getListReportFiles(String list) {
        logger.finest(String.format("Starting execution of method - getListReportFiles"));
        logger.finest(String.format("Input parameter list type: %s; value: %s", list.getClass(), list));

        String output = String.format("-list \"%s\"", list);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getProjectFile(String file) {
        logger.finest(String.format("Starting execution of method - getProjectFile"));
        logger.finest(String.format("Input parameter file type: %s; value: %s", file.getClass(), file));

        String output = String.format("\"%s\"", file);

        logger.finest(String.format("Returning value: %s", output));

        return output;
    }

    public static String getProduct(PRQA product) {
        logger.finest(String.format("Starting execution of method - getProduct"));
        logger.finest(String.format("Input parameter product type: %s; value: %s", product.getClass(), product));

        logger.finest(String.format("Returning value: %s", product));

        return product.toString();
    }
    
    public static String getHost(String hostname) {
        logger.entering(PRQACommandBuilder.class.getName(), "getHost", hostname);
        String host = String.format("-host %s", hostname);
        logger.exiting(PRQACommandBuilder.class.getName(), "getHost", host);
        return host;
    }
    
    public static String getUser(String user) {
        logger.entering(PRQACommandBuilder.class.getName(), "getUser", user);
        String userres = String.format("-user %s", user);
        logger.exiting(PRQACommandBuilder.class.getName(), "getUser", userres);
        return userres;
    }
    
    public static String getPassword(String password) {
        logger.entering(PRQACommandBuilder.class.getName(), "getPassword", password);
        String pass = String.format("-pass %s", password);
        logger.exiting(PRQACommandBuilder.class.getName(), "getPassword", pass);
        return pass;
    }
    
    public static String getProjectDatabase(String databaseName) {
        logger.entering(PRQACommandBuilder.class.getName(), "getProjectDatabase", databaseName);
        String dbname = String.format("-db %s", databaseName);
        logger.exiting(PRQACommandBuilder.class.getName(), "getProjectDatabase", dbname);
        return dbname;
    }
    
    public static String getSingle(boolean useSingleSnapshotMode) {
        logger.entering(PRQACommandBuilder.class.getName(), "getSingle", useSingleSnapshotMode);
        if(useSingleSnapshotMode) {
            logger.exiting(PRQACommandBuilder.class.getName(), "getSingle", "-single");
            return "-single";
        }
        logger.exiting(PRQACommandBuilder.class.getName(), "getSingle", "");
        return "";
    }
    
    public static String getSnapshotName(String snapshotName) {
        logger.entering(PRQACommandBuilder.class.getName(), "getSnapshotName", snapshotName);
        String ssname = "";
        if(StringUtils.isNotBlank(snapshotName)) {
            ssname = String.format("-ssname %s", snapshotName);
        }
        logger.exiting(PRQACommandBuilder.class.getName(), "getSnapshotName", ssname);
        return ssname;
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
        logger.entering(PRQACommandBuilder.class.getName(), "wrapInQuotationMarks", string);
        String wrapped = String.format("\""+"%s"+"\"", string);
        logger.exiting(PRQACommandBuilder.class.getName(), "wrapInQuotationMarks", wrapped);
        return wrapped;
    }
    
    public static String getQavOutPathParameter(String outpath) {
        return getQavOutPathParameter(outpath, false);
    }
    
    public static String getQavOutPathParameter(String outpath, boolean escapeInputParameterWhiteSpace) {
        logger.entering(PRQACommandBuilder.class.getName(), "getQavOutPathParameter", escapeInputParameterWhiteSpace);
        String out = "";
        if(escapeInputParameterWhiteSpace) {
            out = String.format("-po qav::outputh=%s", outpath.replace(" ", "\\ "));        
        } else {
            out = String.format("-po qav::output=%s", outpath);    
        }
        logger.exiting(PRQACommandBuilder.class.getName(), "getQavOutPathParameter", out);
        return out;
    }
    
    public static String getVcsXmlString(String vcsXmlPath) {
        logger.entering(PRQACommandBuilder.class.getName(), "getVcsXmlString", vcsXmlPath);
        String vcsxml = String.format("-po qav::prqavcs=\\\"%s\\\"", vcsXmlPath);
        logger.exiting(PRQACommandBuilder.class.getName(), "getVcsXmlString", vcsxml);
        return vcsxml;
    }
    
    //RQ-6
    public static String getPrqaVcs(CodeUploadSetting setting, String repositoyPath) {
        String res = "";
        if(!(setting.equals(CodeUploadSetting.AllCode) && StringUtils.isBlank(repositoyPath))) {
            res = "-po qav::prqavcs";
        }
        return res;
    }
    
    public static String getRepositorySetting(String repositoyPath) {
        String res = "";
        if(!StringUtils.isBlank(repositoyPath)) {
            res = String.format("-r %s",repositoyPath);
        }
        return res;
    } 
    
    public static String getMessageConfigurationParameter(String projConfigXml) {
        String res = "";
        if(!StringUtils.isBlank(projConfigXml)) {
            res = String.format("-config %s", projConfigXml);
        }
        return res;
    }
    
    
}

