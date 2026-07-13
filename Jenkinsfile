pipeline {
    agent any
    tools { maven 'Maven3'; jdk 'JDK17' }
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
