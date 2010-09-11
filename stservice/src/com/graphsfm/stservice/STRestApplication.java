package com.graphsfm.stservice;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import com.graphsfm.stservice.resources.AddQuestionResource;
import com.graphsfm.stservice.resources.CreateUserResource;
import com.graphsfm.stservice.resources.QuestionsResource;
import com.graphsfm.stservice.resources.TestResource;
import com.graphsfm.stservice.resources.UserResource;
import com.graphsfm.stservice.resources.UserResponseResource;

public class STRestApplication extends Application {
	private static Logger log = Logger.getLogger(STRestApplication.class.getName());
	
	public STRestApplication() {
		log.info("Created STRestApplication");
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> resources = new HashSet<Class<?>>();
		
		resources.add(TestResource.class);
		resources.add(QuestionsResource.class);
		resources.add(AddQuestionResource.class);
		resources.add(CreateUserResource.class);
		resources.add(UserResource.class);
		resources.add(UserResponseResource.class);
		
		return resources;
	}
}
