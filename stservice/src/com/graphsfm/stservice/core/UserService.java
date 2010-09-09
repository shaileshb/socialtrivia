package com.graphsfm.stservice.core;

import com.graphsfm.stservice.data.User;

public interface UserService {
	public User getUser(String userkey);
	public User createUser();
}
