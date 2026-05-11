package util

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Builder-Class for Pipeline-Jobs.
 */
class JobBuilder {
    String jobName
    String repoUrl
    String branch = 'main'
    String jenkinsfilePath = 'Jenkinsfile'
    
    // DslFactory-Kontext ('factory'), 
    // um die Jenkins Job DSL Befehle (wie pipelineJob, cpsScm, etc.) nutzen zu können.
    Job build(DslFactory factory) {
        return factory.pipelineJob(this.jobName) {
            description("Dieser Job wurde automatisch durch die JobBuilder-Klasse generiert.")
            
            // Parameter hinzufügen
            parameters {
                stringParam('BRANCH', this.branch, 'Branch, der gebaut werden soll')
                booleanParam('SKIP_TESTS', false, 'Tests überspringen?')
            }

            // Log last 10 builds
            logRotator {
                numToKeep(10)
            }

            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url(this.repoUrl)
                            }
                            branch('${BRANCH}') // branch parameter
                            // maybe credentials?
                            // credentials('git-token-id')
                        }
                    }
                    scriptPath(this.jenkinsfilePath)
                }
            }
        }
    }
}
