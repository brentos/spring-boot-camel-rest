package com.brentyarger.pinger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.spring.boot.FatJarRouter;
import org.restlet.Request;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringBootRouter extends FatJarRouter {

    @Override
    public void configure() {
    	
    	restConfiguration().component("restlet").host("localhost").port(9000);
    	
//        from("timer://trigger").
//                transform().simple("ref:myBean").
//                to("log:out", "mock:test");
        
        
        rest("/test").produces("application/json").get().to("direct:hello");
        
        from("direct:hello").process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);
				exchange.getIn().setBody(request.getClientInfo().getUpstreamAddress());
			}
        	
        });
        
        
    }

//    @Bean
//    String myBean() {
//        return "I'm Spring bean!";
//    }

}
