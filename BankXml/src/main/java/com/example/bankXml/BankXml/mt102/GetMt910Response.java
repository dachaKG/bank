//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.13 at 12:14:26 AM CEST 
//


package com.example.bankXml.BankXml.mt102;

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
 *         &lt;element name="mt910" type="{http://mt102.BankXml.bankXml.example.com}mt910"/>
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
    "mt910"
})
@XmlRootElement(name = "getMt910Response")
public class GetMt910Response {

    @XmlElement(required = true)
    protected Mt910 mt910;

    /**
     * Gets the value of the mt910 property.
     * 
     * @return
     *     possible object is
     *     {@link Mt910 }
     *     
     */
    public Mt910 getMt910() {
        return mt910;
    }

    /**
     * Sets the value of the mt910 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mt910 }
     *     
     */
    public void setMt910(Mt910 value) {
        this.mt910 = value;
    }

}
