pipeline {
    agent any
    tools { maven 'maven 3.9.6'; jdk 'Open jdk 17' }
    stages {
        stage('Test') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
    post {
        always {
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
