package com.yihaodian.sby.console;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

public class ResourceUtil{
	public static String getFileAsString(String name){
		Reader ts = new InputStreamReader(ScriptHandler.class.getClassLoader().getResourceAsStream("src"+File.separatorChar+name));
		String re ="";
		try {
		    re =IOUtils.toString(ts);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return re;
	}
}
