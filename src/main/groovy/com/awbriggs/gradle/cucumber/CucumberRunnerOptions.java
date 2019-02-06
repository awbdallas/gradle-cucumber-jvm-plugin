package com.awbriggs.gradle.cucumber;

import java.util.List;

/**
 * Created by jgelais on 6/16/15.
 */
public interface CucumberRunnerOptions {

    List<String> getTags();

    List<String> getStepDefinitionRoots();

    List<String> getFeatureRoots();

    List<String> getPlugins();

    Boolean getIsDryRun();

    Boolean getIsMonochrome();

    Boolean getIsStrict();

    Boolean getJunitReport();

    String getSnippets();

    Integer getMaxParallelForks();
}
