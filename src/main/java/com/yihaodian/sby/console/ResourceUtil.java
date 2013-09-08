package com.yihaodian.sby.console;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

public class ResourceUtil{
	public static String getFileAsString(String name){
		StringWriter writer = new StringWriter();
		Reader ts = new InputStreamReader(ScriptHandler.class.getClassLoader().getResourceAsStream("src"+File.separatorChar+name));
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
