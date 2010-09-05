package com.graphsfm.stservice.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.data.Question;

@Path("/user/{userid}/questions")
public class QuestionsResource {
	@Inject TriviaService triviaService;
	
	@GET
	@Produces({ "application/json", "application/xml" })
	public List<Question> getQuestions(
			@PathParam("userid") long userId,
			@QueryParam("limit") @DefaultValue("8") int limit) {
		ArrayList<Question> ret = new ArrayList<Question>();
		ret.add(new Question(1L, "", 1L, 0L));
		ret.add(new Question(1L, "injected value is " + triviaService, 1L, 0L));
		return ret;
	}
}
