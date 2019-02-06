package com.awbriggs.gradle.cucumber;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

public class CucumberPlugin implements Plugin<Project> {

    public void apply(Project project) {
        project.getPlugins().apply(JavaPlugin.class);
        project.getExtensions().create("cucumber", CucumberExtension.class, project, this);
    }

}
