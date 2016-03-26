package com.yihaodian.sby.console;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ConsoleAction extends HttpServlet  {
	private static final long serialVersionUID = 12346254546356L;
	public static String ipAuth ;
	public static String envAuth ;
	
	
	
    public void init(){
		ipAuth =getServletConfig().getInitParameter("ipAuth");
		ipAuth=ipAuth==null ?null :ipAuth.trim();
		envAuth =getServletConfig().getInitParameter("envAuth");
		envAuth=envAuth==null ?null :envAuth.trim();
        init();
    }


	public static GroovyShell shell=null;


	private void init(){
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sContext);
		String[] beans = applicationContext.getBeanDefinitionNames();
		Binding binding = new Binding();
		for (int i = 0; i < beans.length; i++) {
			try {
				Object abean = applicationContext.getBean(beans[i]);
				binding.setVariable(beans[i], abean);
			} catch (BeansException e) {
			}
		}
		GroovyClassLoader gcloader = new GroovyClassLoader(GroovyInit.class.getClassLoader());
		CompilerConfiguration gcon = new CompilerConfiguration();
		gcon.setScriptBaseClass("com.yihaodian.sby.console.ScriptTool");
		GroovyInit.shell = new GroovyShell(gcloader, binding, gcon);
	}
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		this.securityChech(request, response);
		if(request.getParameter("file")!=null){
			InputStream is = ConsoleAction.class.getClassLoader().getResourceAsStream(request.getParameter("file"));
			ServletOutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
		}else if(request.getParameter("code")!=null){
			String code = request.getParameter("code");
			Object re = null;
			try {
				if (code != null && code.length() > 0)
					re = shell.evaluate(code);
			} catch (Exception ex) {
				re = ex;
			}

			try {
				response.setHeader("content-type", "text/html; charset=UTF-8");
				String restring = re==null?"":re.toString();
				response.getOutputStream().write(restring.toString().getBytes());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			InputStream is = ConsoleAction.class.getClassLoader().getResourceAsStream("groovy.htm");
			ServletOutputStream os = response.getOutputStream();
			IOUtils.copy(is, os);
			is.close();
			os.close();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		doGet(request,response);
	}
	
	
	private void securityChech(HttpServletRequest request, HttpServletResponse response){
		String sysValue="";
		if(ipAuth==null && envAuth==null){
			try {
				response.getWriter().write("没有设置安全访问变量，禁止访问。请设置安全访问策略变量ipAuth  或者envAuth");
				response.getWriter().flush();
				response.getWriter().close();
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		
		
		boolean ipok=false;
		boolean envok=false;
		
		if(ipAuth==null)ipok=true;
		else{
			String ip= request.getRemoteAddr();
			if(ip.matches(ipAuth))ipok=true;
			else ipok=false;
		}
		if(envAuth==null)envok=true;
		else{
			String envAuthKey = envAuth.substring(0, envAuth.indexOf('='));
			String envAuthValue = envAuth.substring(envAuth.indexOf('=')+1);
			sysValue = System.getProperty(envAuthKey);
			if(sysValue==null || !sysValue.matches(envAuthValue))envok=false;
			else envok=true;
		}
		
		if(ipok&&envok);
		//if(envok);
		else{
			try {
				String freason = "";
				if(!ipok) freason ="your ip is forbidden to access, your ip :"+request.getRemoteAddr()+"\n";
				if(!envok) freason =freason+"  current env is forbidden to access console, current env is :"+sysValue;
				response.getWriter().write(freason);
				response.getWriter().flush();
				response.getWriter().close();
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
