import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement
import spock.lang.Specification
import spock.lang.Unroll

class JobScriptsSpec extends Specification {

    @Unroll
    def "test script #file.name"(File file) {
        given:
        def jm = new JenkinsJobManagement(System.out, [:], new File('.'))

        when:
        new DslScriptLoader(jm).runScript(file.text)

        then:
        noExceptionThrown()

        where:
        file << collectDslFiles()
    }

    static List<File> collectDslFiles() {
        def files = []
        new File('jobs').eachFileRecurse(FileType.FILES) {
            if (it.name.endsWith('.groovy')) {
                files << it
            }
        }
        files
    }
}