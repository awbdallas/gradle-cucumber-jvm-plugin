package com.awbriggs.gradle.cucumber;

import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.json.Feature;

public class FeatureTotals {

    private int completedFeatures;
    private int totalScenarios;
    private int failedScenarios;
    private int totalSteps;
    private int failedSteps;
    private int skippedSteps;
    private int pendingSteps;
    private int undefinedSteps;
    private int totalFeatures;
    private int completedScenarios;
    private int completedSteps;

    private List<String> failureNotes;

    public FeatureTotals() {
        completedFeatures = 0;
        totalScenarios = 0;
        failedScenarios = 0;
        totalScenarios = 0;
        totalSteps = 0;
        failedSteps = 0;
        skippedSteps = 0;
        pendingSteps = 0;
        undefinedSteps = 0;
        totalFeatures = 0;
        completedScenarios = 0;
        completedSteps = 0;

        failureNotes = new ArrayList<>();
    }

    public void addFeatureResults(Feature result) {
        completedFeatures++;
        completedScenarios += result.getPassedScenarios() + result.getFailedScenarios();
        failedScenarios += result.getFailedScenarios();
        completedSteps += result.getPassedSteps() + result.getFailedSteps();
        failedSteps += result.getFailedSteps();
        skippedSteps += result.getSkippedSteps();
        pendingSteps += result.getPendingSteps();
        undefinedSteps += result.getUndefinedSteps();
    }

    public int getCompletedFeatures() {
        return completedFeatures;
    }

    public int getTotalScenarios() {
        return totalScenarios;
    }

    public int getFailedScenarios() {
        return failedScenarios;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getFailedSteps() {
        return failedSteps;
    }

    public int getSkippedSteps() {
        return skippedSteps;
    }

    public int getPendingSteps() {
        return pendingSteps;
    }

    public int getUndefinedSteps() {
        return undefinedSteps;
    }

    public int getTotalFeatures() {
        return totalFeatures;
    }

    public List<String> getFailureNotes() {
        return failureNotes;
    }

    public void setTotalFeatures(int totalFeatures) {
        this.totalFeatures = totalFeatures;
    }

    public int getCompletedScenarios() {
        return completedScenarios;
    }

    public int getCompletedSteps() {
        return completedSteps;
    }

}
