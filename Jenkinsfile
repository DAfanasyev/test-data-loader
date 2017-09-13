#!groovy
@Library('github.com/cloudogu/ces-build-lib@cd5a253')
import com.cloudogu.ces.cesbuildlib.*

node {

    properties([
            // Keep only the most recent builds in order to preserve space
            buildDiscarder(logRotator(numToKeepStr: '20')),
            // Don't run concurrent builds for a branch, because they use the same workspace directory
            disableConcurrentBuilds()
    ])

    catchError {

        def mvnHome = tool 'M3'
        def javaHome = tool 'JDK8'

        Maven mvn = new MavenLocal(this, mvnHome, javaHome)
        Git git = new Git(this)

        stage('Checkout') {
            checkout scm
            /* Don't remove folders starting in "." like
             * .m2 (maven), .npm, .cache, .local (bower)
             */
            git.clean('".*/"')
        }

        stage('Build') {
            mvn 'clean install -DskipTests'
            archive '**/target/*.jar'
        }

        stage('Unit Test') {
            mvn "test"
        }
    }

    // Archive Unit and integration test results, if any
    junit allowEmptyResults: true,
            testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/*.xml'

    // Find maven warnings and visualize in job
    warnings consoleParsers: [[parserName: 'Maven']]

    mailIfStatusChanged(emailRecipients)
}

String getEmailRecipients() {
    def isStableBranch = env.EMAIL_RECIPIENTS_ACCOUNT in ['master', 'develop']
    if (isStableBranch) {
        return env.EMAIL_RECIPIENTS_TDL
    } else {
        return git.commitAuthorEmail
    }
}