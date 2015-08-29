/**
 * GetOUInfoByDPIDIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public class GetOUInfoByDPIDIn  implements java.io.Serializable {
    private java.lang.String DPID;

    private boolean withInherit;

    public GetOUInfoByDPIDIn() {
    }

    public GetOUInfoByDPIDIn(
           java.lang.String DPID,
           boolean withInherit) {
           this.DPID = DPID;
           this.withInherit = withInherit;
    }


    /**
     * Gets the DPID value for this GetOUInfoByDPIDIn.
     * 
     * @return DPID
     */
    public java.lang.String getDPID() {
        return DPID;
    }


    /**
     * Sets the DPID value for this GetOUInfoByDPIDIn.
     * 
     * @param DPID
     */
    public void setDPID(java.lang.String DPID) {
        this.DPID = DPID;
    }


    /**
     * Gets the withInherit value for this GetOUInfoByDPIDIn.
     * 
     * @return withInherit
     */
    public boolean isWithInherit() {
        return withInherit;
    }


    /**
     * Sets the withInherit value for this GetOUInfoByDPIDIn.
     * 
     * @param withInherit
     */
    public void setWithInherit(boolean withInherit) {
        this.withInherit = withInherit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOUInfoByDPIDIn)) return false;
        GetOUInfoByDPIDIn other = (GetOUInfoByDPIDIn) obj;
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
            this.withInherit == other.isWithInherit();
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
        _hashCode += (isWithInherit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOUInfoByDPIDIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetOUInfoByDPIDIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("withInherit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "withInherit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
