to test plugin 
1. create a test pom.xml in a folder as i did and placed it into use-example
2. confiure the pom as when you are calling a plugin inside the pom of a project. 
3. run mvn clean install and then mvn compile -f usage-example/pom.xml
REMEMBER THAT FOR THIS CASE ${project.basedir} will be C:\workspace\interface-generator-maven-plugin\usage-example  and not C:\workspace\interface-generator-maven-plugin because 
the pom you are running on is located not in the root folder but in use-example. ===> don´t be afraid it is just in test case but in production case pom.xml will be located in the root so that
${project.basedir} will be the root and you will be able to call access the target and put the generated file insides