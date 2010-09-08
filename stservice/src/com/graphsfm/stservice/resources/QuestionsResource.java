package com.graphsfm.stservice.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.data.Question;
import com.graphsfm.stservice.resources.data.QuestionInfo;

@Path("/user/{userid}/questions")
public class QuestionsResource {
	private static Logger log = Logger.getLogger(QuestionsResource.class.getName());
	private TriviaService triviaService;
	
	@Inject
	public void setTriviaService(TriviaService triviaService) {
		this.triviaService = triviaService;
	}
	
	@GET
	@Produces({ "application/json", "application/xml" })
	public List<QuestionInfo> getQuestions(
			@PathParam("userid") long userId,
			@QueryParam("limit") @DefaultValue("8") int limit) {
		List<Question> qlist = triviaService.getNextQuestions(userId, limit);
		if (qlist == null || qlist.size() == 0) {
			log.warning("Could not fetch any questions for userid = " + userId);
			return null;
		}
		List<QuestionInfo> ret = new ArrayList<QuestionInfo>();
		for (Question q : qlist) {
			QuestionInfo qi = new QuestionInfo(q);
			ret.add(qi);
		}
		return ret;
	}
}
