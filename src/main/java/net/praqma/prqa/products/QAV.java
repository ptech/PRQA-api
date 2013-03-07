/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.HashMap;
import net.praqma.prqa.PRQACommandLineUtility;
import net.praqma.prqa.exceptions.PrqaSetupException;

/**
 *
 * @author Praqma
 */
public class QAV implements Product {
    
    public static final String QAV_UPLOAD_LOG = PRQACommandLineUtility.FILE_SEPARATOR+"qavupload.log";
    public static final String QAV_IMPORT_LOG = PRQACommandLineUtility.FILE_SEPARATOR+"qaimport.log";

    public QAV() { }

    @Override
    public String getProductVersion(HashMap<String,String> environment, File workspace) throws PrqaSetupException {
        return "Works fine";
    }
}
