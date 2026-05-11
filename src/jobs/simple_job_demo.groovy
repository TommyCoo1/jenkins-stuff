package jobs

import util.SimpleJobBuilder

// Ordner für die Demo-Jobs
folder('simple-demos') {
    description('Jobs erstellt mit dem SimpleJobBuilder')
}

// Beispiel 1: Ein ganz einfacher Job
new SimpleJobBuilder(
    jobName: 'simple-demos/hello-world',
    description: 'Ein einfacher Hello World Job',
    shellCommand: 'echo "Hello from the demo script!"'
).build(this)

// Beispiel 2: Eine Liste von Jobs generieren
def maintenanceTasks = [
    [name: 'cleanup-tmp', cmd: 'rm -rf /tmp/*'],
    [name: 'check-disk',  cmd: 'df -h'],
    [name: 'system-info', cmd: 'uname -a']
]

maintenanceTasks.each { task ->
    new SimpleJobBuilder(
        jobName: "simple-demos/${task.name}",
        description: "Maintenance Task: ${task.name}",
        shellCommand: task.cmd
    ).build(this)
}
