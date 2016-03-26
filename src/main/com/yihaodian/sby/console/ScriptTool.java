package com.yihaodian.sby.console;

import groovy.lang.Script;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * @author 陈作平(zuopingchen@163.com)
 * @date 2013年9月8日
 *
 */
public class ScriptTool  extends Script {

	@Override
	public Object run() {
		return null;
	}

	public Object  ls(){
		return  this.getBinding().getVariables().keySet();
	}
	
	public String toString(Object o){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(o);
		return json;
	}
}
