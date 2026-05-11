package jobs

def helpers = evaluate(readFileFromWorkspace('src/util/helpers.groovy'))

folder('simple-demos') {
    description('Jobs erstellt mit Helper-Script')
}

helpers.createSimpleJob(this, [
    jobName: 'simple-demos/hello-world',
    description: 'Ein einfacher Hello World Job',
    shellCommand: 'echo "Hello from the demo script!"'
])

def maintenanceTasks = [
    [name: 'cleanup-tmp', cmd: 'rm -rf /tmp/*'],
    [name: 'check-disk',  cmd: 'df -h'],
    [name: 'system-info', cmd: 'uname -a']
]

maintenanceTasks.each { task ->
    helpers.createSimpleJob(this, [
        jobName: "simple-demos/${task.name}",
        description: "Maintenance Task: ${task.name}",
        shellCommand: task.cmd
    ])
}