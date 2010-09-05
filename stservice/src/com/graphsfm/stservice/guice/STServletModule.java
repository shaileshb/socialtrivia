package com.graphsfm.stservice.guice;

import java.util.HashMap;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.graphsfm.stservice.STServlet;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class STServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		bind(STServlet.class).in(Scopes.SINGLETON);
		serve("/test").with(STServlet.class);
		
		HashMap<String,String> initParams = new HashMap<String, String>();
		initParams.put("javax.ws.rs.Application", com.graphsfm.stservice.STRestApplication.class.getName());
		bind(GuiceContainer.class).in(Scopes.SINGLETON);
		serve("/rest/*").with(GuiceContainer.class, initParams);
	}
}
