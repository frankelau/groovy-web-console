package com.yihaodian.sby.console;


import javax.servlet.ServletContext;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GroovyInit {
    public static GroovyShell shell=null;
    public static void init(ServletContext sContext){
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sContext);
        String[] beans = applicationContext.getBeanDefinitionNames();
        Binding binding = new Binding();
        for (int i = 0; i < beans.length; i++) {
            try {
                Object abean = applicationContext.getBean(beans[i]);
                binding.setVariable(beans[i], abean);
            } catch (Exception e) {
            }
        }
        GroovyClassLoader gcloader = new GroovyClassLoader(GroovyInit.class.getClassLoader());
        CompilerConfiguration gcon = new CompilerConfiguration();
        gcon.setScriptBaseClass("com.yihaodian.sby.console.ScriptTool");
        GroovyInit.shell = new GroovyShell(gcloader, binding, gcon);
    }
}
