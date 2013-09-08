package com.yihaodian.sby.console;

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

/**
 * 
 * @author 陈作平(zuopingchen@163.com)
 * @date 2013年9月7日
 * 
 */
public class ScriptHandler {
	public void Handle(HttpServletRequest request, HttpServletResponse response) {
		String prefix = request.getRequestURL().toString();
		String code = request.getParameter("code");
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		String[] beans = applicationContext.getBeanDefinitionNames();
		Binding binding = new Binding();
		for (int i = 0; i < beans.length; i++) {

			/*
			 * when there is a abstract spring bean XX (set abstract attr true)
			 * in applicationContext the getBean(XX) mathed will throw exception
			 * and the process end so use try catch to solve this problem
			 */
			try {
				Object abean = applicationContext.getBean(beans[i]);
				binding.setVariable(beans[i], abean);
			} catch (Exception e) {
			}

		}
		// build groovy tool
		GroovyClassLoader gcloader = new GroovyClassLoader(getClass().getClassLoader());
		CompilerConfiguration gcon = new CompilerConfiguration();
		gcon.setScriptBaseClass("com.yihaodian.sby.console.ScriptTool");
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
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("outtext", re);
			data.put("intext", code);
			data.put("prefix", prefix);
			response.getWriter().write(this.getPage(data));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPage(HashMap<String, Object> data) {
		String template = ResourceUtil.getFileAsString("groovy.ftl");
		for (String key : data.keySet()) {
			template = template.replaceAll("\\$\\{" + key + "\\}", data.get(key) == null ? "" : data.get(key).toString());
		}
		return template.toString();
	}
}
