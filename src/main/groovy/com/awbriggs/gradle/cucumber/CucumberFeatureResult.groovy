package com.awbriggs.gradle.cucumber

/**
 * Created by jgelais on 6/19/15.
 */
class CucumberFeatureResult {
    long totalScenarios = 0
    long failedScenarios = 0
    long totalSteps = 0
    long failedSteps = 0
    long skippedSteps = 0
    long pendingSteps = 0
    long undefinedSteps = 0

    List<String> failureNotes = []

    boolean hadFailures() {
        return (failedScenarios > 0 || failedSteps > 0 || undefinedSteps > 0)
    }
}
