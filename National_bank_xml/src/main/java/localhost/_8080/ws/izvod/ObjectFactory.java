//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.11 at 03:22:09 PM CEST 
//


package localhost._8080.ws.izvod;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the localhost._8080.ws.izvod package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: localhost._8080.ws.izvod
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPresekRequest }
     * 
     */
    public GetPresekRequest createGetPresekRequest() {
        return new GetPresekRequest();
    }

    /**
     * Create an instance of {@link GetPresekResponse }
     * 
     */
    public GetPresekResponse createGetPresekResponse() {
        return new GetPresekResponse();
    }

    /**
     * Create an instance of {@link Presek }
     * 
     */
    public Presek createPresek() {
        return new Presek();
    }

    /**
     * Create an instance of {@link StavkaPreseka }
     * 
     */
    public StavkaPreseka createStavkaPreseka() {
        return new StavkaPreseka();
    }

    /**
     * Create an instance of {@link ZaglavljePreseka }
     * 
     */
    public ZaglavljePreseka createZaglavljePreseka() {
        return new ZaglavljePreseka();
    }

}
