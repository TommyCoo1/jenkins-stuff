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

