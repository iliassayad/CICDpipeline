pipeline {
	agent any

	tools {
		jdk 'JDK17'
		maven 'MAVEN3.9'
	}

	stages {

		stage('Build') {
			steps {
				echo 'Building...'
				sh 'mvn clean install'
			}
			post {
				success {
					sh 'echo Build Successful'
					archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
			}
		}
		stage ('Test Docker'){
			steps {
				echo 'Testing Docker....'
				sh 'docker --version'
			}
		}
	}
}