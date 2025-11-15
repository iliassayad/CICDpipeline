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
			steps {
				withSonarQubeEnv('sonarserver') {
					sh '''
						mvn sonar:sonar \
						-Dsonar.projectKey=unitTesting \
						-Dsonar.projectName=unitTesting
					'''
				}
			}
		}
	}
}