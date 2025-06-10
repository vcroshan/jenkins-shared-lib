package org.example.utils

class MavenUtils implements Serializable {

    static void build() {
        sh 'mvn clean install'
    }

    static void sonarScan() {
        withSonarQubeEnv('SonarQubeServer') {
            sh 'mvn sonar:sonar'
        }
    }

    static void publishToArtifactory() {
        sh 'mvn deploy -DaltDeploymentRepository=artifactory::default::http://your-artifactory-url/artifactory/libs-release-local'
    }
}
