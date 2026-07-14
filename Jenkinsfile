pipeline {
    agent any
    tools { maven 'maven 3.9.6' }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test "-Dbrowser=${params.browser}"'
            }
        }
    }
    post {
        always {
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
