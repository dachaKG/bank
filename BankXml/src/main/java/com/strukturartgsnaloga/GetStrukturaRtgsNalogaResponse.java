//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.13 at 12:14:26 AM CEST 
//


package com.strukturartgsnaloga;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mt900" type="{http://strukturaRtgsNaloga.com}mt900"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mt900"
})
@XmlRootElement(name = "getStrukturaRtgsNalogaResponse")
public class GetStrukturaRtgsNalogaResponse {

    @XmlElement(required = true)
    protected Mt900 mt900;

    /**
     * Gets the value of the mt900 property.
     * 
     * @return
     *     possible object is
     *     {@link Mt900 }
     *     
     */
    public Mt900 getMt900() {
        return mt900;
    }

    /**
     * Sets the value of the mt900 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt900 }
     *     
     */
    public void setMt900(Mt900 value) {
        this.mt900 = value;
    }

}
