pipeline {
    agent any
    stages {
        stage('Checkout from GitHub.') {
            steps {
                git url: 'git@github.com:nakanoi/JenkinsJava', credentialsId: 'LocalJenkinsSSH'
            }
        }

        stage('Gradle build.') {
            steps {
                sh "./gradlew build"
            }
        }

        stage('Build other jobs.') {
            steps {
                build job: 'theSecondJob'
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