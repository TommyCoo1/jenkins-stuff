job('seed-job') {
    description('Der Meta-Seed-Job: Generiert alle anderen Jobs aus dem Git-Repo und aktualisiert sich selbst.')

    triggers {
        scm('H/5 * * * *') // all 5 min check repo for updates
    }

    scm {
        git {
            remote {
                url('https://github.com/TommyCoo1/jenkins-stuff.git')
            }
            branch('main')
        }
    }

    steps {
        dsl {
            external('jobs/**/*.groovy')
            // classpath
            additionalClasspath('src/main/groovy')
            removeAction('DELETE')
            removeViewAction('DELETE')
        }
    }
}