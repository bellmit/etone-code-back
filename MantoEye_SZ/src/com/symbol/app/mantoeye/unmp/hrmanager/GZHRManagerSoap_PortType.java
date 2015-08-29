/**
 * GZHRManagerSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public interface GZHRManagerSoap_PortType extends java.rmi.Remote {

    /**
     * 获取所有用户的详细信息
     */
    public void getAllUserInfo(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetAllUserInfoIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getAllUserInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetAllUserInfoOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * 获取平台所有部门的详细信息
     */
    public void getAllOUInfo(java.lang.String systemID, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetAllOUInfoIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getAllOUInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetAllOUInfoOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * 根据用户标识号（员工编号）获取用户的信息
     */
    public void getUserInfoByUserID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByUserIDIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByUserIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByUserIDOutHolder outParm) throws java.rmi.RemoteException;

    /**
     * 获取指定用户所在的组织单元信息
     */
    public void getOUInfoByUserID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByUserIDIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getOUInfoByUserIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetOUInfoByUserIDOutHolder outParm) throws java.rmi.RemoteException;

    /**
     * 获取领导分管的详细信息
     */
    public void getManagerInfo(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetManagerInfoIn inParm, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getManagerInfoResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetManagerInfoOutHolder outParm) throws java.rmi.RemoteException;

    /**
     * 根据用户登录账号获取用户的信息
     */
    public void getUserInfoByAccount(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByAccountIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByAccountResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByAccountOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * 根据省公司到UNMP的映射关系得到UNMP的UserID
     */
    public com.symbol.app.mantoeye.unmp.hrmanager.UNMPCallResult getUNMPUserIDByAccount(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, javax.xml.rpc.holders.StringHolder gmccLoginID) throws java.rmi.RemoteException;

    /**
     * 获取指定部门/科室的所有用户信息
     */
    public void getUserInfoByDPID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetUserInfoByDPIDIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getUserInfoByDPIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetUserInfoByDPIDOutHolder outParam) throws java.rmi.RemoteException;

    /**
     * 通过组织单元编号获取组织单元及下级单元的信息
     */
    public void getOUInfoByDPID(java.lang.String systemid, java.lang.String sysAccount, java.lang.String sysPassword, com.symbol.app.mantoeye.unmp.hrmanager.GetOUInfoByDPIDIn inParam, com.symbol.app.mantoeye.unmp.hrmanager.holders.UNMPCallResultHolder getOUInfoByDPIDResult, com.symbol.app.mantoeye.unmp.hrmanager.holders.GetOUInfoByDPIDOutHolder outParam) throws java.rmi.RemoteException;
}
