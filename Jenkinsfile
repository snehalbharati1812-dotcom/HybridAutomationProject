pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Pulls the latest code from your GitHub repository
                checkout scm
            }
        }

        stage('Build and Run Tests') {
            steps {
                // Replace 'mvn clean test' with whatever command you use to run your automation project
                // (e.g., gradle test, pytest, npm test, etc.)
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Publishes your HTML report to the Jenkins dashboard
            publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',         // UPDATE THIS: Folder where your report is saved (e.g., 'target', 'reports', 'test-output')
                    reportFiles: 'index.html',   // UPDATE THIS: Your exact HTML report file name
                    reportName: 'Hybrid Automation Report'
                ])
        }
    }
}