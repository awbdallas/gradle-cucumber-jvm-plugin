package com.awbriggs.gradle.cucumber

import org.gradle.api.Project

/**
 * Created by jgelais on 6/11/15.
 */
class CucumberExtension {
    /**
     * Tags used to filter which scenarios should be run.
     *
     * Defaults to an empty list.
     */
    List<String> tags = []

    /**
     * Maximum number of parallel threads to run features on.
     *
     * Defaults to 1.
     */
    int maxParallelForks = 1

    /**
     *
     */
    List<String> stepDefinitionRoots = ['cucumber.steps', 'cucumber.hooks']

    /**
     *
     */
    List<String> featureRoots = ['features']

    /**
     *
     */
    List<String> plugins = []

    /**
     *
     */
    boolean isDryRun = false

    /**
     *
     */
    boolean isMonochrome = false

    /**
     *
     */
    boolean isStrict = false

    /**
     *
     */
    String snippets = 'camelcase'

    /**
     * Property to enable/disable junit reporting
     */
    boolean junitReport = false

    /**
     * Property to cause or prevent build failure when cucumber tests fail
     */
    boolean ignoreFailures = false

    private final Project project

    private final CucumberPlugin plugin

    CucumberExtension(Project project, CucumberPlugin plugin) {
        this.project = project
        this.plugin = plugin
    }

    def cucumber(Closure closure) {
        closure.setDelegate this
        closure.call()
    }

    void setMaxParallelForks(int maxParallelForks) {
        if (maxParallelForks < 1) {
            throw new IllegalArgumentException('maxParallelForks most be a positive integer. ' +
                    "You supplied: $maxParallelForks")
        }
        this.maxParallelForks = maxParallelForks
    }

    @SuppressWarnings('DuplicateStringLiteral')
    void setSnippets(String snippets) {
        if (!['camelcase', 'underscore', null].contains(snippets)) {
            throw new IllegalArgumentException('Legal values for snippets include [camelcase, underscore]. ' +
                    "You provided: ${snippets}")
        }
        this.snippets = snippets
    }

    /**
     * Register a new Cucumber suite in the project.
     *
     * @param name The name of the suite, e.g. "cucumberTest".
     */
    void suite(String name) {
        plugin.addSuite(name, project)
    }
}
