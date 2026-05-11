// import util.JobBuilder

// // def mainJob = new JobBuilder(
// //     jobName: 'class-based-demo-job',
// //     repoUrl: 'https://github.com/TommyCoo1/jenkins-stuff.git',
// //     branch: 'main',
// //     jenkinsfilePath: 'pipelines/demo.Jenkinsfile'
// // )
// // 'this' = Job DSL Skript
// // mainJob.build(this)


// // multiple jobs
// def microservices = ['auth-service', 'user-service', 'payment-service']

// // microservices.each { serviceName ->
// //     new JobBuilder(
// //         jobName: "microservice-${serviceName}",
// //         repoUrl: "https://github.com/TommyCoo1/${serviceName}.git",
// //         jenkinsfilePath: 'Jenkinsfile'
// //     ).build(this)
// // }

// // Folder for jobs
// folder('microservices') {
//     description('Beinhaltet alle Microservice-Build-Jobs')
// }

// // jobs to folder with prefix
// microservices.each { serviceName ->
//     new JobBuilder(
//         jobName: "microservices/build-${serviceName}",
//         repoUrl: "https://github.com/TommyCoo1/${serviceName}.git"
//     ).build(this)
// }
