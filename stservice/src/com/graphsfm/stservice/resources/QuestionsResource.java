package com.graphsfm.stservice.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.appengine.api.datastore.Key;
import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.core.UserService;
import com.graphsfm.stservice.data.Question;
import com.graphsfm.stservice.data.User;
import com.graphsfm.stservice.resources.data.QuestionInfo;

@Path("/user/{uid}/questions")
public class QuestionsResource {
	private static Logger log = Logger.getLogger(QuestionsResource.class.getName());
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
	
	@GET
	@Produces({ "application/json", "application/xml" })
	public List<QuestionInfo> getQuestions(
			@PathParam("uid") String uid,
			@QueryParam("limit") @DefaultValue("8") int limit) {
		List<Question> qlist = triviaService.getNextQuestions(uid, limit);
		if (qlist == null || qlist.size() == 0) {
			log.warning("Could not fetch any questions for userid = " + uid);
			return null;
		}
		HashMap<Key, User> umap = new HashMap<Key, User>();
		List<QuestionInfo> ret = new ArrayList<QuestionInfo>();
		for (Question q : qlist) {
			User u = umap.get(q.getUserfk());
			if (u == null) {
				u = userService.getUserById(q.getUserfk());
				umap.put(q.getUserfk(), u);
			}
			QuestionInfo qi = new QuestionInfo(q, u);
			ret.add(qi);
		}
		return ret;
	}
}
