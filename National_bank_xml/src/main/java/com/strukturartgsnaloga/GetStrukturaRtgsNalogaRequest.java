//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.12 at 01:17:19 AM CEST 
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
 *         &lt;element name="strukturaRtgsNaloga" type="{http://strukturaRtgsNaloga.com}struktura_rtgs_naloga"/>
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
    "strukturaRtgsNaloga"
})
@XmlRootElement(name = "getStrukturaRtgsNalogaRequest")
public class GetStrukturaRtgsNalogaRequest {

    @XmlElement(required = true)
    protected StrukturaRtgsNaloga strukturaRtgsNaloga;

    /**
     * Gets the value of the strukturaRtgsNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link StrukturaRtgsNaloga }
     *     
     */
    public StrukturaRtgsNaloga getStrukturaRtgsNaloga() {
        return strukturaRtgsNaloga;
    }

    /**
     * Sets the value of the strukturaRtgsNaloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrukturaRtgsNaloga }
     *     
     */
    public void setStrukturaRtgsNaloga(StrukturaRtgsNaloga value) {
        this.strukturaRtgsNaloga = value;
    }

}
