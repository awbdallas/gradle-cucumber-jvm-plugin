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
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteForExistingSourceSetWhenCreatedInExtension() {
        final String suiteName = 'myTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanCreateSourceSet() {
        final String suiteName = 'myNewTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanCreateSourceSetWhenCreatedInExtension() {
        final String suiteName = 'myNewTest'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName)
        project.tasks.findByName(suiteName).sourceSet == project.sourceSets.findByName(suiteName)
    }

    def testAddCucumberSuiteCanTakeOverTestSourceSet() {
        final String suiteName = 'test'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName) instanceof CucumberTask
    }

    def testAddCucumberSuiteCanTakeOverTestSourceSetWhenCreatedInExtension() {
        final String suiteName = 'test'

        when:
        project.apply(plugin: 'java')
        project.apply(plugin: pluginName)
        CucumberExtension extension = project.extensions.findByType(CucumberExtension.class)
        extension.addSuite(suiteName, project)

        then:
        project.tasks.findByName(suiteName) instanceof CucumberTask
    }
}
