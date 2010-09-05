package com.graphsfm.stservice.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/sayhello")
public class TestResource {
	public TestResource() {
		super();
	}
	
	@GET
	@Produces({ "application/json", "application/xml" })
	public Greeting getGreeting(
			@QueryParam("username") @DefaultValue("world") String username) {
		Greeting g = new Greeting();
		g.setText("hello");
		g.setRecipient(username);
		return g;
	}
}
