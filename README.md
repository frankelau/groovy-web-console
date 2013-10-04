groovy-web-console
================


background
--------------

it is a tool to  access java class at runtime.
it can invoke spring bean, ibatis dao ,hibernate dao dynamically

this characteristic make it very useful to debug online.
when a java project deploy online , it is very difficult to access its inner component.
so when a bug occur ,it is difficult to find excat failure point.
in my company, to solve this problem ,people always add log code and redeploy the project and watch the log.
this way is so time-consuming and ineffective.

so i create this java web console to solve the problem.
when a bug occur , i invoke all the required beans and check the result one by one 
then the exact failure point found.


the main tech behind this tool is groovy , it can compile and  run java code or groovy script at runtime.

install
------

1.download the jar file and put it in right place
if you use maven ,then intall this jar file to you local repository by this commond , and add the following  dependency section to you project pom file 

if you donn't use maven then just put the jar file in "WEB-INF/lib" directory

2.put the following  servlet declare section to your web.xml file

	<!--  webconsole -->
	<servlet>
		<servlet-name>groovyWebConsole</servlet-name>
		<servlet-class>com.yihaodian.sby.console.ConsoleAction</servlet-class>
		<init-param>
		        <param-name>ipAuth</param-name>
		        <param-value>127.0.0.1</param-value><!-- regex  -->
         	</init-param>
 
    	        <init-param>
		        <param-name>envAuth</param-name>
		        <param-value>xxx=yyy</param-value>
                        <!--  xxx is the java envi key , and is a string 
                              yyy is xxx's java envi value , yyy is a regex can match multiple evn --->
         	</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>groovyWebConsole</servlet-name>
		<url-pattern>/groovyWebConsole</url-pattern>
	</servlet-mapping>
3.that is all and enjoy the groovy web console


usage
-----

the groovy web console is just a embeded groovy script interpreter. so it can do all the thing that a groovy interpreter commond line do . the main difference are tow : groovy web console has a web interface and it can access spring or ibatis bean , so you can dynamically chech whether a  bean  behave the right way .
<ul>
<li>
open you browser and inpuy the url http://127.0.0.1/groovyWebConsole      your  url can be a little  different from this one,if your project is not the root project .
</li>
<li>
input some groovy script and hit submit and you will get the script result
</li>
</ul>
