pipeline {
	agent any

    tools {
		jdk 'JDK17'
       maven 'MAVEN3.9'
    }

    environment {
		DOCKER_REGISTRY = "52.90.159.202:8082"
       REPO_NAME = "docker-img"              // Nom du repository Nexus
       IMAGE_NAME = "myapp"
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
					// Format: registry/repository/image:tag
                dockerImage = docker.build("${DOCKER_REGISTRY}/${REPO_NAME}/${IMAGE_NAME}:${env.BUILD_ID}")
             }
          }
       }

       stage('Push docker image to Nexus') {
			steps {
				script {
					docker.withRegistry("http://${DOCKER_REGISTRY}", "nexusLog") {
						dockerImage.push()
                   dockerImage.push('latest')
                }
             }
          }
       }
    }
}