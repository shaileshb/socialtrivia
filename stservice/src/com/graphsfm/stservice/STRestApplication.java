package com.graphsfm.stservice;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;

import com.graphsfm.stservice.resources.TestResource;

public class STRestApplication extends Application {
	private static Logger log = Logger.getLogger(STRestApplication.class.getName());
	
	public STRestApplication() {
		log.info("Created STRestApplication");
	}

	@Override
	public Set<Class<?>> getClasses() {
		log.info("STRestApplication.getClasses() called");
		HashSet<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(TestResource.class);
		return resources;
	}
}
