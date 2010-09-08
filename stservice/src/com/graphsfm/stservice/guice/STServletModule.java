package com.graphsfm.stservice.guice;

import java.util.HashMap;

import javax.jdo.PersistenceManager;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.graphsfm.stservice.STServlet;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class STServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		// PersistenceManagerProvide is a @RequestScoped object
		bind(PersistenceManager.class).toProvider(
				PersistenceManagerProvider.class);

		bind(PersistenceFilter.class).in(Scopes.SINGLETON);
		filter("/*").through(PersistenceFilter.class);

		bind(STServlet.class).in(Scopes.SINGLETON);
		serve("/test").with(STServlet.class);

		bind(GuiceContainer.class).in(Scopes.SINGLETON);
		HashMap<String, String> initParams = new HashMap<String, String>();
		initParams.put("javax.ws.rs.Application",
				com.graphsfm.stservice.STRestApplication.class.getName());
		serve("/rest/*").with(GuiceContainer.class, initParams);
	}
}
