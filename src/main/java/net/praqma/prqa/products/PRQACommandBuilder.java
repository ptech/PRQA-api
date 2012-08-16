/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.prqa.logging.Config;

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

    public static String getProduct(String product) {
        logger.finest(String.format("Starting execution of method - getProduct"));
        logger.finest(String.format("Input parameter product type: %s; value: %s", product.getClass(), product));

        logger.finest(String.format("Returning value: %s", product));

        return product;
    }
}
