pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *') // all 5 min check repo for updates
    }

    stages {
        stage('Generate Jobs') {
            steps {
                jobDsl(
                    targets: 'src/jobs/*.groovy',
                    additionalClasspath: 'src',
                    removedJobAction: 'DELETE',
                    removedViewAction: 'DELETE'
                )
            }
        }
    }
}
