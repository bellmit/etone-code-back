/**
 * GetManagerInfoOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.symbol.app.mantoeye.unmp.hrmanager;

public class GetManagerInfoOut  implements java.io.Serializable {
    private com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail[] leaderInfo;

    public GetManagerInfoOut() {
    }

    public GetManagerInfoOut(
           com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail[] leaderInfo) {
           this.leaderInfo = leaderInfo;
    }


    /**
     * Gets the leaderInfo value for this GetManagerInfoOut.
     * 
     * @return leaderInfo
     */
    public com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail[] getLeaderInfo() {
        return leaderInfo;
    }


    /**
     * Sets the leaderInfo value for this GetManagerInfoOut.
     * 
     * @param leaderInfo
     */
    public void setLeaderInfo(com.symbol.app.mantoeye.unmp.hrmanager.LeaderDetail[] leaderInfo) {
        this.leaderInfo = leaderInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetManagerInfoOut)) return false;
        GetManagerInfoOut other = (GetManagerInfoOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.leaderInfo==null && other.getLeaderInfo()==null) || 
             (this.leaderInfo!=null &&
              java.util.Arrays.equals(this.leaderInfo, other.getLeaderInfo())));
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
        if (getLeaderInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLeaderInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLeaderInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetManagerInfoOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "GetManagerInfoOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leaderInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "leaderInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://iamsweb.gmcc.net/UNMPWS/", "LeaderDetail"));
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
