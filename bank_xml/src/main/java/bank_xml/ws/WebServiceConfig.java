package bank_xml.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{

	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "bank")
	public DefaultWsdl11Definition defaultWsdl11Definition(CommonsXsdSchemaCollection schemaCollection) throws Exception {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://localhost:8080/ws/nalogZaPlacanje");

		

	
		wsdl11Definition.setSchemaCollection(schemaCollection);
		//wsdl11Definition.setSchema(countriesSchema);

		return wsdl11Definition;
	}
	
	@Bean
	public CommonsXsdSchemaCollection schemeCollection() {
		CommonsXsdSchemaCollection collection =
				new CommonsXsdSchemaCollection(new Resource[] { new ClassPathResource("/nalogZaPlacanje.xsd"), new ClassPathResource("/strukturaRtgsNaloga.xsd")});
		collection.setInline(true);
		return collection;
	}	

/*	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("nalogZaPlacanje.xsd"));
	}*/	
	
/*	@Bean
	public CommonsXsdSchemaCollection schemaCollection() {
	    return new CommonsXsdSchemaCollection() {
	    
	        public XsdSchema[] getXsdSchemas() {
	            return new XsdSchema[]{new SimpleXsdSchema(new ClassPathResource("nalogZaPlacanje.xsd"))};
	        }

	        public XmlValidator createValidator() {
	            throw new UnsupportedOperationException();
	        }
	    };
	}*/
	
}
