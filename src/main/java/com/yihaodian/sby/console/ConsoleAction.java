package com.yihaodian.sby.console;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * webconsole
 * 
 * @author 陈作平zuopingchen@gmail.com
 * @date 2013-7-12
 * @version 1.0
 * 
 */
public class ConsoleAction extends HttpServlet  {
	private static final long serialVersionUID = 12346254546356L;
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		this.securityChech(request, response);
		if(request.getParameter("file")!=null){
			new ResourceHandler().Handle(request, response);
		}else{
			new ScriptHandler().Handle(request, response);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		doGet(request,response);
	}
	
	
	private void securityChech(HttpServletRequest request, HttpServletResponse response){
		String ipAuth =getServletConfig().getInitParameter("ipAuth");
		ipAuth=ipAuth==null ?null :ipAuth.trim();
		String envAuth =getServletConfig().getInitParameter("envAuth");
		envAuth=envAuth==null ?null :envAuth.trim();
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
			String sysValue = System.getenv(envAuthKey);
			if(sysValue.matches(envAuthValue))envok=false;
			else envok=true;
		}
		
		if(ipok&&envok);
		else{
			try {
				response.getWriter().write("权限不够");
				response.getWriter().flush();
				response.getWriter().close();
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
