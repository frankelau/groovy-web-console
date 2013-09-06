call mvn clean
call mvn package
call mvn install:install-file  -DgroupId=com.yihaodian.sby.console  -DartifactId=groovy-web-console -Dversion=1.0 -Dfile=./target/groovy-console-1.0.jar  -Dpackaging=jar  -DgeneratePom=true
pause 	


