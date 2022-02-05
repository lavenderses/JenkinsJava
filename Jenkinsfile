pipeline {
    agent any
    stages {

        stage('Echooo.') {
            steps {
                sh "echo \"\nHello.\n\""
                sh "echo \"\nBranch Name: $BRANCH_NAME\n\""
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
                spotBugs pattern: 'build/reports/spotbugs/*.xml'
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