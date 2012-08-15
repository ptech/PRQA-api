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
		logger.log(Level.FINEST, "Starting execution of method - getReadout");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});

		switch(category) {
		case NumberOfFunctionMetrics:
			return getNumberOfFunctionMetrics();
		case NumberOfFunctions:
			return getNumberOfFunctions();
		case NumberOfSourceFiles:
			return getNumberOfSourceFiles();
		case TotalNumberOfFiles:
			return getTotalNumberOfFiles();
		case LinesOfCode:
			return getLinesOfCode();
		case NumberOfFileMetrics:
			return getNumberOfFileMetrics();
		case NumberOfClassMetrics:
			return getNumberOfClassMetrics();
		case NumberOfClasses:
			return getNumberOfClasses();
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Dident find category %s for class %s", category, this.getClass()));

			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

			throw exception;
		}
	}

	@Override
	public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
		logger.log(Level.FINEST, "Starting execution of method - setReadout");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{category.getClass(), category});
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{value.getClass(), value});

		switch(category) {
		case NumberOfFunctionMetrics:
			setNumberOfFunctionMetrics(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case NumberOfFunctions:
			setNumberOfFunctions(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case NumberOfSourceFiles:
			setNumberOfSourceFiles(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case TotalNumberOfFiles:
			setTotalNumberOfFiles(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case LinesOfCode:
			setLinesOfCode(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case NumberOfFileMetrics:
			setNumberOfFileMetrics(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case NumberOfClasses:
			setNumberOfClasses(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		case NumberOfClassMetrics:
			setNumberOfClassMetrics(value.intValue());

			logger.log(Level.FINEST, "Ending execution of method - setReadout");

			break;
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s", value, category, this.getClass()));

			logger.log(Level.SEVERE, "Exception thrown type: {0}; message: {1}", new Object[]{exception.getClass(), exception.getMessage()});

			throw exception;
		}
	}

	public int getNumberOfClasses() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfClasses");
		logger.log(Level.FINEST, "Returning value: {0}", numberOfClasses);

		return numberOfClasses;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfClasses");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfClasses});

		this.numberOfClasses = numberOfClasses;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfClasses");
	}

	public int getNumberOfClassMetrics() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfClassMetrics");
		logger.log(Level.FINEST, "Returning value: {0}", this.numberOfClassMetrics);

		return this.numberOfClassMetrics;
	}

	public void setNumberOfClassMetrics(int numberOfClassMetrics) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfClassMetrics");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfClassMetrics});

		this.numberOfClassMetrics = numberOfClassMetrics;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfClassMetrics");
	}

	/**
	 * @return the totalNumberOfFiles
	 */
	public int getTotalNumberOfFiles() {
		logger.log(Level.FINEST, "Starting execution of method - getTotalNumberOfFiles");
		logger.log(Level.FINEST, "Returning value: {0}", this.totalNumberOfFiles);

		return totalNumberOfFiles;
	}

	/**
	 * @param totalNumberOfFiles the totalNumberOfFiles to set
	 */
	public void setTotalNumberOfFiles(int totalNumberOfFiles) {
		logger.log(Level.FINEST, "Starting execution of method - setTotalNumberOfFiles");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", totalNumberOfFiles});

		this.totalNumberOfFiles = totalNumberOfFiles;

		logger.log(Level.FINEST, "Ending execution of method - setTotalNumberOfFiles");
	}

	/**
	 * @return the linesOfCode
	 */
	public int getLinesOfCode() {
		logger.log(Level.FINEST, "Starting execution of method - getLinesOfCode");
		logger.log(Level.FINEST, "Returning value: {0}", this.linesOfCode);

		return linesOfCode;
	}

	/**
	 * @param linesOfCode the linesOfCode to set
	 */
	public void setLinesOfCode(int linesOfCode) {
		logger.log(Level.FINEST, "Starting execution of method - setLinesOfCode");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", linesOfCode});

		this.linesOfCode = linesOfCode;

		logger.log(Level.FINEST, "Ending execution of method - setLinesOfCode");
	}

	/**
	 * @return the numberOfSourceFiles
	 */
	public int getNumberOfSourceFiles() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfSourceFiles");
		logger.log(Level.FINEST, "Returning value: {0}", this.numberOfSourceFiles);

		return numberOfSourceFiles;
	}

	/**
	 * @param numberOfSourceFiles the numberOfSourceFiles to set
	 */
	public void setNumberOfSourceFiles(int numberOfSourceFiles) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfSourceFiles");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfSourceFiles});

		this.numberOfSourceFiles = numberOfSourceFiles;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfSourceFiles");
	}

	/**
	 * @return the numberOfFileMetrics
	 */
	public int getNumberOfFileMetrics() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfFileMetrics");
		logger.log(Level.FINEST, "Returning value: {0}", this.numberOfFileMetrics);

		return numberOfFileMetrics;
	}

	/**
	 * @param numberOfFileMetrics the numberOfFileMetrics to set
	 */
	public void setNumberOfFileMetrics(int numberOfFileMetrics) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfFileMetrics");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfFileMetrics});

		this.numberOfFileMetrics = numberOfFileMetrics;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfFileMetrics");
	}

	/**
	 * @return the numberOfFunctions
	 */
	public int getNumberOfFunctions() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfFunctions");
		logger.log(Level.FINEST, "Returning value: {0}", this.numberOfFunctions);

		return numberOfFunctions;
	}

	/**
	 * @param numberOfFunctions the numberOfFunctions to set
	 */
	public void setNumberOfFunctions(int numberOfFunctions) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfFunctions");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfFunctions});

		this.numberOfFunctions = numberOfFunctions;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfFunctions");
	}

	/**
	 * @return the numberOfFunctionMetrics
	 */
	public int getNumberOfFunctionMetrics() {
		logger.log(Level.FINEST, "Starting execution of method - getNumberOfFunctionMetrics");
		logger.log(Level.FINEST, "Returning value: {0}", this.numberOfFunctionMetrics);

		return numberOfFunctionMetrics;
	}

	/**
	 * @param numberOfFunctionMetrics the numberOfFunctionMetrics to set
	 */
	public void setNumberOfFunctionMetrics(int numberOfFunctionMetrics) {
		logger.log(Level.FINEST, "Starting execution of method - setNumberOfFunctionMetrics");
		logger.log(Level.FINEST, "Input parameter argument type: {0}; value: {1}", new Object[]{"int", numberOfFunctionMetrics});

		this.numberOfFunctionMetrics = numberOfFunctionMetrics;

		logger.log(Level.FINEST, "Ending execution of method - setNumberOfFunctionMetrics");
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
