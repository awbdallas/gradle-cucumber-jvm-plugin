package com.awbriggs.gradle.cucumber

import groovy.util.logging.Slf4j
import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult

@SuppressWarnings('DuplicateStringLiteral')
@Slf4j
class CucumberIntegrationSpec extends IntegrationSpec {
    def setup() {
        copyResources('teststeps/TestSteps.groovy', 'src/test/groovy/cucumber/steps/TestSteps.groovy')
        buildFile << '''
            apply plugin: 'groovy'
            apply plugin: 'com.awbriggs.cucumber-jvm'

            addCucumberSuite 'test'

            cucumber {
                tags = ["@test", "@happypath", "~@ignore"]
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

        when:
        ExecutionResult result = runTasksSuccessfully('test')
        log.info(result.standardOutput)

        then:
        result.wasExecuted(':test')
        fileExists('build/reports/test/cucumber-html-reports/overview-features.html')
    }

    def testFailingBackgroundStep() {
        given:
        copyResources('testfeatures/failing-background-test.feature',
                'src/test/resources/features/failing-background-test.feature')

        when:
        ExecutionResult result = runTasksWithFailure('test')

        then:
        fileExists('build/reports/test/cucumber-html-reports/overview-failures.html')
        result.standardError.contains("build/reports/test/cucumber-html-reports/overview-failures.html")
    }

    def testSysProps() {
        given:
        copyResources('testfeatures/check-sysprop.feature', 'src/test/resources/features/check-sysprop.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')

        then:
        result.wasExecuted(':test')
    }

    def ignoreScenario() {
        given:
        copyResources('testfeatures/ignored-test.feature', 'src/test/resources/features/ignored-test.feature')

        when:
        ExecutionResult result = runTasksSuccessfully('test')

        then:
        result.wasExecuted(':test')
    }

    def happyPathReportsDirectoryCreated() {
        given:
        copyResources('testfeatures/happypath.feature', 'src/test/resoucres/features/happypath.feature')


        when:
        ExecutionResult result = runTasksSuccessfully('test')

        then:
        fileExists('build/reports/test/cucumber-html-reports/overview-features.html')

    }

}
