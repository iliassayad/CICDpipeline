pipeline {
	agent any

    tools {
		jdk 'JDK17'
       maven 'MAVEN3.9'
    }

    environment {
		IMAGE_NAME = "myapp"
		REGISTRY_CRDS = "nexusLog"  	 // ID des credentials Jenkins pour Nexus
		DOCKER_REGISTRY = "172.31.35.115:8082"
       	dockerImage = ''
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

       stage('Build docker image') {
			steps {
				script {
					dockerImage = docker.build("${DOCKER_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}")
             }
          }
       }

       stage('Push docker image to Nexus') {
			steps {
				script {
					docker.withRegistry("http://${DOCKER_REGISTRY}", "${REGISTRY_CRDS}") {
						dockerImage.push()
                }
             }
          }
       }
       stage('Clean Up Local Docker Images') {
			steps {
				script {
					sh "docker rmi ${DOCKER_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER} || true"
			 }
		  }
	   }
	   stage('run docker container') {
			steps {
				script {
					sh "docker run -d -p 80:8080 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}"
			 	}
			}
	   }
    }
}