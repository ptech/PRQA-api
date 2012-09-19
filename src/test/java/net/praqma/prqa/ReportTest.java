/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import net.praqma.prga.excetions.PrqaCommandLineException;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.parsers.CodeReviewReportParser;
import net.praqma.prqa.parsers.SuppressionReportParser;
import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.reports.PRQACodeReviewReport;
import net.praqma.prqa.reports.PRQAComplianceReport;
import net.praqma.prqa.reports.PRQAReport;
import net.praqma.prqa.reports.PRQASuppressionReport;
import net.praqma.prqa.status.PRQAStatus;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class ReportTest {
    @Test public void testReportFileNotFoundProject() {
        PRQAComplianceReport report = new PRQAComplianceReport();
        assertNull(report.getReportTool());
        
        PRQASuppressionReport report3 = new PRQASuppressionReport();
        assertNull(report3.getReportTool());
        assertTrue(report3.getParser() instanceof SuppressionReportParser);
        
        
        PRQACodeReviewReport report4 = new PRQACodeReviewReport();
        assertNull(report4.getReportTool());
        assertTrue(report4.getParser() instanceof CodeReviewReportParser);
        
        
        
        QAR qar = new QAR(new QAC(), "C:/not/exists/project", PRQAContext.QARReportType.Compliance);
        QAR qar3 = new QAR(new QAC(), "C:/not/exists/project", PRQAContext.QARReportType.Suppression);
        QAR qar4 = new QAR(new QAC(), "C:/not/exists/project", PRQAContext.QARReportType.CodeReview);
        
        
        PRQAComplianceReport report2 = new PRQAComplianceReport(qar);
        report.setReportTool(qar);
        report3.setReportTool(qar3);
        report4.setReportTool(qar4);
        
        List<PRQAReport<? extends PRQAStatus>> reports = Arrays.asList(report,report2,report3,report4);
        
          
        try {
            
            for(PRQAReport<? extends PRQAStatus> rep : reports) {
                assertNotNull(rep.getReportTool());
                assertTrue(rep.getNamingTemplate().endsWith(PRQAReport.XHTML_REPORT_EXTENSION));
                assertTrue(report.getNamingTemplate().equals(report.getNamingTemplate(rep.getReportTool().getType(), PRQAReport.XHTML_REPORT_EXTENSION)));
                rep.generateReport();    
            }
            
        } catch (PrqaException ex) {
            if (!(ex instanceof PrqaCommandLineException)) {
                fail("Incorrect exception thrown!");                
            }
            
            if(!(ex.getCause() instanceof FileNotFoundException)) {
                fail("This should not happen");
            }
        }
    }
    
    
    /*
     * 
     */
    @Test public void testReportDefaultValues() {
        PRQAComplianceReport report = new PRQAComplianceReport();
        assertNull(report.getReportTool());
        
        assertFalse(report.isUseCrossModuleAnalysis());
        assertFalse(report.isEnableDependencyMode());
        assertFalse(report.isEnableDataFlowAnalysis());
        
    }
    
    @Test public void testConstants() {
        assertTrue(PRQAContext.QARReportType.values().length == 3);
        assertTrue(PRQAContext.QARReportType.REQUIRED_TYPES.size() == 1);
        assertTrue(PRQAContext.QARReportType.OPTIONAL_TYPES.size() == 2);
        assertTrue(PRQAContext.QARReportType.REQUIRED_TYPES.contains(PRQAContext.QARReportType.Compliance));
    }
       
}
