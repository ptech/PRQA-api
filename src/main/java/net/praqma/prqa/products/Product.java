/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa.products;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import net.praqma.prqa.exceptions.PrqaSetupException;

/**
 *
 * @author Praqma
 */
public interface Product extends Serializable {
    String getProductVersion(HashMap<String,String> environment, File currentDirectory, boolean isUnix) throws PrqaSetupException;
}
