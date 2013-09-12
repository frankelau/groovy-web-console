groovy-web-console
================


the background
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



