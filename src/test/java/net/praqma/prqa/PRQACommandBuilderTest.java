/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import net.praqma.prqa.products.PRQACommandBuilder;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Praqma
 */
public class PRQACommandBuilderTest {
    public String emptyRes = "";
    
    String nullBlank = null;
    String manySpaceBlank = "           ";
    String noSpaceBlank = "";
    String notBlank = "test";
    
    @Test public void testDataFlowAnalysis() {
        String res = "-ed+";
        
        
        assertEquals(res,PRQACommandBuilder.getDataFlowAnanlysisParameter(true));
        assertEquals(emptyRes, PRQACommandBuilder.getDataFlowAnanlysisParameter(false));
        
    }
    
    @Test public void testEnableDependencyMode() {
        String res = "-mode depend";
        
        assertEquals(res,PRQACommandBuilder.getDependencyModeParameter(true));
        assertEquals(emptyRes, PRQACommandBuilder.getDataFlowAnanlysisParameter(false));
        
    }
    
    @Test public void testGetProdParameter() {
        String res = "-prod %Q ";
        String resSingle = PRQACommandBuilder.getSingle(true);
        
        assertEquals(res+resSingle+" ",PRQACommandBuilder.getProd(true));
        assertEquals(res, PRQACommandBuilder.getProd(false));
        
    }
    
    @Test public void getNumberOfThreads() {
        String res = String.format("-po qav::thread=%s", Runtime.getRuntime().availableProcessors());
        int numThread = Runtime.getRuntime().availableProcessors();
        
        assertEquals(res, PRQACommandBuilder.getNumberOfThreads(numThread));
        
    }
    
    @Test public void getSfbaOption() {
        assertEquals(PRQACommandBuilder.getSfbaOption(true), "-sfba");
        assertEquals(PRQACommandBuilder.getSfbaOption(false),"");
    }
    
    @Test public void getImportLogFilePathParameter() {
        String logPath = "C:/my/path";
        String res = String.format("-po qav::log=\\\"%s\\\"", logPath);
        assertEquals(PRQACommandBuilder.getImportLogFilePathParameter(logPath),res);        
    }
    
    @Test public void getLogFilePathParameter() {
        String logPath = "C:/my/path";
        String res = String.format("-log \\\"%s\\\"", logPath);
        assertEquals(PRQACommandBuilder.getLogFilePathParameter(logPath),res);        
    }
    
    @Test public void getMessageConfigurationParameter() {

        
        assertEquals(PRQACommandBuilder.getMessageConfigurationParameter(nullBlank),"");
        assertEquals(PRQACommandBuilder.getMessageConfigurationParameter(manySpaceBlank),"");
        assertEquals(PRQACommandBuilder.getMessageConfigurationParameter(noSpaceBlank),"");
        
        String format = String.format("-config %s", notBlank);
        
        assertEquals(PRQACommandBuilder.getMessageConfigurationParameter(notBlank), format);
    }
    
    @Test public void getRepositorySetting() {
        String res = String.format("-r %s",notBlank);
        assertEquals(PRQACommandBuilder.getRepositorySetting(nullBlank),"");
        assertEquals(PRQACommandBuilder.getRepositorySetting(manySpaceBlank),"");
        assertEquals(PRQACommandBuilder.getRepositorySetting(noSpaceBlank),"");
        assertEquals(PRQACommandBuilder.getRepositorySetting(notBlank),res);        
    }
    
    @Test public void getVcsXmlString() {
        String res = String.format("-po qav::prqavcs=\\\"%s\\\"", notBlank);
        assertEquals(PRQACommandBuilder.getVcsXmlString(notBlank),res);
    }
    
    @Test public void getPrqaVcs() {
        String resNotBlank = PRQACommandBuilder.getVcsXmlString(notBlank);
        
        CodeUploadSetting allCode = CodeUploadSetting.AllCode;
        CodeUploadSetting settingNone = CodeUploadSetting.None;
        CodeUploadSetting settingOnly = CodeUploadSetting.OnlyNew;
        
        assertEquals(PRQACommandBuilder.getPrqaVcs(allCode,nullBlank),"");
        assertEquals(PRQACommandBuilder.getPrqaVcs(allCode,manySpaceBlank),"");
        assertEquals(PRQACommandBuilder.getPrqaVcs(allCode,noSpaceBlank),"");
        
        assertEquals(PRQACommandBuilder.getPrqaVcs(settingNone, notBlank), resNotBlank);      
    }
    
    @Test public void wrapInQuotationMarks() {
         String wrapped = String.format("\""+"%s"+"\"", notBlank);
         assertEquals(wrapped, PRQACommandBuilder.wrapInQuotationMarks(notBlank));
    }
    
    @Test public void getCodeAll() {
        String empty = "";
        String all = "-po qav::code=all";
        String code = "-po qav::code";
        
        assertEquals(empty, PRQACommandBuilder.getCodeAll(CodeUploadSetting.None));
        assertEquals(all, PRQACommandBuilder.getCodeAll(CodeUploadSetting.AllCode));
        assertEquals(code, PRQACommandBuilder.getCodeAll(CodeUploadSetting.OnlyNew));
        
        
    }
    
    @Test public void getHost() {
        String res = String.format("-host %s", notBlank);
        assertEquals(res, PRQACommandBuilder.getHost(notBlank)); 
        
    }
    
}
