/**
 * SSOSoap_BindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.sso;

public class SSOSoap_BindingStub extends org.apache.axis.client.Stub implements com.symbol.app.mantoeye.unmp.sso.SSOSoap_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SSOCheckLogon");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonIn"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("LoginCheck");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginIn"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LoginCheckResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnmpAndPortalLoginCheck");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginIn"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheckResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnmpAndPortalSSOCheckLogon");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonIn"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogonResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SSOCheckLogonTest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "tokenEx"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"));
        oper.setReturnClass(com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonTestResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SSOCheckLoginTest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccSystemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "loginID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "passWord"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"));
        oper.setReturnClass(com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginTestResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnmpAndPortalLoginCheckTest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccSystemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "loginID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "passWord"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"));
        oper.setReturnClass(com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheckTestResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnmpAndPortalSSOCheckLogonTest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "tokenEx"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"));
        oper.setReturnClass(com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogonTestResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UnmpAndPortalSSOCheckLogon2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonIn"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogon2Result"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut"), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public SSOSoap_BindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SSOSoap_BindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SSOSoap_BindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void SSOCheckLogon(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder SSOCheckLogonResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/SSOCheckLogon");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogon"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, inParam});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                SSOCheckLogonResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonResult"));
            } catch (java.lang.Exception _exception) {
                SSOCheckLogonResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonResult")), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void loginCheck(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder loginCheckResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/LoginCheck");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LoginCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, inParam});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                loginCheckResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LoginCheckResult"));
            } catch (java.lang.Exception _exception) {
                loginCheckResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LoginCheckResult")), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void unmpAndPortalLoginCheck(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalLoginCheckResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/UnmpAndPortalLoginCheck");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheck"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, inParam});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                unmpAndPortalLoginCheckResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheckResult"));
            } catch (java.lang.Exception _exception) {
                unmpAndPortalLoginCheckResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheckResult")), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void unmpAndPortalSSOCheckLogon(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalSSOCheckLogonResult, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/UnmpAndPortalSSOCheckLogon");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogon"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, inParam});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                unmpAndPortalSSOCheckLogonResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogonResult"));
            } catch (java.lang.Exception _exception) {
                unmpAndPortalSSOCheckLogonResult.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogonResult")), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut SSOCheckLogonTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String ip, java.lang.String token, java.lang.String tokenEx) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/SSOCheckLogonTest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLogonTest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, ip, token, tokenEx});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_resp, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.symbol.app.mantoeye.unmp.sso.UNMPCallResult SSOCheckLoginTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String gmccSystemID, java.lang.String loginID, java.lang.String passWord) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/SSOCheckLoginTest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginTest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, gmccSystemID, loginID, passWord});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.symbol.app.mantoeye.unmp.sso.UNMPCallResult unmpAndPortalLoginCheckTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String gmccSystemID, java.lang.String loginID, java.lang.String passWord) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/UnmpAndPortalLoginCheckTest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalLoginCheckTest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, gmccSystemID, loginID, passWord});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut unmpAndPortalSSOCheckLogonTest(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, java.lang.String ip, java.lang.String token, java.lang.String tokenEx) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/UnmpAndPortalSSOCheckLogonTest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogonTest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, ip, token, tokenEx});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_resp, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void unmpAndPortalSSOCheckLogon2(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn inParam, com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder unmpAndPortalSSOCheckLogon2Result, com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/UnmpAndPortalSSOCheckLogon2");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogon2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemID, sysAccount, sysPassword, inParam});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                unmpAndPortalSSOCheckLogon2Result.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogon2Result"));
            } catch (java.lang.Exception _exception) {
                unmpAndPortalSSOCheckLogon2Result.value = (com.symbol.app.mantoeye.unmp.sso.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UnmpAndPortalSSOCheckLogon2Result")), com.symbol.app.mantoeye.unmp.sso.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
