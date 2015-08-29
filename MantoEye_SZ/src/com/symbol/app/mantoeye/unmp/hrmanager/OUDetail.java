/**
 * OUDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public class OUDetail  implements java.io.Serializable {
    private java.lang.String DPID;

    private java.lang.String DPName;

    private java.lang.String parentDPID;

    private java.lang.String DPFullName;

    private java.lang.String DPCode;

    private java.lang.String isTmpDP;

    private java.lang.String deptOrderNo;

    public OUDetail() {
    }

    public OUDetail(
           java.lang.String DPID,
           java.lang.String DPName,
           java.lang.String parentDPID,
           java.lang.String DPFullName,
           java.lang.String DPCode,
           java.lang.String isTmpDP,
           java.lang.String deptOrderNo) {
           this.DPID = DPID;
           this.DPName = DPName;
           this.parentDPID = parentDPID;
           this.DPFullName = DPFullName;
           this.DPCode = DPCode;
           this.isTmpDP = isTmpDP;
           this.deptOrderNo = deptOrderNo;
    }


    /**
     * Gets the DPID value for this OUDetail.
     * 
     * @return DPID
     */
    public java.lang.String getDPID() {
        return DPID;
    }


    /**
     * Sets the DPID value for this OUDetail.
     * 
     * @param DPID
     */
    public void setDPID(java.lang.String DPID) {
        this.DPID = DPID;
    }


    /**
     * Gets the DPName value for this OUDetail.
     * 
     * @return DPName
     */
    public java.lang.String getDPName() {
        return DPName;
    }


    /**
     * Sets the DPName value for this OUDetail.
     * 
     * @param DPName
     */
    public void setDPName(java.lang.String DPName) {
        this.DPName = DPName;
    }


    /**
     * Gets the parentDPID value for this OUDetail.
     * 
     * @return parentDPID
     */
    public java.lang.String getParentDPID() {
        return parentDPID;
    }


    /**
     * Sets the parentDPID value for this OUDetail.
     * 
     * @param parentDPID
     */
    public void setParentDPID(java.lang.String parentDPID) {
        this.parentDPID = parentDPID;
    }


    /**
     * Gets the DPFullName value for this OUDetail.
     * 
     * @return DPFullName
     */
    public java.lang.String getDPFullName() {
        return DPFullName;
    }


    /**
     * Sets the DPFullName value for this OUDetail.
     * 
     * @param DPFullName
     */
    public void setDPFullName(java.lang.String DPFullName) {
        this.DPFullName = DPFullName;
    }


    /**
     * Gets the DPCode value for this OUDetail.
     * 
     * @return DPCode
     */
    public java.lang.String getDPCode() {
        return DPCode;
    }


    /**
     * Sets the DPCode value for this OUDetail.
     * 
     * @param DPCode
     */
    public void setDPCode(java.lang.String DPCode) {
        this.DPCode = DPCode;
    }


    /**
     * Gets the isTmpDP value for this OUDetail.
     * 
     * @return isTmpDP
     */
    public java.lang.String getIsTmpDP() {
        return isTmpDP;
    }


    /**
     * Sets the isTmpDP value for this OUDetail.
     * 
     * @param isTmpDP
     */
    public void setIsTmpDP(java.lang.String isTmpDP) {
        this.isTmpDP = isTmpDP;
    }


    /**
     * Gets the deptOrderNo value for this OUDetail.
     * 
     * @return deptOrderNo
     */
    public java.lang.String getDeptOrderNo() {
        return deptOrderNo;
    }


    /**
     * Sets the deptOrderNo value for this OUDetail.
     * 
     * @param deptOrderNo
     */
    public void setDeptOrderNo(java.lang.String deptOrderNo) {
        this.deptOrderNo = deptOrderNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OUDetail)) return false;
        OUDetail other = (OUDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.DPID==null && other.getDPID()==null) || 
             (this.DPID!=null &&
              this.DPID.equals(other.getDPID()))) &&
            ((this.DPName==null && other.getDPName()==null) || 
             (this.DPName!=null &&
              this.DPName.equals(other.getDPName()))) &&
            ((this.parentDPID==null && other.getParentDPID()==null) || 
             (this.parentDPID!=null &&
              this.parentDPID.equals(other.getParentDPID()))) &&
            ((this.DPFullName==null && other.getDPFullName()==null) || 
             (this.DPFullName!=null &&
              this.DPFullName.equals(other.getDPFullName()))) &&
            ((this.DPCode==null && other.getDPCode()==null) || 
             (this.DPCode!=null &&
              this.DPCode.equals(other.getDPCode()))) &&
            ((this.isTmpDP==null && other.getIsTmpDP()==null) || 
             (this.isTmpDP!=null &&
              this.isTmpDP.equals(other.getIsTmpDP()))) &&
            ((this.deptOrderNo==null && other.getDeptOrderNo()==null) || 
             (this.deptOrderNo!=null &&
              this.deptOrderNo.equals(other.getDeptOrderNo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDPID() != null) {
            _hashCode += getDPID().hashCode();
        }
        if (getDPName() != null) {
            _hashCode += getDPName().hashCode();
        }
        if (getParentDPID() != null) {
            _hashCode += getParentDPID().hashCode();
        }
        if (getDPFullName() != null) {
            _hashCode += getDPFullName().hashCode();
        }
        if (getDPCode() != null) {
            _hashCode += getDPCode().hashCode();
        }
        if (getIsTmpDP() != null) {
            _hashCode += getIsTmpDP().hashCode();
        }
        if (getDeptOrderNo() != null) {
            _hashCode += getDeptOrderNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OUDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "OUDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentDPID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "ParentDPID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPFullName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPFullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isTmpDP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "IsTmpDP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deptOrderNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "deptOrderNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
