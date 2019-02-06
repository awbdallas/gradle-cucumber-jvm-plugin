package com.awbriggs.gradle.cucumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.plugins.ide.idea.IdeaPlugin;

public class CucumberExtension {
    private static final String DEFAULT_PARENT_SOURCESET = "main";

    private List<String> tags;
    private List<String> stepDefinitionRoots;
    private List<String> featureRoots;
    private List<String> plugins;

    private Integer maxParallelForks;

    private String snippets;

    private Boolean isDryRun;
    private Boolean isMonochrome;
    private Boolean isStrict;
    private Boolean junitReport;
    private Boolean ignoreFailures;

    private final Project project;

    private final CucumberPlugin plugin;

    public CucumberExtension(Project project, CucumberPlugin plugin) {
        this.project = project;
        this.plugin = plugin;
    }

    public List<String> getTags() {
        if (tags == null) {
            return new ArrayList<>();
        }

        return tags;
    }

    public Integer getMaxParallelForks() {
        if (maxParallelForks == null) {
            return 1;
        }

        return maxParallelForks;
    }

    public List<String> getStepDefinitionRoots() {
        if (stepDefinitionRoots == null) {
            return Arrays.asList("cucumber.steps", "cucumber.hooks");
        }

        return stepDefinitionRoots;
    }

    public List<String> getFeatureRoots() {
        if (featureRoots == null) {
            return Collections.singletonList("features");
        }

        return featureRoots;
    }

    public List<String> getPlugins() {
        if (plugins == null) {
            return new ArrayList<>();
        }

        return plugins;
    }

    public Boolean isDryRun() {
        if (isDryRun == null) {
            return false;
        }

        return isDryRun;
    }

    public Boolean isMonochrome() {
        if (isMonochrome == null) {
            return false;
        }

        return isMonochrome;
    }

    public Boolean isStrict() {
        if (isStrict == null) {
            return false;
        }

        return isStrict;
    }

    public String getSnippets() {
        if (snippets == null) {
            return "camelcase";
        }
        return snippets;
    }

    public Boolean isJunitReportEnabled() {
        if (junitReport == null) {
            return false;
        }

        return junitReport;
    }

    public Boolean isIgnoringFailures() {
        if (ignoreFailures == null) {
            return false;
        }

        return ignoreFailures;
    }


    void setMaxParallelForks(Integer maxParallelForks) {
        if (maxParallelForks < 1) {
            throw new IllegalArgumentException("Invalid fork amount. Must be greater than 1.");
        }
        this.maxParallelForks = maxParallelForks;
    }

    void setSnippets(String snippets) {
        if (!Arrays.asList("camelcase", "underscore", null).contains(snippets)) {
            throw new IllegalArgumentException("Legal values for snippets include [camelcase, underscore].");
        }
        this.snippets = snippets;
    }


    public void setDryRun(Boolean dryRun) {
        isDryRun = dryRun;
    }

    public void setMonochrome(Boolean monochrome) {
        isMonochrome = monochrome;
    }

    public void setStrict(Boolean strict) {
        isStrict = strict;
    }

    public void setJunitReport(Boolean junitReport) {
        this.junitReport = junitReport;
    }

    public void setIgnoreFailures(Boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setStepDefinitionRoots(List<String> stepDefinitionRoots) {
        this.stepDefinitionRoots = stepDefinitionRoots;
    }

    public void setFeatureRoots(List<String> featureRoots) {
        this.featureRoots = featureRoots;
    }

    public void setPlugins(List<String> plugins) {
        this.plugins = plugins;
    }

    public void setSuites(List<String> suiteNames) {
        suiteNames.forEach(name -> this.addSuite(name, project));
    }

    protected void addSuite(String sourceSetName, Project project) {
        JavaPluginConvention convention = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSet parentSourceSet = convention.getSourceSets().getByName(DEFAULT_PARENT_SOURCESET);

        SourceSet newSourceSet = (convention.getSourceSets().findByName(sourceSetName) == null) ? convention.getSourceSets().create(sourceSetName) : convention.getSourceSets().findByName(sourceSetName);
        newSourceSet.setCompileClasspath(newSourceSet.getCompileClasspath().plus(parentSourceSet.getOutput()).plus(parentSourceSet.getCompileClasspath()));
        newSourceSet.setRuntimeClasspath(newSourceSet.getOutput().plus(newSourceSet.getCompileClasspath()));

        CucumberTask task = project.getTasks().replace(sourceSetName, CucumberTask.class);
        task.dependsOn(newSourceSet.getClassesTaskName());
        task.sourceSet(newSourceSet);

        project.getPlugins().withType(IdeaPlugin.class, plugin -> {
            plugin.getModel().getModule().getTestSourceDirs().addAll(newSourceSet.getAllSource().getSrcDirs());
            plugin.getModel().getModule().getTestSourceDirs().addAll(newSourceSet.getResources().getSrcDirs());
        });

    }


}
