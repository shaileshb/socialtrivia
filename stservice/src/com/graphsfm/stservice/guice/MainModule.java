package com.graphsfm.stservice.guice;

import com.google.inject.AbstractModule;
import com.graphsfm.stservice.core.MediaClipService;
import com.graphsfm.stservice.core.MediaClipServiceImpl;
import com.graphsfm.stservice.core.RealTriviaService;
import com.graphsfm.stservice.core.TriviaService;
import com.graphsfm.stservice.core.UserService;
import com.graphsfm.stservice.core.UserServiceImpl;

public class MainModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(TriviaService.class).to(RealTriviaService.class);
		bind(UserService.class).to(UserServiceImpl.class);
		bind(MediaClipService.class).to(MediaClipServiceImpl.class);
	}
}
