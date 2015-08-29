/**
 * SSOSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.sso;

public interface SSOSoap_PortType extends java.rmi.Remote {

    /**
     * ½�¼¼풩
     */
    public void SSOCheckLogon(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder SSOCheckLogonResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩
     */
    public void loginCheck(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder loginCheckResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩,°�PºΊ¡Portal
     */
    public void unmpAndPortalLoginCheck(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalLoginCheckResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩,°�PºΊ¡Portal
     */
    public void unmpAndPortalSSOCheckLogon(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalSSOCheckLogonResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩²㋔
     */
    public com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut SSOCheckLogonTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String ip, java.lang.String token, java.lang.String tokenEx) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩²㋔
     */
    public com.symbol.app.mantoeye.unmp.sso.UNMPCallResult SSOCheckLoginTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String gmccSystemID, java.lang.String loginID, java.lang.String passWord) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩²㋔
     */
    public com.symbol.app.mantoeye.unmp.sso.UNMPCallResult unmpAndPortalLoginCheckTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String gmccSystemID, java.lang.String loginID, java.lang.String passWord) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩²㋔
     */
    public com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut unmpAndPortalSSOCheckLogonTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String ip, java.lang.String token, java.lang.String tokenEx) throws java.rmi.RemoteException;

    /**
     * ½�¼¼풩,°�PºΊ¡Portal
     */
    public void unmpAndPortalSSOCheckLogon2(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalSSOCheckLogon2Result, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException;
}
