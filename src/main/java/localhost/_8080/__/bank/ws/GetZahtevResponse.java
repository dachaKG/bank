//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.05 at 11:09:16 PM CEST 
//


package localhost._8080.__.bank.ws;

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
 *         &lt;element name="ZahtevZaDobijanjeIzvoda" type="{http://localhost:8080/#/bank/ws/}Zahtev_za_dobijanje_izvoda"/>
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
    "zahtevZaDobijanjeIzvoda"
})
@XmlRootElement(name = "getZahtevResponse")
public class GetZahtevResponse {

    @XmlElement(name = "ZahtevZaDobijanjeIzvoda", required = true)
    protected ZahtevZaDobijanjeIzvoda zahtevZaDobijanjeIzvoda;

    /**
     * Gets the value of the zahtevZaDobijanjeIzvoda property.
     * 
     * @return
     *     possible object is
     *     {@link ZahtevZaDobijanjeIzvoda }
     *     
     */
    public ZahtevZaDobijanjeIzvoda getZahtevZaDobijanjeIzvoda() {
        return zahtevZaDobijanjeIzvoda;
    }

    /**
     * Sets the value of the zahtevZaDobijanjeIzvoda property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZahtevZaDobijanjeIzvoda }
     *     
     */
    public void setZahtevZaDobijanjeIzvoda(ZahtevZaDobijanjeIzvoda value) {
        this.zahtevZaDobijanjeIzvoda = value;
    }

}
