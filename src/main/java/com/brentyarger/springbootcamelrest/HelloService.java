package com.brentyarger.springbootcamelrest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public interface HelloService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() ;
}
