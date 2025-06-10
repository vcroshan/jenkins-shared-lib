/**
 * Gets the current Git commit SHA.
 * @return String The short Git commit SHA.
 */
def getGitCommitShortSha() {
    return sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
}

/**
 * Gets the current Git branch name.
 * @return String The Git branch name.
 */
def getGitBranchName() {
    return sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
}

/**
 * Generates a consistent build number.
 * You might want to use Jenkins' built-in BUILD_NUMBER or customize this.
 * @return String The generated build number.
 */
def generateBuildNumber() {
    return env.BUILD_NUMBER
}
