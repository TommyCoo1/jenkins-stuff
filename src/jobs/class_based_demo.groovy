// package jobs

// import util.JobBuilder

// // multiple jobs
// def microservices = ['auth-service', 'user-service', 'payment-service']

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
