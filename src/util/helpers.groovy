def createSimpleJob(parent, Map cfg) {
    parent.job(cfg.jobName) {
        description(cfg.description ?: 'Automatischer Freestyle-Job')

        if (cfg.cronTrigger) {
            triggers {
                scm(cfg.cronTrigger)
            }
        }

        steps {
            shell(cfg.shellCommand ?: 'echo "Hello from helper"')
        }

        logRotator {
            numToKeep(5)
        }
    }
}

return this