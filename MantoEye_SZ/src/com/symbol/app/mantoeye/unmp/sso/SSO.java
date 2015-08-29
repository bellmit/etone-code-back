/**
 * SSO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.sso;

public interface SSO extends javax.xml.rpc.Service {
    public java.lang.String getSSOSoapAddress();

    public com.symbol.app.mantoeye.unmp.sso.SSOSoap_PortType getSSOSoap() throws javax.xml.rpc.ServiceException;

    public com.symbol.app.mantoeye.unmp.sso.SSOSoap_PortType getSSOSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
