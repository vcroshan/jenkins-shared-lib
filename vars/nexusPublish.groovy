/**
 * Publishes Maven artifacts to Nexus Repository Manager.
 * This function assumes your project's pom.xml has the <distributionManagement>
 * section configured to point to your Nexus release and snapshot repositories.
 * It also assumes that the Maven settings.xml (either global or per-user)
 * on the Jenkins agent contains the server credentials matching the IDs
 * defined in your pom.xml's distributionManagement.
 *
 * @param config A map containing configuration parameters:
 * - `repositoryId` (String, optional): The ID of the repository defined in your pom.xml's distributionManagement
 * and corresponding in your Maven settings.xml. Default: 'snapshots' or 'releases'.
 * This parameter is primarily for documentation; 'mvn deploy' handles this based on POM.
 * - `pomFile` (String, optional): Path to the pom.xml file (default: 'pom.xml').
 * - `mavenToolName` (String, optional): Name of the Maven tool configured in Jenkins (default: 'M3').
 */
def call(Map config = [:]) {
    def pomFile = config.pomFile ?: 'pom.xml'
    def mavenToolName = config.mavenToolName ?: 'M3'
    def settingsfile = config.settingsfile ?: 'settings.xml'

    // Optional: Use the Maven tool configured in Jenkins if not globally available
    // tool "${mavenToolName}"

    echo "Attempting to deploy artifacts to Nexus using 'mvn deploy'..."
    // The 'mvn deploy' goal reads the <distributionManagement> section from your pom.xml
    // and uses the server credentials configured in Maven's settings.xml (via Jenkins credentials)
    // to authenticate with Nexus.
    sh "mvn deploy -f ${pomFile} -s ${settingsfile}"

    echo "Maven artifacts deployed to Nexus."
}