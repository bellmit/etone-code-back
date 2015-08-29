/**
 * SSOCheckLoginIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.sso;

public class SSOCheckLoginIn  implements java.io.Serializable {
    private java.lang.String gmccSystemID;

    private java.lang.String loginID;

    private java.lang.String passWord;

    public SSOCheckLoginIn() {
    }

    public SSOCheckLoginIn(
           java.lang.String gmccSystemID,
           java.lang.String loginID,
           java.lang.String passWord) {
           this.gmccSystemID = gmccSystemID;
           this.loginID = loginID;
           this.passWord = passWord;
    }


    /**
     * Gets the gmccSystemID value for this SSOCheckLoginIn.
     * 
     * @return gmccSystemID
     */
    public java.lang.String getGmccSystemID() {
        return gmccSystemID;
    }


    /**
     * Sets the gmccSystemID value for this SSOCheckLoginIn.
     * 
     * @param gmccSystemID
     */
    public void setGmccSystemID(java.lang.String gmccSystemID) {
        this.gmccSystemID = gmccSystemID;
    }


    /**
     * Gets the loginID value for this SSOCheckLoginIn.
     * 
     * @return loginID
     */
    public java.lang.String getLoginID() {
        return loginID;
    }


    /**
     * Sets the loginID value for this SSOCheckLoginIn.
     * 
     * @param loginID
     */
    public void setLoginID(java.lang.String loginID) {
        this.loginID = loginID;
    }


    /**
     * Gets the passWord value for this SSOCheckLoginIn.
     * 
     * @return passWord
     */
    public java.lang.String getPassWord() {
        return passWord;
    }


    /**
     * Sets the passWord value for this SSOCheckLoginIn.
     * 
     * @param passWord
     */
    public void setPassWord(java.lang.String passWord) {
        this.passWord = passWord;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSOCheckLoginIn)) return false;
        SSOCheckLoginIn other = (SSOCheckLoginIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.gmccSystemID==null && other.getGmccSystemID()==null) || 
             (this.gmccSystemID!=null &&
              this.gmccSystemID.equals(other.getGmccSystemID()))) &&
            ((this.loginID==null && other.getLoginID()==null) || 
             (this.loginID!=null &&
              this.loginID.equals(other.getLoginID()))) &&
            ((this.passWord==null && other.getPassWord()==null) || 
             (this.passWord!=null &&
              this.passWord.equals(other.getPassWord())));
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
        if (getGmccSystemID() != null) {
            _hashCode += getGmccSystemID().hashCode();
        }
        if (getLoginID() != null) {
            _hashCode += getLoginID().hashCode();
        }
        if (getPassWord() != null) {
            _hashCode += getPassWord().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSOCheckLoginIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "SSOCheckLoginIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gmccSystemID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "gmccSystemID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "loginID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passWord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "passWord"));
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
