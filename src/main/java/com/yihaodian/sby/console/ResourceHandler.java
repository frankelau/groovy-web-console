package com.yihaodian.sby.console;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceHandler {

	public void Handle(String file, HttpServletResponse response) {
		try {
			response.getWriter().write(ResourceUtil.getFileAsString(file));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
