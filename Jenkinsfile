pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Pulls the latest code from GitHub
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                // Runs Maven build and executes TestNG tests
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            // Archives the Extent Report so it's viewable in Jenkins
            archiveArtifacts artifacts: 'target/ExtentReport.html', fingerprint: true
        }
        success {
            echo 'Pipeline completed successfully! Test report archived.'
        }
        failure {
            echo 'Pipeline failed! Check the console output or surefire reports.'
        }
    }
}