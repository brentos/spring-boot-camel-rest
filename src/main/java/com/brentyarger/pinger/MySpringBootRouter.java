package com.brentyarger.pinger;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletComponent;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.spring.boot.FatJarRouter;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootRouter extends FatJarRouter {

    @Override
    public void configure() {
    	
    	restConfiguration().component("restlet");
    	
//        from("timer://trigger").
//                transform().simple("ref:myBean").
//                to("log:out", "mock:test");
        
        
        rest("/test").produces("application/json").get().to("direct:hello");
        
        from("direct:hello").transform().simple("Hello World!");
        
        
    }
//
//    @Bean
//    public ServletContextInitializer initializer() {
//    	return new ServletContextInitializer() {
//
//			public void onStartup(ServletContext servletContext)
//					throws ServletException {
//				servletContext.setInitParameter("org.restlet.component", "RestletComponent");
//			}
//    		
//    	};
//    }
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
    	
    	SpringServerServlet serverServlet = new SpringServerServlet();
    	ServletRegistrationBean regBean = new ServletRegistrationBean( serverServlet, "/rest/*");
    	
    	
    	Map<String,String> params = new HashMap<String,String>();
    	
    	params.put("org.restlet.component", "restletComponent");
    	
    	regBean.setInitParameters(params);
    	
    	return regBean;
    }
    
    
    @Bean
    public Component restletComponent() {
    	return new Component();
    }
    
    @Bean
    public RestletComponent restletComponentService() {
    	return new RestletComponent(restletComponent());
    }
    
//    @Bean
//    String myBean() {
//        return "I'm Spring bean!";
//    }

}
