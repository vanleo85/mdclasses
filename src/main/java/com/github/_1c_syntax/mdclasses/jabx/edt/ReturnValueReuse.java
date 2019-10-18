//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.17 at 10:32:56 PM MSK 
//


package com.github._1c_syntax.mdclasses.jabx.edt;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for returnValueReuse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="returnValueReuse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DuringRequest"/>
 *     &lt;enumeration value="DuringSession"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "returnValueReuse")
@XmlEnum
public enum ReturnValueReuse {

    @XmlEnumValue("DuringRequest")
    DURING_REQUEST("DuringRequest"),
    @XmlEnumValue("DuringSession")
    DURING_SESSION("DuringSession");
    private final String value;

    ReturnValueReuse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReturnValueReuse fromValue(String v) {
        for (ReturnValueReuse c: ReturnValueReuse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
