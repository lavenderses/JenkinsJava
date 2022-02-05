pipeline {
    agent any
    stages {
        stage('Checkout from GitHub.') {
            steps {
                git url: 'git@github.com:nakanoi/JenkinsJava', credentialsId: 'LocalJenkinsSSH'
            }
        }

        stage('Echo project name.') {
            steps {
                sh "echo \"\nEcho prject name: $PROJECT_NAME\n\""
            }
        }

        stage('Gradle build.') {
            steps {
                sh "./gradlew build"
            }
        }

        stage('Build other jobs.') {
            steps {
                build job: 'theSecondJob', parameters: [text(name: 'FROM_WHERE', value: params.PROJECT_NAME)]
            }
        }

        stage('Test report.') {
            steps {
                junit 'build/test-results/**/*.xml'
                jacoco execPattern: '**/**.exec'
            }
        }
        
        stage('Static code analysis.') {
            steps {
                checkStyle pattern: 'build/reports/checkstyle/*.xml'
                spotBugs pattern: 'build/reports/checkstyle/*.xml'
            }
        }
        
        stage('Send email.') {
            steps {
                step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'behappyday113@gmail.com', sendToIndividuals: false])
            }
        }
        
        stage('Save archive files.') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar', followSymlinks: false
            }
        }
    }
}