package bank.firm.ws;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);

		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "firm")
	public DefaultWsdl11Definition defaultWsdl11Definition(CommonsXsdSchemaCollection schemaCollection)
			throws Exception {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("FirmPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://firm_xml.com/ws/");

		wsdl11Definition.setSchemaCollection(schemaCollection);
		// wsdl11Definition.setSchema(countriesSchema);

		return wsdl11Definition;
	}

	@Bean
	public CommonsXsdSchemaCollection schemeCollection() {
		CommonsXsdSchemaCollection collection = new CommonsXsdSchemaCollection(
				new Resource[] { new ClassPathResource("/nalogZaPlacanje.xsd") });

		collection.setInline(true);
		return collection;
	}

	@Bean
	Jaxb2Marshaller jaxb2Marshaller() {

		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.nalogzaplacanje");
		return jaxb2Marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate() {

		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
		webServiceTemplate.setDefaultUri("http://localhost:8080/ws");

		return webServiceTemplate;
	}
	
	@Bean
	public WSTemplate wsTemplate() {

		WSTemplate webServiceTemplate = new WSTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
		webServiceTemplate.setDefaultUri("http://localhost:8080/ws");

		return webServiceTemplate;
	}
	/*
	@Bean
	  public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	          SecurityConstraint securityConstraint = new SecurityConstraint();
	          securityConstraint.setUserConstraint("CONFIDENTIAL");
	          SecurityCollection collection = new SecurityCollection();
	          collection.addPattern("/*");
	          securityConstraint.addCollection(collection);
	          context.addConstraint(securityConstraint);
	        }
	      };
	    
	    tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
	    return tomcat;
	  }
	  */
	  private Connector initiateHttpConnector() {
	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	    connector.setScheme("http");
	    connector.setPort(8080);
	    connector.setSecure(false);
	    connector.setRedirectPort(8443);
	    
	    return connector;
	  }

}
