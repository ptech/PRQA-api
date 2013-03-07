/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import net.praqma.prqa.reports.PRQAComplianceReport;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class ReportTest {
    @Test public void testReportFileNotFoundProject() {
    }

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
