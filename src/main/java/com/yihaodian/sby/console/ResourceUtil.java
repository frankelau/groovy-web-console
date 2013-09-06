package com.yihaodian.sby.console;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

public class ResourceUtil{
	public static String getFileAsString(String name){
		StringWriter writer = new StringWriter();
		Reader ts = new InputStreamReader(ScriptHandler.class.getClassLoader().getResourceAsStream(name));
		char[]  buf = new char[4096];
		int len =0;
		try {
			while((len =ts.read(buf))!=-1){
				writer.write(buf,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}
}
