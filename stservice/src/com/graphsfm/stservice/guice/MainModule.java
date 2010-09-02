package com.graphsfm.stservice.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.graphsfm.stservice.core.RealTriviaService;
import com.graphsfm.stservice.core.TriviaService;

public class MainModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(TriviaService.class).to(RealTriviaService.class).in(Singleton.class);
	}
}
