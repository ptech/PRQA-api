/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.util.logging.Logger;
import net.praqma.prqa.PRQAContext.QARReportType;
import net.praqma.util.execute.CmdResult;
import net.praqma.util.execute.CommandLine;

/**
 * Reporting class.
 *
 * @author jes
 */
public class QAR implements Product{

    private static final Logger logger = Logger.getLogger(QAR.class.getName());
	public final String projectFile;
	public final String product;	
	private QARReportType type;
    
	public static final String QAW_WRAPPER = "qaw";
    

	/**
	 * QAR is invoked using QAW where this is taken as parameter in the QAW command.
	 */
   
    
    public QAR(String product, String projectFile, QARReportType type) {
        this.product = product;
        this.projectFile = projectFile;
        this.type = type;
    }

	@Override
	public String toString() {
		String out = "";
		out += "QAR selected project file:\t" + this.projectFile + System.getProperty("line.separator");
		out += "QAR selected product:\t\t" + product + System.getProperty("line.separator");
		out += "QAR selected report type:\t" + this.type + System.getProperty("line.separator");
		return out;
	}
    
    @Override
    public String getProductVersion() {
        logger.finest(String.format("Starting execution of method - getProductVersion"));
        
        String version = "Unknown";
        try {
            CmdResult res = CommandLine.getInstance().run("qar -version");            
            version = res.stdoutBuffer.toString();
        } catch (Exception ex) {
            logger.warning("Failed to obtains QAR product version");
        }
        
        logger.finest(String.format("Returning value %s", version));
        
        return version;
    }
}
