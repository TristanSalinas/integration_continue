pipeline {
    agent { label 'agent-windows' }

    stages {
        stage('Check MySQL') {
            steps {
                bat 'mysql --version'
            }
        }
    }
}
