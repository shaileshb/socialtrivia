package com.graphsfm.stservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.UserService;
import com.graphsfm.stservice.data.User;

@Path("/user/{userkey}")
public class UserResource {
	private UserService userService;

	@Inject
	protected void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GET
	@Produces({ "application/json", "application/xml" })
	public User getUser(@PathParam("userkey") String userkey) {
		User u = userService.getUser(userkey);
		if (u == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		return u;
	}
}
