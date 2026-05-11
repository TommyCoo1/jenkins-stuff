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
        jobDsl {
            targets('jobs/**/*.groovy')
            
            // classpath
            additionalClasspath('src/main/groovy')
            
            // 
            sandbox(false)
            
            // delete in repo -> delete job
            removedJobAction('DELETE')
            removedViewAction('DELETE')
        }
    }
}
