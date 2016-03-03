package com.brentyarger.springbootcamelrest;

import javax.annotation.PreDestroy;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.boot.FatJarRouter;
import org.restlet.Component;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootRouter extends FatJarRouter {

	PooledConnectionFactory pooledConnectionFactory;
	
	private String brokerUrl = "tcp://localhost:61616";
	private int maxConnections = 10;
	private int concurrentConsumers = 10;
	
    @Override
    public void configure() {
    	
    	registerActivemqComponent();
    	
    	from("activemq:queue:bjytest001?username=admin&password=admin")
    	.routeId("hollaback")
    	.to("bean:bytesEcho");

        
    }

    public void registerActivemqComponent() {
    	ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory();
    	amqConnectionFactory.setBrokerURL(brokerUrl);
    	
    	pooledConnectionFactory = new PooledConnectionFactory();
    	pooledConnectionFactory.setMaxConnections(maxConnections);
    	pooledConnectionFactory.setConnectionFactory(amqConnectionFactory);
    	
    	JmsConfiguration jmsConfig = new JmsConfiguration();
    	jmsConfig.setConnectionFactory(pooledConnectionFactory);
    	jmsConfig.setConcurrentConsumers(concurrentConsumers);
    	
    	ActiveMQComponent activemq = new ActiveMQComponent();
    	activemq.setConfiguration(jmsConfig);
    	
    	pooledConnectionFactory.start();
    	
    	this.getContext().addComponent("activemq", activemq);
    	
    	
    }
    
    @PreDestroy
    public void shutdown() {
    	
    	pooledConnectionFactory.stop();
    }
    
    @Bean
    public BytesMessageEcho bytesEcho() {
    	return new BytesMessageEcho();
    }
    
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//    	
//    	SpringServerServlet serverServlet = new SpringServerServlet();
//    	ServletRegistrationBean regBean = new ServletRegistrationBean( serverServlet, "/rest/*");
//    	
//    	
//    	Map<String,String> params = new HashMap<String,String>();
//    	
//    	params.put("org.restlet.component", "restletComponent");
//    	
//    	regBean.setInitParameters(params);
//    	
//    	return regBean;
//    }
//    
//    
//    @Bean
//    public Component restletComponent() {
//    	return new Component();
//    }
//    
//    @Bean
//    public RestletComponent restletComponentService() {
//    	return new RestletComponent(restletComponent());
//    }

}
