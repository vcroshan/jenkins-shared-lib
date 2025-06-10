def call() {
    stage('Checkout') {
        checkout scm
    }

    stage('Build') {
        MavenUtils.build()
    }

    stage('SonarQube Analysis') {
        MavenUtils.sonarScan()
    }

    stage('Publish to Artifactory') {
        MavenUtils.publishToArtifactory()
    }
}
