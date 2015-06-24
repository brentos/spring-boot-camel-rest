Spring Boot Example
===================

This example shows how to create a project using camel-spring-boot, the REST DSL, and restlet.

The example creates a REST endpoint that returns a "Hello World" message. To test the example, navigate to http://localhost:8080/rest/hello

This example exposes Jolokia API and Spring Boot actuators endpoints (like metrics) via the webmvc endpoint. We consider
this as the best practice - Spring Boot applications with these API exposed can be easily monitored and managed by the
3rd parties tools.

We recommend to package your application as a fat WAR. Fat WARs can be executed just as regular fat jars, but you can also
deploy them to the servlet containers like Tomcat. Fat WAR approach gives you the deployment flexibility, so we highly
recommend it.

You will need to compile this example first:
  mvn install

To run the example type
  mvn spring-boot:run

You can also execute the fat WAR directly:

  java -jar target/spring-boot-camel-rest-1.0-SNAPSHOT.war

To stop the example hit ctrl + c

Additionally, this example contains the necessary .openshift files to deploy to OpenShift online in a DIY cartridge.

For more help see the Apache Camel documentation

    http://camel.apache.org/

