package com.graphsfm.stservice.guice;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class PersistenceFilter implements Filter {
	private static Logger log = Logger.getLogger(PersistenceFilter.class.getName());
	
	@Inject
	private Injector injector;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			PersistenceManager pm = injector.getInstance(PersistenceManager.class);
			if (pm != null)
				pm.close();
			log.info("closed pm instance: " + pm.toString());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
