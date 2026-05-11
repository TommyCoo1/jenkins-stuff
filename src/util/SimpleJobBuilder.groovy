package util

import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.dsl.Job

class SimpleJobBuilder {
    String jobName
    String description = 'Automatischer Freestyle-Job'
    String shellCommand = 'echo "Hello from SimpleJobBuilder"'
    String cronTrigger = ''

    Job build(JobParent parent) {
        parent.job(this.jobName) {
            description(this.description)

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