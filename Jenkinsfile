pipeline {
    agent any
    tools { maven 'maven 3.9.6' }
    parameters {
     choice(choices: ['chrome', 'firefox', 'edge'], name: 'BROWSER')
        string(name: 'BASE_URL', defaultValue: 'https://app.qase.io', description: 'Qase base URL')
    }
    environment {
        BROWSER    = "${params.BROWSER}"
        BASE_URL   = "${params.BASE_URL}"
        QASE_CREDS = credentials('qase-user-password')
        QASE_TOKEN = credentials('qase-token')
    }
    stages {
        stage('Test') {
            steps {
                sh '''
                    mvn clean test \
                        -Dbrowser=$BROWSER \
                        -DbaseUrl=$BASE_URL \
                        -Duser=$QASE_CREDS_USR \
                        -Dpassword=$QASE_CREDS_PSW \
                        -Dtoken=$QASE_TOKEN
                '''
            }
        }
    }
    post {
        always {
            allure includeProperties: false, results: [[path: 'target/allure-results']]
        }
    }
}
