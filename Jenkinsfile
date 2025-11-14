pipeline {
	agent any

    tools {
		jdk 'JAVA21'
       maven 'MAVEN3.9'
    }

   stages {
		stage('Checkout') {
			steps {
				checkout scm
			}
		}
		stage('Build') {
			steps {
				sh 'mvn clean install -DskipTests'
			}
			post {
				success {
					echo 'Build completed successfully.'
					 archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
				}
				failure {
					echo 'Build failed. Please check the logs.'
				}
			}
		}
		stage('Test') {
			steps {
				sh 'mvn test'
			}
		}
		stage('sonarQube Analysis') {
			environment {
				scannerHome = tool 'sonar6.2'
			}

			steps {
				withSonarQubeEnv('sonarserver') {
					sh '''
					${scannerHome}/bin/sonar-scanner \
					-Dsonar.projectKey=unitTesting \
					-Dsonar.projectName=unitTesting \
					-Dsonar.projectVersion=1.0 \
					-Dsonar.java.binaries=target/classes \
					-Dsonar.junit.reportPaths=target/surefire-reports \
					-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
					-Dsonar.sources=src \
					'''
				}
			}
		}
	}
}