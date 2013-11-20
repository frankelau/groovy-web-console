package com.yihaodian.sby.console;


import javax.servlet.ServletContext;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * webconsole
 * 
 * @author 陈作平zuopingchen@gmail.com
 * @date 2013-7-12
 * @version 1.0
 * 
 */
public class GroovyInit {
    public static GroovyShell shell=null;
    public static void init(ServletContext sContext){
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sContext);
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
        GroovyClassLoader gcloader = new GroovyClassLoader(GroovyInit.class.getClassLoader());
        CompilerConfiguration gcon = new CompilerConfiguration();
        gcon.setScriptBaseClass("com.yihaodian.sby.console.ScriptTool");
        // binding together
        GroovyInit.shell = new GroovyShell(gcloader, binding, gcon);
    }
}
