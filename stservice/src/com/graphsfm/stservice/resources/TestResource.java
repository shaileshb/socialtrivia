package com.graphsfm.stservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/sayhello/{username}")
public class TestResource {
	@GET
	@Produces({"application/json", "application/xml"})
	public Greeting getGreeting(@PathParam("username") String username) {
		Greeting g = new Greeting();
		g.setText("Hello");
		g.setRecipient(username);
		return g;
	}
}
