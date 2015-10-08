package org.devefx.test.config;

import org.devefx.service.core.ServiceView;
import org.devefx.test.controller.HelloController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.render.ViewType;

public class DefaultConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		constants.setDevMode(true);
		constants.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes routes) {
		routes.add("/services", ServiceView.class, "/");
		
		routes.add("/hello", HelloController.class, "/");
	}
	
	@Override
	public void configHandler(Handlers handlers) {
		
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
		
	}

	@Override
	public void configPlugin(Plugins plugins) {
		
	}
}
