pipeline {
    agent any
    tools {
        maven "M3"
        //jdk "OracleJDK17"
    }

    environment {
        registryCredential = 'ecr:ap-southeast-1:geethdula1aws'
        appRegistry = "608900566080.dkr.ecr.ap-southeast-1.amazonaws.com/springboot-test"
        myprojectRegistry = "https://608900566080.dkr.ecr.ap-southeast-1.amazonaws.com/"
        cluster = "dev-app"
        service = "dev-app-svc"
    }
    stages {
    stage('Fetch code'){
      steps {
        git credentialsId: 'geethdula1git', url: 'https://github.com/geethdula/sample-springboot.git'
      }
    }


    stage('Test'){
      steps {
        sh 'mvn test'
      }
    }

    stage ('CODE ANALYSIS WITH CHECKSTYLE'){
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
            post {
                success {
                    echo 'Generated Analysis Result'
                }
            }
        }
/*
        stage('build && SonarQube analysis') {
            environment {
             scannerHome = tool 'sonar4.8'
          }
            steps {
                withSonarQubeEnv('mysonar') {
                 sh '''${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=myproject \
                   -Dsonar.projectName=myproject-repo \
                   -Dsonar.projectVersion=1.0 \
                   -Dsonar.sources=src/ \
                   -Dsonar.java.binaries=target/test-classes/com/visualpathit/account/controllerTest/ \
                   -Dsonar.junit.reportsPath=target/surefire-reports/ \
                   -Dsonar.jacoco.reportsPath=target/jacoco.exec \
                   -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml'''
                }
            }
        }
/*
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
*/
     stage('Build') {
            steps {

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
    }
    stage('Build App Image') {
       steps {
       
         script {
                dockerImage = docker.build( appRegistry + ":$BUILD_NUMBER", "./")
             }

        }
    
    }

    stage('Upload App Image') {
          steps{
            script {
              docker.withRegistry( myprojectRegistry, registryCredential ) {
                dockerImage.push("$BUILD_NUMBER")
                dockerImage.push('latest')
              }
            }
        }
     }

     stage('Deploy to ECS') {
          steps {
            withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: 'geethdula1aws',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
            sh 'aws ecs update-service --cluster ${cluster} --service ${service} --force-new-deployment'
        }
      }
     }
  }
}