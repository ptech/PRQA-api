/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.status;

import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma
 */
public class PRQASuppressionStatus extends PRQAStatus {

	private int numberOfFiles;
	private int linesOfCode;
	private int uniqueMsgsSuppressed;
	private int msgsSuppressed;
	private double pctMsgsSuppressed;

	@Override
	public boolean isValid() {
		logger.finest(String.format("Starting execution of method - isValid"));

		UnsupportedOperationException exception;
		exception = new UnsupportedOperationException("Not supported yet.");

		logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

		throw exception;
	}

	@Override
	public Number getReadout(StatusCategory category) throws PrqaException.PrqaReadingException {
		logger.finest(String.format("Starting execution of method - getReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));

		Number output;
		switch (category) {
		case TotalNumberOfFiles:
			output = getNumberOfFiles();
			break;
		case LinesOfCode:
			output = getLinesOfCode();
			break;
		case UniqueMessagesSuppressed:
			output = getUniqueMsgsSuppressed();
			break;
		case MessagesSuppressed:
			output = getMsgsSuppressed();
			break;
		case PercentageMessagesSuppressed:
			output = getMsgsSuppressed();
			break;
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Didn't find category %s for class %s", category, this.getClass()));

            logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

            throw exception;
		}

		logger.finest(String.format("Returning value: %s", output));

		return output;
	}

	@Override
	public void setReadout(StatusCategory category, Number value) throws PrqaException.PrqaReadingException {
		logger.finest(String.format("Starting execution of method - setReadout"));
		logger.finest(String.format("Input parameter category type: %s; value: %s", category.getClass(), category));
		logger.finest(String.format("Input parameter value type: %s; value: %s", value.getClass(), value));

		switch (category) {
		case TotalNumberOfFiles:
			logger.finest(String.format("Setting numberOfFiles to: %s.", value.intValue()));

			setNumberOfFiles(value.intValue());
			break;
		case LinesOfCode:
			logger.finest(String.format("Setting linesOfCode to: %s.", value.intValue()));
			
			setLinesOfCode(value.intValue());
			break;
		case UniqueMessagesSuppressed:
			logger.finest(String.format("Setting uniqueMsgsSuppressed to: %s.", value.intValue()));
			
			setUniqueMsgsSuppressed(value.intValue());
			break;
		case MessagesSuppressed:
			logger.finest(String.format("Setting msgsSuppressed to: %s.", value.intValue()));
			
			setMsgsSuppressed(value.intValue());
			break;
		case PercentageMessagesSuppressed:
			logger.finest(String.format("Setting pctMsgsSuppressed to: %s.", value.doubleValue()));
			
			setPctMsgsSuppressed(value.doubleValue());
			break;
		default:
			PrqaException.PrqaReadingException exception;
			exception = new PrqaException.PrqaReadingException(String.format("Could not set value of %s for category %s in class %s", value, category, this.getClass()));

			logger.severe(String.format("Exception thrown type: %s; message: %s", exception.getClass(), exception.getMessage()));

			throw exception;
		}

		logger.finest(String.format("Ending execution of method - setReadout"));
	}

	/**
	 * @return the numberOfFiles
	 */
	public int getNumberOfFiles() {
		logger.finest(String.format("Starting execution of method - getNumberOfFiles"));
		logger.finest(String.format("Returning value: %s", numberOfFiles));

		return numberOfFiles;
	}

	/**
	 * @param numberOfFiles the numberOfFiles to set
	 */
	public void setNumberOfFiles(int numberOfFiles) {
		logger.finest(String.format("Starting execution of method - setNumberOfFiles"));
		logger.finest(String.format("Input parameter numberOfFiles type: %s; value: %s", "int", numberOfFiles));

		this.numberOfFiles = numberOfFiles;

		logger.finest(String.format("Ending execution of method - setNumberOfFiles"));
	}

	/**
	 * @return the linesOfCode
	 */
	public int getLinesOfCode() {
		logger.finest(String.format("Starting execution of method - getLinesOfCode"));
		logger.finest(String.format("Returning value: %s", linesOfCode));

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
	 * @return the uniqueMsgsSuppressed
	 */
	public int getUniqueMsgsSuppressed() {
		logger.finest(String.format("Starting execution of method - getUniqueMsgsSuppressed"));
		logger.finest(String.format("Returning value: %s", uniqueMsgsSuppressed));

		return uniqueMsgsSuppressed;
	}

	/**
	 * @param uniqueMsgsSuppressed the uniqueMsgsSuppressed to set
	 */
	public void setUniqueMsgsSuppressed(int uniqueMsgsSuppressed) {
		logger.finest(String.format("Starting execution of method - setUniqueMsgsSuppressed"));
		logger.finest(String.format("Input parameter uniqueMsgsSuppressed type: %s; value: %s", "int", uniqueMsgsSuppressed));

		this.uniqueMsgsSuppressed = uniqueMsgsSuppressed;

		logger.finest(String.format("Ending execution of method - setUniqueMsgsSuppressed"));
	}

	/**
	 * @return the msgsSuppressed
	 */
	public int getMsgsSuppressed() {
		logger.finest(String.format("Starting execution of method - getMsgsSuppressed"));
		logger.finest(String.format("Returning value: %s", msgsSuppressed));

		return msgsSuppressed;
	}

	/**
	 * @param msgsSuppressed the msgsSuppressed to set
	 */
	public void setMsgsSuppressed(int msgsSuppressed) {
		logger.finest(String.format("Starting execution of method - setMsgsSuppressed"));
		logger.finest(String.format("Input parameter msgsSuppressed type: %s; value: %s", "int", msgsSuppressed));

		this.msgsSuppressed = msgsSuppressed;

		logger.finest(String.format("Ending execution of method - setMsgsSuppressed"));
	}

	/**
	 * @return the pctMsgsSuppressed
	 */
	public double getPctMsgsSuppressed() {
		logger.finest(String.format("Starting execution of method - getPctMsgsSuppressed"));
		logger.finest(String.format("Returning value: %s", pctMsgsSuppressed));

		return pctMsgsSuppressed;
	}

	/**
	 * @param pctMsgsSuppressed the pctMsgsSuppressed to set
	 */
	public void setPctMsgsSuppressed(double pctMsgsSuppressed) {
		logger.finest(String.format("Starting execution of method - setPctMsgsSuppressed"));
		logger.finest(String.format("Input parameter pctMsgsSuppressed type: %s; value: %s", "double", pctMsgsSuppressed));

		this.pctMsgsSuppressed = pctMsgsSuppressed;

		logger.finest(String.format("Ending execution of method - setPctMsgsSuppressed"));
	}

	@Override
	public String toString() {
		String res = "";
		res += "Scanned the following supression report values: " + System.getProperty("line.separator");
		res += "Number of Files: " + numberOfFiles + System.getProperty("line.separator");
		res += "Lines of Code: " + linesOfCode + System.getProperty("line.separator");
		res += "Unique Messages Suppressed: " + uniqueMsgsSuppressed + System.getProperty("line.separator");
		res += "Number of Messages Suppressed: " + msgsSuppressed + System.getProperty("line.separator");
		res += "Percentage of Messages Suppressed: " + pctMsgsSuppressed + System.getProperty("line.separator");

		return res;
	}

	@Override
	public String toHtml() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table style=\"border:solid;border-width:1px;padding:15px;margin:10px;\">");
		sb.append("<h2>Quality Summary</h2>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th>Number of Files</th>");
		sb.append("<th>Lines of Code</th>");
		sb.append("<th>Unique Messages Suppressed</th>");
		sb.append("<th>Number of Messages Suppressed</th>");
		sb.append("<th>Percentage of Messages Suppressed</th>");
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		sb.append("<tr>");
		sb.append("<td>").append(getNumberOfFiles()).append("</td>");
		sb.append("<td>").append(getLinesOfCode()).append("</td>");
		sb.append("<td>").append(getUniqueMsgsSuppressed()).append("</td>");
		sb.append("<td>").append(getMsgsSuppressed()).append("</td>");
		sb.append("<td>").append(getPctMsgsSuppressed()).append("%</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");

		return sb.toString();
	}
}
