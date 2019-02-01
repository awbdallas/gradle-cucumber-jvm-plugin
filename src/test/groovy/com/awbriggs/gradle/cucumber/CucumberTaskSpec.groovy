package com.awbriggs.gradle.cucumber

import groovy.mock.interceptor.MockFor
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by jgelais on 7/8/15.
 */
@SuppressWarnings('DuplicateStringLiteral')
@SuppressWarnings('DuplicateMapLiteral')
class CucumberTaskSpec extends Specification {

    def testTaskExecution() {
        def cucumberRunnerMock = new MockFor(CucumberRunner)
        cucumberRunnerMock.demand.run { a, b, c ->
            true
        }

        expect:
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin: 'cucumber-jvm')
        project.addCucumberSuite('test')
        CucumberTask task = (CucumberTask) project.tasks.getByPath('test')
        cucumberRunnerMock.use {
            task.runTests()
        }
    }

    def testTaskExecutionWithIdeaPlugin() {
        def cucumberRunnerMock = new MockFor(CucumberRunner)
        cucumberRunnerMock.demand.run { a, b, c ->
            true
        }

        expect:
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin: 'idea')
        project.apply(plugin: 'cucumber-jvm')
        project.addCucumberSuite('test')
        CucumberTask task = (CucumberTask) project.tasks.getByPath('test')
        cucumberRunnerMock.use {
            task.runTests()
        }
    }
}
