package com.awbriggs.gradle.cucumber;

import org.gradle.internal.logging.progress.ProgressLogger;
import org.gradle.internal.logging.progress.ProgressLoggerFactory;
import org.slf4j.Logger;

import net.masterthought.cucumber.json.Feature;

/**
 * Created by jgelais on 6/18/15.
 */
public class CucumberTestResults {
    private final ProgressLoggerFactory progressLoggerFactory;
    private final Logger logger;
    private final FeatureTotals featureTotals;
    private ProgressLogger progressLogger;


    CucumberTestResults(ProgressLoggerFactory factory, Logger logger) {
        this.progressLoggerFactory = factory;
        this.logger = logger;
        featureTotals = new FeatureTotals();
    }

    synchronized void afterFeature(Feature result) {
        featureTotals.addFeatureResults(result);
        progressLogger.progress(getShortSummary());
    }

    private String getShortSummary() {
        return String.format("%s of %s completed.\n[%s completed, %s failed]", featureTotals.getCompletedFeatures(), getLabeledCount(featureTotals.getTotalFeatures(), "feature"),
                featureTotals.getCompletedFeatures(), featureTotals.getFailedScenarios());
    }

    private String getLongSummary() {
        return String.format("%s Completed.\nScenarios [%5s completed, %5s failed]\nSteps[%5s completed, %5s failed, %5s skipped, %5s pending]",
                getLabeledCount(featureTotals.getCompletedFeatures(), "feature"),
                featureTotals.getCompletedScenarios(), featureTotals.getFailedScenarios(),
                featureTotals.getCompletedSteps(), featureTotals.getFailedSteps(), featureTotals.getSkippedSteps(), featureTotals.getPendingSteps());
    }

    private static String getLabeledCount(int count, String noun) {
        String labeledCount = count + " " + noun;
        if (count != 0) {
            labeledCount += "s";
        }

        return labeledCount;
    }

    void beforeSuite(int totalFeatures) {
        featureTotals.setTotalFeatures(totalFeatures);
        progressLogger = progressLoggerFactory.newOperation(CucumberTestResults.class);
        progressLogger.setDescription("Run tests");
        progressLogger.started();
        progressLogger.progress(getShortSummary());
    }

    void afterSuite() {
        progressLogger.completed();
        if (hadFailures()) {
            logger.error(getLongSummary());
        }
    }

    private boolean hadFailures() {
        return (featureTotals.getFailedScenarios() > 0 || featureTotals.getFailedSteps() > 0);
    }

}
