pipeline {
    agent any
    tools { maven 'maven 3.9.16' }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
    }
    post {
        always {
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
