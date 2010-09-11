package com.graphsfm.stservice.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.core.UserService;

@Path("/user/{uid}/question/{qid}")
public class UserResponseResource {
	private TriviaService triviaService;
	private UserService userService;

	@Inject
	public void setTriviaService(TriviaService triviaService) {
		this.triviaService = triviaService;
	}

	@Inject
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PUT
	@Produces({ "application/json", "application/xml" })
	public void saveUserResponse(
			@PathParam("uid") String uid,
			@PathParam("qid") String qid,
			@FormParam("answer") String answer) {
		triviaService.saveAnswer(uid, qid, answer);
		userService.saveAnswer(uid, qid, answer);
	}
}
