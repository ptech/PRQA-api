/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

/**
 *
 * @author Praqma Very generic interface which all classes implementing the PRQA report should implement.
 */
public interface PRQATask<T,K> {
    public T completeTask(K parameter);  
}
