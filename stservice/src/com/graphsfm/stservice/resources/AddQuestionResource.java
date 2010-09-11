package com.graphsfm.stservice.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;

@Path("/question")
public class AddQuestionResource {
	private TriviaService triviaService;

	@Inject
	public void setTriviaService(TriviaService triviaService) {
		this.triviaService = triviaService;
	}
	
	@PUT
	@Produces({ "application/json", "application/xml" })
	public void addQuestion(
			@FormParam("text") String text,
			@FormParam("uid") String uid) {
		triviaService.addQuestion(uid, text);
	}
}
