pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build and Run Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',
                    reportFiles: 'index.html',
                    reportName: 'Hybrid Automation Report'
                ])
        }
    }
}