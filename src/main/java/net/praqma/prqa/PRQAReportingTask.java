/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import net.praqma.jenkins.plugin.prqa.PrqaException;

/**
 *
 * @author Praqma Very generic interface which all classes implementing the PRQA report should implement.
 * 
 * 
 */
public interface PRQAReportingTask<T,K> {
    public String getNamingTemplate();
    public String getFullReportPath();
    public T completeTask(K parameter) throws PrqaException;
    public T completeTask() throws PrqaException;
}
