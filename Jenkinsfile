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
					archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
			}
		}
	}
}