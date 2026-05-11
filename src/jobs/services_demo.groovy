def REPO = 'https://github.com/TommyCoo1/jenkins-stuff.git'

def services = [
    [name: 'auth-service',    tasks: ':auth:build',    hasTests: true ],
    [name: 'user-service',    tasks: ':user:build',    hasTests: true ],
    [name: 'gateway',         tasks: ':gateway:build', hasTests: false],
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
            
            // Nutze den 'gradle' Step statt 'shell' für bessere Jenkins-Integration
            gradle {
                tasks(svc.tasks)
                useWrapper(false) // Falls kein Wrapper im Repo ist
            }

            // Wenn Tests existieren, führe sie auch wirklich aus
            if (svc.hasTests) {
                shell("echo \"Running tests for ${svc.name}...\"")
                gradle {
                    tasks(svc.tasks.replace('build', 'test'))
                    useWrapper(false)
                }
            }
        }
    }
}
