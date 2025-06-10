/**
 * Publishes Maven artifacts to JFrog Artifactory.
 * Requires the Jenkins JFrog Artifactory plugin.
 *
 * @param config A map containing configuration parameters:
 * - `serverName` (String, mandatory): The ID of the Artifactory server configured in Jenkins.
 * - `repoKey` (String, mandatory): The target Artifactory repository key (e.g., 'libs-release-local').
 * - `buildInfoName` (String, mandatory): The name of the build in Artifactory.
 * - `buildNumber` (String, mandatory): The build number.
 * - `pomFile` (String, optional): Path to the pom.xml file (default: 'pom.xml').
 */
def call(Map config) {
    // Validate mandatory parameters
    if (!config.serverName || !config.repoKey || !config.buildInfoName || !config.buildNumber) {
        error "jfrogPublish: Missing mandatory parameters (serverName, repoKey, buildInfoName, buildNumber)"
    }

    def pomFile = config.pomFile ?: 'pom.xml'

    // Use the rtMaven pipeline step provided by the JFrog Artifactory plugin
    // This step automatically handles build info collection and publishing.
    rtMavenRun (
        tool: 'M3', // Use the Maven tool name configured in Jenkins
        pom: "${pomFile}",
        goals: "clean install", // This will build and then deploy
        resolverId: "${config.serverName}",
        deployerId: "${config.serverName}"
    )

    // Publish build info to Artifactory
    rtPublishBuildInfo (
        serverID: "${config.serverName}",
        buildName: "${config.buildInfoName}",
        buildNumber: "${config.buildNumber}"
    )
    echo "Published build info for ${config.buildInfoName} #${config.buildNumber} to Artifactory ${config.serverName}"
}
