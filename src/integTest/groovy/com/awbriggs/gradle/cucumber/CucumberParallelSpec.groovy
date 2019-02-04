package com.awbriggs.gradle.cucumber

import groovy.util.logging.Slf4j
import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult
import org.gradle.api.GradleException

@Slf4j
class CucumberParallelSpec extends IntegrationSpec {

    def setup() {
        copyResources('teststeps/TestSteps.groovy', 'src/test/groovy/cucumber/steps/TestSteps.groovy')
        buildFile << '''
            apply plugin: 'groovy'
            apply plugin: 'com.awbriggs.cucumber-jvm'

            addCucumberSuite 'test'
                        
            cucumber {
                tags = ["@test", "@happypath", "~@ignore"]
                maxParallelForks = 2
            }

            repositories {
                jcenter()
            }

            dependencies {
                compile 'org.codehaus.groovy:groovy-all:2.4.1'
                compile 'info.cukes:cucumber-java:1.2.2'
            }

            test {
                systemProperty 'foo', 'bar'
            }
          
        '''.stripIndent()
    }

    def testHappyPath() {
        given:
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath1.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath2.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath3.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')
        log.info(result.standardOutput)
        print(result.standardOutput)

        then:
        result.wasExecuted(':test')
    }

    def testFailingBackgroundStep() {
        given:
        copyResources('testfeatures/failing-background-test.feature', 'src/test/resources/features/failing-background-test.feature')
        copyResources('testfeatures/failing-background-test.feature', 'src/test/resources/features/failing-background-test1.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath1.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')
        log.info(result.standardOutput)

        then:
        thrown GradleException
    }

    def testSysProps() {
        given:
        copyResources('testfeatures/check-sysprop.feature', 'src/test/resources/features/check-sysprop.feature')
        copyResources('testfeatures/check-sysprop.feature', 'src/test/resources/features/check-sysprop1.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath1.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath2.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')
        log.info(result.standardOutput)

        then:
        result.wasExecuted(':test')
    }

    def ignoreScenario() {
        given:
        copyResources('testfeatures/ignored-test.feature', 'src/test/resources/features/ignored-test.feature')
        copyResources('testfeatures/ignored-test.feature', 'src/test/resources/features/ignored-test1.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath.feature')
        copyResources('testfeatures/happypath.feature', 'src/test/resources/features/happypath1.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')
        log.info(result.standardOutput)

        then:
        result.wasExecuted(':test')

    }


}
