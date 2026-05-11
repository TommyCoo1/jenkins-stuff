def REPO = 'https://github.com/TommyCoo1/jenkins-stuff.git'

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
