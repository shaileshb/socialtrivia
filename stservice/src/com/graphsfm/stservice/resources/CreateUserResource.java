package com.graphsfm.stservice.resources;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.UserService;
import com.graphsfm.stservice.data.User;

@Path("/user/create")
public class CreateUserResource {
	private UserService userService;

	@Inject
	protected void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PUT
	@Produces({ "application/json", "application/xml" })
	public User createUser() {
		return userService.createUser();
	}
}
