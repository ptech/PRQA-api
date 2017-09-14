/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.prqa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Praqma
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QAVerifyServerSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String host;
    private int port = 0;
    private String protocol;
    private String password;
    private String user;
}
