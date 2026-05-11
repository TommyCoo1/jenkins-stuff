listView('Alle Deployments') {
    description('Zeigt alle Deployment-Jobs')
    jobs {
        regex('deploy-to-.*')
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
