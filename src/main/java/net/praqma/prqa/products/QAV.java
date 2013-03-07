/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import net.praqma.prqa.CodeUploadSetting;
import net.praqma.prqa.PRQACommandLineUtility;

/**
 *
 * @author Praqma
 */
public class QAV implements Product {
    private CodeUploadSetting codeUploadSettings;
    
    public static final String QAV_UPLOAD_LOG = PRQACommandLineUtility.FILE_SEPARATOR+"qavupload.log";
    public static final String QAV_IMPORT_LOG = PRQACommandLineUtility.FILE_SEPARATOR+"qaimport.log";
    
    
    
    public QAV() { }

    @Override
    public String getProductVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
