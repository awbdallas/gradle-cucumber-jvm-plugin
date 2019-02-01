package com.awbriggs.gradle.cucumber

/**
 * Created by jgelais on 6/16/15.
 */
interface CucumberRunnerOptions {

    List<String> getTags()

    List<String> getStepDefinitionRoots()

    List<String> getFeatureRoots()

    List<String> getPlugins()

    Boolean getIsDryRun()

    Boolean getIsMonochrome()

    Boolean getIsStrict()

    Boolean getJunitReport()

    String getSnippets()

    Integer getMaxParallelForks()

}
