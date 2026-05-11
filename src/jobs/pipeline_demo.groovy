// import util.JobBuilder

// def REPO = 'https://github.com/TommyCoo1/jenkins-stuff.git'

// folder('pipelines') {
//     description('Pipeline-Jobs erzeugt via JobBuilder-Klasse')
// }

// // Verschiedene Jenkinsfile-Pfade im selben Repo
// def pipelineConfigs = [
//     [name: 'pipelines/build-main',    branch: 'main',    jenkinsfile: 'Jenkinsfile'],
//     [name: 'pipelines/build-develop', branch: 'develop', jenkinsfile: 'Jenkinsfile'],
// ]

// pipelineConfigs.each { cfg ->
//     new JobBuilder(
//         jobName: cfg.name,
//         repoUrl: REPO,
//         branch: cfg.branch,
//         jenkinsfilePath: cfg.jenkinsfile
//     ).build(this)
// }
