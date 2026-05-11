package jobs

job('demo-freestyle') {
    description('Per Job DSL aus Git erzeugter Freestyle-Job')
    label('')
    steps {
        shell('echo "Hallo aus dem generierten Job TEST TEST"')
    }
}