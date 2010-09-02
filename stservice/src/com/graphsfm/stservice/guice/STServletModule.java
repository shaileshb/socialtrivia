package com.graphsfm.stservice.guice;

import java.util.HashMap;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.graphsfm.stservice.STServlet;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class STServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		bind(STServlet.class).in(Scopes.SINGLETON);
		serve("/test").with(STServlet.class);
		
		HashMap<String,String> initParams = new HashMap<String, String>();
		initParams.put("javax.ws.rs.Application", com.graphsfm.stservice.STRestApplication.class.getName());
		bind(ServletContainer.class).in(Scopes.SINGLETON);
		serve("/rest/*").with(ServletContainer.class, initParams);
	}
}
