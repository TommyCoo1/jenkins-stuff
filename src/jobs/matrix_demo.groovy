package jobs

def REPO = 'https://github.com/TommyCoo1/jenkins-stuff.git'

def branches = ['main']
def jdkVersions = ['jdk17', 'jdk21']

folder('matrix-builds') {
    description('Matrix-Builds: Branch x JDK Kombination')
}

branches.each { targetBranch ->
    jdkVersions.each { targetJdk ->
        job("matrix-builds/${targetBranch}-${targetJdk}") {
            description("Build von Branch '${targetBranch}' mit ${targetJdk}")

            // jdk(targetJdk)  // nicht unterstützt in MemoryJobManagement-Tests

            scm {
                git {
                    remote {
                        url(REPO)
                    }
                    branch(targetBranch)
                }
            }

            steps {
                shell("echo \"Branch: ${targetBranch}, JDK: ${targetJdk}\"")
                shell('java -version')
            }
        }
    }
}
