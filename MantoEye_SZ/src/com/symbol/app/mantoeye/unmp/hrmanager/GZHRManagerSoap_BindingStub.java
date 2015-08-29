/**
 * GZHRManagerSoap_BindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public class GZHRManagerSoap_BindingStub extends org.apache.axis.client.Stub implements com.symbol.app.mantoeye.unmp.hrmanager.GZHRManagerSoap_PortType {
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
        oper.setName("GetAllUserInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetAllOUInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUserInfoByUserID");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParm"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetOUInfoByUserID");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParm"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetManagerInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParm"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUserInfoByAccount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUNMPUserIDByAccount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccLoginID"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"));
        oper.setReturnClass(com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUNMPUserIDByAccountResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetUserInfoByDPID");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetOUInfoByDPID");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "systemid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysAccount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "sysPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "inParam"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDIn"), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDIn.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDResult"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult"), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDOut"), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDOut.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public GZHRManagerSoap_BindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public GZHRManagerSoap_BindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public GZHRManagerSoap_BindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ArrayOfLeaderDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail");
            qName2 = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ArrayOfOUDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.OUDetail[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUDetail");
            qName2 = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUDetail");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ArrayOfOUInfo");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.OUInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUInfo");
            qName2 = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ArrayOfUserDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.UserDetail[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserDetail");
            qName2 = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserDetail");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ArrayOfUserInfo");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.UserInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserInfo");
            qName2 = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDIn");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDIn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDOut");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDOut.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.OUDetail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUInfo");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.OUInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UNMPCallResult");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserDetail");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.UserDetail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserInfo");
            cachedSerQNames.add(qName);
            cls = com.symbol.app.mantoeye.unmp.hrmanager.UserInfo.class;
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

    public void getAllUserInfo(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getAllUserInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetAllUserInfoOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetAllUserInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfo"));

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
                getAllUserInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoResult"));
            } catch (java.lang.Exception _exception) {
                getAllUserInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllUserInfoResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAllOUInfo(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getAllOUInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetAllOUInfoOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetAllOUInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfo"));

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
                getAllOUInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoResult"));
            } catch (java.lang.Exception _exception) {
                getAllOUInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetAllOUInfoResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getUserInfoByUserID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByUserIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByUserIDOutHolder outParm) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetUserInfoByUserID");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, inParm});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getUserInfoByUserIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDResult"));
            } catch (java.lang.Exception _exception) {
                getUserInfoByUserIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByUserIDResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"));
            } catch (java.lang.Exception _exception) {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm")), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getOUInfoByUserID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getOUInfoByUserIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetOUInfoByUserIDOutHolder outParm) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetOUInfoByUserID");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserID"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, inParm});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getOUInfoByUserIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDResult"));
            } catch (java.lang.Exception _exception) {
                getOUInfoByUserIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByUserIDResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"));
            } catch (java.lang.Exception _exception) {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm")), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getManagerInfo(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getManagerInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetManagerInfoOutHolder outParm) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetManagerInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, inParm});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                getManagerInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoResult"));
            } catch (java.lang.Exception _exception) {
                getManagerInfoResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm"));
            } catch (java.lang.Exception _exception) {
                outParm.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParm")), com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getUserInfoByAccount(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByAccountResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByAccountOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetUserInfoByAccount");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccount"));

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
                getUserInfoByAccountResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountResult"));
            } catch (java.lang.Exception _exception) {
                getUserInfoByAccountResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByAccountResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult getUNMPUserIDByAccount(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, javax.xml.rpc.holders.StringHolder gmccLoginID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetUNMPUserIDByAccount");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUNMPUserIDByAccount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {systemid, sysAccount, sysPassword, gmccLoginID.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                gmccLoginID.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccLoginID"));
            } catch (java.lang.Exception _exception) {
                gmccLoginID.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccLoginID")), java.lang.String.class);
            }
            try {
                return (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getUserInfoByDPID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByDPIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByDPIDOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetUserInfoByDPID");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPID"));

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
                getUserInfoByDPIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDResult"));
            } catch (java.lang.Exception _exception) {
                getUserInfoByDPIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetUserInfoByDPIDResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getOUInfoByDPID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getOUInfoByDPIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetOUInfoByDPIDOutHolder outParam) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://iamsweb.gmcc.net/UNMPWS/GetOUInfoByDPID");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPID"));

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
                getOUInfoByDPIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDResult"));
            } catch (java.lang.Exception _exception) {
                getOUInfoByDPIDResult.value = (com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDResult")), com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult.class);
            }
            try {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDOut) _output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam"));
            } catch (java.lang.Exception _exception) {
                outParam.value = (com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDOut) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "outParam")), com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
