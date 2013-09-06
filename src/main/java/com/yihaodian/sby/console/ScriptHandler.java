package com.yihaodian.sby.console;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ScriptHandler{
	public void Handle(HttpServletRequest request, HttpServletResponse response) {
		String prefix = request.getRequestURL().toString();
		String code = request.getParameter("code");
		WebApplicationContext applicationContext=WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		String[] beans = applicationContext.getBeanDefinitionNames();
		Binding binding = new Binding();
		for (int i = 0; i < beans.length; i++) {
			if (!beans[i].equalsIgnoreCase("baseServiceProvider"))
				binding.setVariable(beans[i], applicationContext.getBean(beans[i]));
		}
		// build groovy tool
		GroovyClassLoader gcloader = new GroovyClassLoader(getClass().getClassLoader());
		try {
			gcloader.parseClass(ResourceUtil.getFileAsString("GroovyTool.groovy"));
		} catch (CompilationFailedException e) {
			e.printStackTrace();
		}
		CompilerConfiguration gcon = new CompilerConfiguration();
		gcon.setScriptBaseClass("GroovyTool");

		// binding together
		GroovyShell shell = new GroovyShell(gcloader, binding, gcon);
		Object re = null;
		try {
			if (code != null && code.length() > 0)
				re = shell.evaluate(code);
		} catch (Exception ex) {
			re = ex;
		}

			ToStringBuilder.getDefaultStyle();
			try {
				response.setHeader("content-type", "text/html; charset=UTF-8");
				HashMap<String , Object>   data = new HashMap<String,Object>();
				data.put("outtext", re);
				data.put("intext", code);
				data.put("prefix", prefix);
				this.getTemplate().process(data, response.getWriter());
				
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
	}
	private Template getTemplate(){
		Configuration cfg = new Configuration();
		StringTemplateLoader  sloader = new StringTemplateLoader();
		sloader.putTemplate("temp", ResourceUtil.getFileAsString("groovy.ftl"));
		cfg.setTemplateLoader(sloader);
		Template template =null;
		try {
			cfg.getTemplate("temp");
			template = cfg.getTemplate("temp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		 return template;
	}
}
