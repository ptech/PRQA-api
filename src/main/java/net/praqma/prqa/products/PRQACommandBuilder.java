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
    private transient static final Logger logger;

    static {
        logger = Logger.getLogger(Config.GLOBAL_LOGGER_NAME);
    }

    public PRQACommandBuilder(String executable) {
        this.executable = executable;
    }

    public PRQACommandBuilder appendArgument(String argument) {
        logger.log(Level.FINEST, "Starting execution of method - appendArgument");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{argument.getClass(), argument});

        arguments.addLast(argument);

        logger.log(Level.FINEST, "Returning {0}", this);

        return this;
    }

    public PRQACommandBuilder prependArgument(String argument) {
        logger.log(Level.FINEST, "Starting execution of method - prependArgument");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{argument.getClass(), argument});

        arguments.addFirst(argument);

        logger.log(Level.FINEST, "Returning {0}", this);

        return this;
    }

    public String getCommand() {
        logger.log(Level.FINEST, "Starting execution of method - getCommand");

        String output = "";
        output += executable + " ";

        for (String s : arguments) {
            output += s + " ";
        }

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getCmaf(String path, boolean escapeInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getCmaf");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{path.getClass(), path});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeInputParameterWhiteSpace});

        if (escapeInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");
            String output = String.format("-cmaf \"%s\"", path.replace(" ", "\\ "));

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-cmaf \"%s\"", path);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getCmaf(String path) {
        logger.log(Level.FINEST, "Starting execution of method - getCmaf");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{path.getClass(), path});

        String output = PRQACommandBuilder.getCmaf(path, false);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getMaseq(String commandSequence, boolean escapeInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getMaseq");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{commandSequence.getClass(), commandSequence});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeInputParameterWhiteSpace});

        if (escapeInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");

            String output = String.format(commandSequence.replace(" ", "\\ "), commandSequence);

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-maseq \"%s\"", commandSequence);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    /**
     * Embedding QAR using QAW. Analysis will be performed and the analysis results used as a base for our report.
     *
     * Constructs a valid secondary analysis command parameter for PRQA. Use this to wrap all your secondary analysis commands.
     */
    public static String getMaseq(String commandSequence) {
        logger.log(Level.FINEST, "Starting execution of method - getMaseq");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{commandSequence.getClass(), commandSequence});

        String output = String.format("-maseq \"%s\"", commandSequence);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getReportFormatParameter(String reportFormat) {
        logger.log(Level.FINEST, "Starting execution of method - getReportFormatParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{reportFormat.getClass(), reportFormat});

        String output = PRQACommandBuilder.getReportFormatParameter(reportFormat, false);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getReportFormatParameter(String reportFormat, boolean escapeinInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getReportFormatParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{reportFormat.getClass(), reportFormat});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeinInputParameterWhiteSpace});

        if (escapeinInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");

            String output = String.format("-po qar::report_format=%s", reportFormat.replace(" ", "\\ "));

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-po qar::report_format=%s", reportFormat);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getReportTypeParameter(String reportType) {
        logger.log(Level.FINEST, "Starting execution of method - getReportTypeParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{reportType.getClass(), reportType});

        String output = String.format("-po qar::report_type=%s\\ Report", reportType);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getReportTypeParameter(String reportType, boolean escapeInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getReportTypeParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{reportType.getClass(), reportType});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeInputParameterWhiteSpace});

        if (escapeInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");

            String output = String.format("-po qar::report_type=%s\\ Report", reportType.replace(" ", "\\ "));

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-po qar::report_type=%s\\ Report", reportType);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getOutputPathParameter(String outpath) {
        logger.log(Level.FINEST, "Starting execution of method - getOutputPathParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{outpath.getClass(), outpath});

        String output = PRQACommandBuilder.getOutputPathParameter(outpath, false);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getOutputPathParameter(String outpath, boolean escapeInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getOutputPathParameter");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{outpath.getClass(), outpath});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeInputParameterWhiteSpace});


        if (escapeInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");

            String output = String.format("-po qar::output_path=%s", outpath.replace(" ", "\\ "));

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-po qar::output_path=%s", outpath);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    /**
     * Uses the project names as specified in the project file
     *
     * @return
     */
    public static String getProjectName() {
        logger.log(Level.FINEST, "Starting execution of method - getProjectName");

        String output = "-po qar::project_name=%J";

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getViewingProgram(String program) {
        logger.log(Level.FINEST, "Starting execution of method - getViewingProgram");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{program.getClass(), program});

        String output = PRQACommandBuilder.getViewingProgram(program, false);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getViewingProgram(String program, boolean escapeInputParameterWhiteSpace) {
        logger.log(Level.FINEST, "Starting execution of method - getViewingProgram");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{program.getClass(), program});
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"boolean", escapeInputParameterWhiteSpace});

        if (escapeInputParameterWhiteSpace) {
            logger.log(Level.FINEST, "replacing spaces with \"\\ \"");

            String output = String.format("-po qar::viewing_program=%s", program.replace(" ", "\\ "));

            logger.log(Level.FINEST, "Returning value: {0}", output);

            return output;
        }

        String output = String.format("-po qar::viewing_program=%s", program);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getListReportFiles(String list) {
        logger.log(Level.FINEST, "Starting execution of method - getListReportFiles");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{list.getClass(), list});

        String output = String.format("-list \"%s\"", list);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getProjectFile(String file) {
        logger.log(Level.FINEST, "Starting execution of method - getProjectFile");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{file.getClass(), file});

        String output = String.format("\"%s\"", file);

        logger.log(Level.FINEST, "Returning value: {0}", output);

        return output;
    }

    public static String getProduct(String product) {
        logger.log(Level.FINEST, "Starting execution of method - getProduct");
        logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{product.getClass(), product});

        logger.log(Level.FINEST, "Returning value: {0}", product);

        return product;
    }
}
