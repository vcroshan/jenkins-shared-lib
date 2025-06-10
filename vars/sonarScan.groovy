/**
 * Executes a SonarQube scan for a Maven project.
 * Requires the Jenkins SonarQube Scanner plugin.
 *
 * @param config A map containing configuration parameters:
 * - `serverName` (String, mandatory): The ID of the SonarQube server configured in Jenkins.
 * - `projectKey` (String, mandatory): The SonarQube project key.
 * - `projectName` (String, optional): The SonarQube project name (default: projectKey).
 * - `projectVersion` (String, optional): The project version.
 * - `sourcePath` (String, optional): Path to the source code (default: '.').
 * - `additionalProperties` (String, optional): Additional SonarQube properties.
 */
def call(Map config) {
    // Validate mandatory parameters
    if (!config.serverName || !config.projectKey) {
        error "sonarScan: Missing mandatory parameters (serverName, projectKey)"
    }

    def projectName = config.projectName ?: config.projectKey
    def projectVersion = config.projectVersion ?: '1.0'
    def sourcePath = config.sourcePath ?: '.'
    def additionalProperties = config.additionalProperties ?: ''

    // Configure SonarQube scanner
    withSonarQubeEnv(config.serverName) {
        // Execute Maven with SonarQube goal
        sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar " +
           "-Dsonar.projectKey=${config.projectKey} " +
           "-Dsonar.projectName='${projectName}' " +
           "-Dsonar.projectVersion='${projectVersion}' " +
           "-Dsonar.sources=${sourcePath} " +
           "${additionalProperties}"
    }

    // Optional: Wait for quality gate status (requires SonarQube plugin configured for webhook/polling)
    // def qg = waitForQualityGate()
    // if (qg.status != 'OK') {
    //    error "SonarQube Quality Gate failed with status: ${qg.status}"
    // }
    echo "SonarQube scan completed for project ${config.projectKey}."
}
