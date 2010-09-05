package com.graphsfm.stservice;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.graphsfm.stservice.core.TriviaService;

public class STServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(STServlet.class.getName());
	
	@Inject TriviaService triviaService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.info("Initializing STServlet");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().printf("trivia service: %s\n", triviaService);
		resp.getWriter().printf("query_string is: %s\n", req.getQueryString());
	}
}
