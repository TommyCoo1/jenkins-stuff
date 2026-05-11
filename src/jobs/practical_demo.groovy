import util.JobBuilder

// Dein einziges Repo
def REPO = 'https://github.com/TommyCoo1/jenkins-stuff.git'


// ============================================================
// BEISPIEL 1: Einfache Loops - Mehrere identische Jobs erzeugen
// ============================================================

def environments = ['dev', 'staging', 'prod']

environments.each { env ->
    job("deploy-to-${env}") {
        description("Deployment-Job für ${env.toUpperCase()} Umgebung")

        parameters {
            stringParam('VERSION', 'latest', 'Version die deployed werden soll')
            choiceParam('ACTION', ['deploy', 'rollback'], 'Was soll gemacht werden?')
        }

        // Nur prod braucht manuelle Bestätigung
        if (env == 'prod') {
            concurrentBuild(false)
        }

        steps {
            shell("echo \"Deploying \${VERSION} to ${env}\"")
            shell("echo \"Action: \${ACTION}\"")
        }
    }
}


// ============================================================
// BEISPIEL 2: Map/Objekte - Jobs aus strukturierten Daten
// ============================================================

def services = [
    [name: 'auth-service',    buildCmd: './gradlew :auth:build',    hasTests: true ],
    [name: 'user-service',    buildCmd: './gradlew :user:build',    hasTests: true ],
    [name: 'gateway',         buildCmd: './gradlew :gateway:build', hasTests: false],
]

// Folder anlegen
folder('services') {
    description('Alle Microservice-Jobs')
}

services.each { svc ->
    job("services/build-${svc.name}") {
        description("Build-Job für ${svc.name}")

        scm {
            git {
                remote {
                    url(REPO)
                }
                branch('main')
            }
        }

        triggers {
            scm('H/10 * * * *')
        }

        steps {
            shell("echo \"Building ${svc.name}...\"")
            shell(svc.buildCmd)

            // Nur wenn Tests existieren
            if (svc.hasTests) {
                shell("echo \"Running tests for ${svc.name}...\"")
            }
        }
    }
}


// ============================================================
// BEISPIEL 3: Verschachtelte Loops - Matrix-Build
// ============================================================

def branches = ['main', 'develop']
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


// ============================================================
// BEISPIEL 4: JobBuilder-Klasse mit Loop (Pipeline-Jobs)
//             → Braucht Jenkinsfile in DIESEM Repo!
// ============================================================

// folder('pipelines') {
//     description('Pipeline-Jobs erzeugt via JobBuilder-Klasse')
// }

// Verschiedene Jenkinsfile-Pfade im selben Repo
// def pipelineConfigs = [
//     [name: 'pipelines/build-main',    branch: 'main',    jenkinsfile: 'Jenkinsfile'],
//     [name: 'pipelines/build-develop', branch: 'develop', jenkinsfile: 'Jenkinsfile'],
// ]

// pipelineConfigs.each { cfg ->
//     new JobBuilder(
//         jobName: cfg.name,
//         repoUrl: REPO,
//         branch: cfg.branch,
//         jenkinsfilePath: cfg.jenkinsfile
//     ).build(this)
// }


// ============================================================
// BEISPIEL 5: Views erzeugen - Jobs gruppiert anzeigen
// ============================================================

listView('Alle Deployments') {
    description('Zeigt alle Deployment-Jobs')
    jobs {
        regex('deploy-to-.*')
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
