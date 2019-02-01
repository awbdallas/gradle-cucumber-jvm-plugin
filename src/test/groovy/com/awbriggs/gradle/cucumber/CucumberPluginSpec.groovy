package com.awbriggs.gradle.cucumber

import nebula.test.PluginProjectSpec

/**
 * Created by jgelais on 6/15/15.
 */
@SuppressWarnings('DuplicateStringLiteral')
@SuppressWarnings('DuplicateMapLiteral')
class CucumberPluginSpec extends PluginProjectSpec {

    @Override
    @SuppressWarnings('GetterMethodCouldBeProperty')
    String getPluginName() {
        return 'cucumber-jvm'
    }

    def testAddCucumberSuiteForExistingSourceSet() {
        final String suiteName = 'myTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.sourceSets.create(suiteName)
        project.addCucumberSuite(suiteName)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteForExistingSourceSetWhenCreatedInExtension() {
        final String suiteName = 'myTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.sourceSets.create(suiteName)
        project.cucumber.suite(suiteName)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanCreateSourceSet() {
        final String suiteName = 'myNewTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.addCucumberSuite(suiteName)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanCreateSourceSetWhenCreatedInExtension() {
        final String suiteName = 'myNewTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.cucumber.suite(suiteName)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanTakeOverTestSourceSet() {
        final String suiteName = 'test'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.addCucumberSuite(suiteName)

        then:
        project.tasks.findByName(suiteName) instanceof CucumberTask
    }

    def testAddCucumberSuiteCanTakeOverTestSourceSetWhenCreatedInExtension() {
        final String suiteName = 'test'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        project.cucumber.suite(suiteName)

        then:
        project.tasks.findByName(suiteName) instanceof CucumberTask
    }
}
