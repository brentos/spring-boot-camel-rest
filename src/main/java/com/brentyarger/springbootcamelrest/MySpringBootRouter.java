package com.brentyarger.springbootcamelrest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxrs.AbstractJAXRSFactoryBean;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class MySpringBootRouter extends FatJarRouter {

	@Autowired
	private ApplicationContext applicationContext;
	
    @Override
    public void configure() {
        
        from("cxfrs:bean:rsServer")
        .process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setBody("Hello World");
			}
        	
        });
        
    }

    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
    	
    	return new ServletRegistrationBean(new CXFServlet(), "/api/*");
    	
    	
    }
    
    
    @Bean
    public AbstractJAXRSFactoryBean rsServer() {
    	Bus bus = (Bus)applicationContext.getBean(Bus.DEFAULT_BUS_ID);
    	JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
    	sf.setResourceClasses(HelloService.class);
    	sf.setAddress("/");
    	sf.setBus(bus);
    	sf.setProvider(new JacksonJaxbJsonProvider());
    	return sf;
    	
    }
    
//    @Bean
//    public Component restletComponent() {
//    	return new Component();
//    }
    
//    @Bean
//    public RestletComponent restletComponentService() {
//    	return new RestletComponent(restletComponent());
//    }

}
