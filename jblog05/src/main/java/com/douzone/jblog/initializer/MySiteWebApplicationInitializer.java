package com.douzone.jblog.initializer;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.douzone.jblog.config.AppConfig;
import com.douzone.jblog.config.WebConfig;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		/* Root Application Context's Configuration Class */
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
		
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("UTF-8", false)};
	}

	/*Exception (No Handler의 경우 exception을 일으켜라 ) */
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		DispatcherServlet servlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		servlet.setThrowExceptionIfNoHandlerFound(true);
		return servlet;
	}
}
