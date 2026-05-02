pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compilar') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Pruebas') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Empaquetar WAR') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archivar WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline completado exitosamente. WAR generado.'
        }
        failure {
            echo 'El pipeline falló. Revisa los logs.'
        }
    }
}