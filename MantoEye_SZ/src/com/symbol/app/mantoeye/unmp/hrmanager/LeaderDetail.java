/**
 * LeaderDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public class LeaderDetail  implements java.io.Serializable {
    private java.lang.String DPID;

    private java.lang.String POS;

    private java.lang.String userID;

    private java.lang.String sequence;

    public LeaderDetail() {
    }

    public LeaderDetail(
           java.lang.String DPID,
           java.lang.String POS,
           java.lang.String userID,
           java.lang.String sequence) {
           this.DPID = DPID;
           this.POS = POS;
           this.userID = userID;
           this.sequence = sequence;
    }


    /**
     * Gets the DPID value for this LeaderDetail.
     * 
     * @return DPID
     */
    public java.lang.String getDPID() {
        return DPID;
    }


    /**
     * Sets the DPID value for this LeaderDetail.
     * 
     * @param DPID
     */
    public void setDPID(java.lang.String DPID) {
        this.DPID = DPID;
    }


    /**
     * Gets the POS value for this LeaderDetail.
     * 
     * @return POS
     */
    public java.lang.String getPOS() {
        return POS;
    }


    /**
     * Sets the POS value for this LeaderDetail.
     * 
     * @param POS
     */
    public void setPOS(java.lang.String POS) {
        this.POS = POS;
    }


    /**
     * Gets the userID value for this LeaderDetail.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this LeaderDetail.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the sequence value for this LeaderDetail.
     * 
     * @return sequence
     */
    public java.lang.String getSequence() {
        return sequence;
    }


    /**
     * Sets the sequence value for this LeaderDetail.
     * 
     * @param sequence
     */
    public void setSequence(java.lang.String sequence) {
        this.sequence = sequence;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LeaderDetail)) return false;
        LeaderDetail other = (LeaderDetail) obj;
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
            ((this.POS==null && other.getPOS()==null) || 
             (this.POS!=null &&
              this.POS.equals(other.getPOS()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            ((this.sequence==null && other.getSequence()==null) || 
             (this.sequence!=null &&
              this.sequence.equals(other.getSequence())));
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
        if (getPOS() != null) {
            _hashCode += getPOS().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        if (getSequence() != null) {
            _hashCode += getSequence().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeaderDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DPID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "DPID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("POS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "POS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "UserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "Sequence"));
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
