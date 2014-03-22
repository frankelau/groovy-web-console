package com.yihaodian.sby.console;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author 陈作平(zuopingchen@163.com)
 * @date 2013年9月7日
 * 
 */
public class ScriptHandler{
    
	public void Handle(String code, HttpServletResponse response) {
		Object re = null;
		try {
			if (code != null && code.length() > 0)
				re = GroovyInit.shell.evaluate(code);
		} catch (Exception ex) {
			re = ex;
		}

		try {
			response.setHeader("content-type", "text/html; charset=UTF-8");
			String restring = re.toString();
			response.getWriter().write(restring);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
