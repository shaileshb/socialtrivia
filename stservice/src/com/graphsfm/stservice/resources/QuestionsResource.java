package com.graphsfm.stservice.resources;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.graphsfm.stservice.data.Question;

@Path("/user/{userid}/questions")
public class QuestionsResource {
	public List<Question> getQuestions(
			@PathParam("userid") long userId,
			@QueryParam("limit") int limit) {
		return null;
	}
}
