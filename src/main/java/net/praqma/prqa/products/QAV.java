/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.util.HashMap;
import net.praqma.prqa.exceptions.PrqaSetupException;

/**
 *
 * @author Praqma
 */
public class QAV implements Product {
    
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String QAV_UPLOAD_LOG = QAV.FILE_SEPARATOR+"qavupload.log";
    public static final String QAV_IMPORT_LOG = QAV.FILE_SEPARATOR+"qaimport.log";

    public QAV() { }

    @Override
    public final String getProductVersion(HashMap<String,String> environment, File workspace, boolean isUnix) throws PrqaSetupException {
        return "Works fine";
    }
}

