package util

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Hilfsklasse für einfache Freestyle-Jobs (nicht Pipeline).
 */
class SimpleJobBuilder {
    String jobName
    String description = 'Automatischer Freestyle-Job'
    String shellCommand = 'echo "Hello from SimpleJobBuilder"'
    String cronTrigger = ''

    /**
     * Erstellt den Freestyle-Job im angegebenen DSL-Kontext.
     */
    Job build(DslFactory factory) {
        return factory.job(this.jobName) {
            delegate.description(this.description)
            
            if (this.cronTrigger) {
                triggers {
                    scm(this.cronTrigger)
                }
            }

            steps {
                shell(this.shellCommand)
            }

            logRotator {
                numToKeep(5)
            }
        }
    }
}
