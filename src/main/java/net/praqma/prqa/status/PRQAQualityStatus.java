/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import java.util.logging.Level;
import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma
 */
public class PRQAQualityStatus extends PRQAStatus {

	private int totalNumberOfFiles;
	private int linesOfCode;
	private int numberOfSourceFiles;
	private int numberOfFileMetrics;
	private int numberOfFunctions;
	private int numberOfFunctionMetrics;

	//Added for Cpp projects
	private int numberOfClasses = 0;
	private int numberOfClassMetrics = 0;

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
		logger.finest(String.format("Starting execution of method - getReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));

		Number output;
		switch(category) {
		case NumberOfFunctionMetrics:
			output = getNumberOfFunctionMetrics();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case NumberOfFunctions:
			output = getNumberOfFunctions();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case NumberOfSourceFiles:
			output = getNumberOfSourceFiles();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case TotalNumberOfFiles:
			output = getTotalNumberOfFiles();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case LinesOfCode:
			output = getLinesOfCode();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case NumberOfFileMetrics:
			output = getNumberOfFileMetrics();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case NumberOfClassMetrics:
			output = getNumberOfClassMetrics();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		case NumberOfClasses:
			output = getNumberOfClasses();
			
			logger.finest(String.format("Returning value: %s", output));
			
			return output;
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Didn't find category %s for class %s", category, this.getClass()));

			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

			throw exception;
		}
	}

	@Override
	public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
		logger.finest(String.format("Starting execution of method - setReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));
		logger.finest(String.format("Input parameter value type: %s; value: %s", value.getClass(), value));

		switch(category) {
		case NumberOfFunctionMetrics:
			setNumberOfFunctionMetrics(value.intValue());
			
			logger.finest(String.format("Setting numberOfFunctionMetrics to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case NumberOfFunctions:
			setNumberOfFunctions(value.intValue());

			logger.finest(String.format("Setting numberOfFunctions to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case NumberOfSourceFiles:
			setNumberOfSourceFiles(value.intValue());

			logger.finest(String.format("Setting numberOfSourceFiles to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case TotalNumberOfFiles:
			setTotalNumberOfFiles(value.intValue());

			logger.finest(String.format("Setting totalNumberOfFiles to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case LinesOfCode:
			setLinesOfCode(value.intValue());

			logger.finest(String.format("Setting linesOfCode to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case NumberOfFileMetrics:
			setNumberOfFileMetrics(value.intValue());

			logger.finest(String.format("Setting numberOfFileMetrics to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case NumberOfClasses:
			setNumberOfClasses(value.intValue());

			logger.finest(String.format("Setting numberOfClasses to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		case NumberOfClassMetrics:
			setNumberOfClassMetrics(value.intValue());

			logger.finest(String.format("Setting numberOfClassMetrics to: %s.", value.intValue()));
			logger.finest(String.format("Ending execution of method - setReadout"));

			break;
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s", value, category, this.getClass()));

			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

			throw exception;
		}
	}

	public int getNumberOfClasses() {
		logger.finest(String.format("Starting execution of method - getNumberOfClasses"));
		logger.finest(String.format("Returning value: %s", numberOfClasses));

		return numberOfClasses;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		logger.finest(String.format("Starting execution of method - setNumberOfClasses"));
		logger.finest(String.format("Input parameter numberOfClasses type: %s; value: %s", "int", numberOfClasses));

		this.numberOfClasses = numberOfClasses;

		logger.finest(String.format("Ending execution of method - setNumberOfClasses"));
	}

	public int getNumberOfClassMetrics() {
		logger.finest(String.format("Starting execution of method - getNumberOfClassMetrics"));
		logger.finest(String.format("Returning value: %s", this.numberOfClassMetrics));

		return this.numberOfClassMetrics;
	}

	public void setNumberOfClassMetrics(int numberOfClassMetrics) {
		logger.finest(String.format("Starting execution of method - setNumberOfClassMetrics"));
		logger.finest(String.format("Input parameter numberOfClassMetrics type: %s; value: %s", "int", numberOfClassMetrics));

		this.numberOfClassMetrics = numberOfClassMetrics;

		logger.finest(String.format("Ending execution of method - setNumberOfClassMetrics"));
	}

	/**
	 * @return the totalNumberOfFiles
	 */
	public int getTotalNumberOfFiles() {
		logger.finest(String.format("Starting execution of method - getTotalNumberOfFiles"));
		logger.finest(String.format("Returning value: %s", this.totalNumberOfFiles));

		return totalNumberOfFiles;
	}

	/**
	 * @param totalNumberOfFiles the totalNumberOfFiles to set
	 */
	public void setTotalNumberOfFiles(int totalNumberOfFiles) {
		logger.finest(String.format("Starting execution of method - setTotalNumberOfFiles"));
		logger.finest(String.format("Input parameter totalNumberOfFiles type: %s; value: %s", "int", totalNumberOfFiles));

		this.totalNumberOfFiles = totalNumberOfFiles;

		logger.finest(String.format("Ending execution of method - setTotalNumberOfFiles"));
	}

	/**
	 * @return the linesOfCode
	 */
	public int getLinesOfCode() {
		logger.finest(String.format("Starting execution of method - getLinesOfCode"));
		logger.finest(String.format("Returning value: %s", this.linesOfCode));

		return linesOfCode;
	}

	/**
	 * @param linesOfCode the linesOfCode to set
	 */
	public void setLinesOfCode(int linesOfCode) {
		logger.finest(String.format("Starting execution of method - setLinesOfCode"));
		logger.finest(String.format("Input parameter linesOfCode type: %s; value: %s", "int", linesOfCode));

		this.linesOfCode = linesOfCode;

		logger.finest(String.format("Ending execution of method - setLinesOfCode"));
	}

	/**
	 * @return the numberOfSourceFiles
	 */
	public int getNumberOfSourceFiles() {
		logger.finest(String.format("Starting execution of method - getNumberOfSourceFiles"));
		logger.finest(String.format("Returning value: %s", this.numberOfSourceFiles));

		return numberOfSourceFiles;
	}

	/**
	 * @param numberOfSourceFiles the numberOfSourceFiles to set
	 */
	public void setNumberOfSourceFiles(int numberOfSourceFiles) {
		logger.finest(String.format("Starting execution of method - setNumberOfSourceFiles"));
		logger.finest(String.format("Input parameter numberOfSourceFiles type: %s; value: %s", "int", numberOfSourceFiles));

		this.numberOfSourceFiles = numberOfSourceFiles;

		logger.finest(String.format("Ending execution of method - setNumberOfSourceFiles"));
	}

	/**
	 * @return the numberOfFileMetrics
	 */
	public int getNumberOfFileMetrics() {
		logger.finest(String.format("Starting execution of method - getNumberOfFileMetrics"));
		logger.finest(String.format("Returning value: %s", this.numberOfFileMetrics));

		return numberOfFileMetrics;
	}

	/**
	 * @param numberOfFileMetrics the numberOfFileMetrics to set
	 */
	public void setNumberOfFileMetrics(int numberOfFileMetrics) {
		logger.finest(String.format("Starting execution of method - setNumberOfFileMetrics"));
		logger.finest(String.format("Input parameter numberOfFileMetrics type: %s; value: %s", "int", numberOfFileMetrics));

		this.numberOfFileMetrics = numberOfFileMetrics;

		logger.finest(String.format("Ending execution of method - setNumberOfFileMetrics"));
	}

	/**
	 * @return the numberOfFunctions
	 */
	public int getNumberOfFunctions() {
		logger.finest(String.format("Starting execution of method - getNumberOfFunctions"));
		logger.finest(String.format("Returning value: %s", this.numberOfFunctions));

		return numberOfFunctions;
	}

	/**
	 * @param numberOfFunctions the numberOfFunctions to set
	 */
	public void setNumberOfFunctions(int numberOfFunctions) {
		logger.finest(String.format("Starting execution of method - setNumberOfFunctions"));
		logger.finest(String.format("Input parameter numberOfFunctions type: %s; value: %s", "int", numberOfFunctions));

		this.numberOfFunctions = numberOfFunctions;

		logger.finest(String.format("Ending execution of method - setNumberOfFunctions"));
	}

	/**
	 * @return the numberOfFunctionMetrics
	 */
	public int getNumberOfFunctionMetrics() {
		logger.finest(String.format("Starting execution of method - getNumberOfFunctionMetrics"));
		logger.finest(String.format("Returning value: %s", this.numberOfFunctionMetrics));

		return numberOfFunctionMetrics;
	}

	/**
	 * @param numberOfFunctionMetrics the numberOfFunctionMetrics to set
	 */
	public void setNumberOfFunctionMetrics(int numberOfFunctionMetrics) {
		logger.finest(String.format("Starting execution of method - setNumberOfFunctionMetrics"));
		logger.finest(String.format("Input parameter numberOfFunctionMetrics type: %s; value: %s", "int", numberOfFunctionMetrics));

		this.numberOfFunctionMetrics = numberOfFunctionMetrics;

		logger.finest(String.format("Ending execution of method - setNumberOfFunctionMetrics"));
	}

	@Override
	public String toString() {
		String res = "";
		res += "Total Number of Files: " + totalNumberOfFiles + System.getProperty("line.separator");
		res += "Lines of Code: " + linesOfCode + System.getProperty("line.separator");
		res += "Number of Source Files: " + numberOfSourceFiles + System.getProperty("line.separator");
		res += "Number of File Metrics: " + numberOfFileMetrics + System.getProperty("line.separator");
		res += "Number of Functions: " + numberOfFunctions + System.getProperty("line.separator");
		res += "Number of Function Metrics: "+ numberOfFunctionMetrics + System.getProperty("line.separator");        
		return res;
	}

	@Override
	public String toHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table style=\"border:solid;border-width:1px;padding:15px;margin:10px;\">");
		sb.append("<h2>Quality Summary</h2>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th>Total Number of Files</th>");
		sb.append("<th>Lines of Code</th>");
		sb.append("<th>Number of Source Files</th>");
		sb.append("<th>Number of File Metrics</th>");
		sb.append("<th>Number of Functions</th>");
		sb.append("<th>Number of Function Metrics</th>");
		sb.append("<th>Number of Classes</th>");
		sb.append("<th>Number of Class Metrics</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td>").append(getTotalNumberOfFiles()).append("</td>");
		sb.append("<td>").append(getLinesOfCode()).append("</td>");
		sb.append("<td>").append(getNumberOfSourceFiles()).append("</td>");
		sb.append("<td>").append(getNumberOfFileMetrics()).append("</td>");
		sb.append("<td>").append(getNumberOfFunctions()).append("</td>");
		sb.append("<td>").append(getNumberOfFunctionMetrics()).append("</td>");
		sb.append("<td>").append(getNumberOfClasses()).append("</td>");
		sb.append("<td>").append(getNumberOfClassMetrics()).append("</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}
}
