/**
 * Builds a Maven project.
 *
 * @param config A map containing configuration parameters:
 * - `goal` (String, optional): Maven goal to run (default: 'clean install').
 * - `options` (String, optional): Additional Maven options (e.g., '-DskipTests').
 * - `pomFile` (String, optional): Path to the pom.xml file (default: 'pom.xml').
 */
def call(Map config = [:]) {
    def goal = config.goal ?: 'clean install'
    def options = config.options ?: ''
    def pomFile = config.pomFile ?: 'pom.xml'

    // Ensure Maven is available in the Jenkins environment
    // Assumes you have a Maven tool configured in Jenkins under "Manage Jenkins -> Tools"
    // named "M3" or similar. Adjust as per your Jenkins configuration.
    // tool 'M3' // Uncomment and configure if Maven is not globally available on agent path

    // Execute the Maven command
    sh "mvn ${goal} ${options} -f ${pomFile}"
}