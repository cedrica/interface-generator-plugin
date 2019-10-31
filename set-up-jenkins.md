#How to set up Jenkins for a project
This documentation is not complet and also not 100% correct. i am also still learniing how to do that properly
check link bellow they could be helpfull
https://www.jfrog.com/confluence/display/RTF/Jenkins+Artifactory+Plug-in
https://www.youtube.com/watch?v=wHX4j0z-sUU

###1.  Create a mvn project
###2.  Add a jenkins file to the project with the following content

``` groovy
import groovy.json.JsonSlurper
pipeline {
    agent any
    tools {    
        maven "(Default)"
        jdk '1.8.0_144_x64' 
    }

    stages {
        stage('Build from branch') {
            when {
                environment name: 'CHANGE_ID', value: null
            }

            steps {
                timestamps {
                    withSonarQubeEnv('cube') {
                        withMaven(maven: '(Default)', mavenLocalRepo: '.repository', mavenOpts: '-Xmx6g -Xms2g -XX:-UseGCOverheadLimit') {
                            bat "mvn -Dsonar.branch.name=$BRANCH_NAME clean verify sonar:sonar"
                        }
                    }
                }
            }
        }

        stage('Build from pull-request') {
            when {
                not { environment name: 'CHANGE_ID', value: null }
            }

            steps {
                timestamps {
                    withSonarQubeEnv('cube') {
                        withMaven(maven: '(Default)', mavenLocalRepo: '.repository', mavenOpts: '-Xmx6g -Xms2g -XX:-UseGCOverheadLimit') {
                            bat "mvn -Dsonar.branch.name=$CHANGE_BRANCH -Dsonar.branch.target=$CHANGE_TARGET clean verify sonar:sonar"
                        }
                    }
                }
            }
        }

        stage('Test') {
            steps {
                junit '**/target/surefire-reports/*.xml' 
            }
        }

        stage('Deploy') {
            steps {
                timestamps {
                    withMaven(maven: '(Default)', mavenLocalRepo: '.repository', mavenOpts: '-Xmx6g -Xms2g -XX:-UseGCOverheadLimit') {
                        bat "mvn deploy -DskipTests"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                currentBuild.result = currentBuild.result ?: 'SUCCESS'
                notifyBitbucket()
            }
        }
    }
}
```
###3.  Configure your artifact repository (in Nexus or in JForg)
###4.  Download and install Jenkins CI 
``` groovy
    https://jenkins.io/download/
``` 
   *   Install some plugins(Sonar, Bitbucket/redmine/Github)
   
    
###5.  setup and configure the mvn project in jenkins as follow
   
   *   go to create Element > "build maven project"
   *   Enter project name and then configure all required things there
       *   Bitbucket / Github 
            >   Owner              
              ...usw
        
###6.  configure your pom.xml using the created artifactory repo
``` xml
    <distributionManagement>
        <repository>
            <id>central</id>
            <url>${artifactory.url}/libs-release-local</url>
        </repository>
        <snapshotRepository>
            <id>snapshots</id>
            <url>${artifactory.url}/libs-snapshot-local</url>
        </snapshotRepository>
    </distributionManagement>

    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>central</id>
            <name>libs-release</name>
            <url>${artifactory.url}/libs-release</url>
        </repository>
        <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>snapshots</id>
            <name>libs-snapshot</name>
            <url>${artifactory.url}/libs-snapshot</url>
        </repository>
    </repositories>
```            

###7.  Create project in Sonar using groupId and artifactId
###8.  Configure Sonar in Bitbucket

   *   Click on Repository configuration icon 
   *   Click Sonar
   *   Enable Sonar
   *    In last Tabpane
        *   Advanced Tab
           **use special = PR-%d** ==>  to show pull request state comming from sonar analyse
   
   
    

 
