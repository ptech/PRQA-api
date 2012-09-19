/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import java.io.FileNotFoundException;
import net.praqma.prga.excetions.PrqaCommandLineException;
import net.praqma.prga.excetions.PrqaException;
import net.praqma.prqa.products.QAC;
import net.praqma.prqa.products.QAR;
import net.praqma.prqa.reports.PRQAComplianceReport;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class ReportTests {
    @Test public void testReportFileNotFoundProject() {
        PRQAComplianceReport report = new PRQAComplianceReport();
        assertNotNull(report.getReportTool());
        
        QAR qar = new QAR(new QAC(), "C:/not/exists/project", PRQAContext.QARReportType.Compliance);
        
        PRQAComplianceReport report2 = new PRQAComplianceReport(qar);
          
        try {
            report2.generateReport();
        } catch (PrqaException ex) {
            if (!(ex instanceof PrqaCommandLineException)) {
                fail("Incorrect exception thrown!");                
            }
            
            if(!(ex.getCause() instanceof FileNotFoundException)) {
                fail();
            }
        }
    }
    
    
    /*
     * 
     */
    @Test public void testReportDefaultValues() {
        PRQAComplianceReport report = new PRQAComplianceReport();
        assertNotNull(report.getReportTool());
        
        assertFalse(report.isUseCrossModuleAnalysis());
        assertFalse(report.isEnableDependencyMode());
        assertFalse(report.isEnableDataFlowAnalysis());
        
    }
    
}
