package com.graphsfm.stservice.core;

import com.google.appengine.api.datastore.Key;
import com.graphsfm.stservice.data.User;

public interface UserService {
	public User getUser(String userkey);
	public User createUser();
	public User getUserById(Key userfk);
	public void saveAnswer(String uid, String qid, String answer);
}
