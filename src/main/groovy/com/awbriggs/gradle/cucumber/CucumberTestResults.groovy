package com.awbriggs.gradle.cucumber

import net.masterthought.cucumber.json.Feature
import org.gradle.internal.logging.progress.ProgressLogger
import org.gradle.internal.logging.progress.ProgressLoggerFactory
import org.slf4j.Logger

/**
 * Created by jgelais on 6/18/15.
 */
class CucumberTestResults {
    private final ProgressLoggerFactory progressLoggerFactory
    private ProgressLogger progressLogger
    private final Logger logger
    private final FeatureTotals featureTotals

    CucumberTestResults(ProgressLoggerFactory factory, Logger logger) {
        this.progressLoggerFactory = factory
        this.logger = logger
        featureTotals = new FeatureTotals()
    }

    synchronized void afterFeature(Feature result) {
        featureTotals.addFeatureResults(result)
        progressLogger.progress(shortSummary)
    }

    private String getShortSummary() {
        return """${featureTotals.getCompletedFeatures()} of ${getLabeledCount(featureTotals.getTotalFeatures(), 'feature')} completed.
                ${featureTotals.getCompletedFeatures()} completed, ${featureTotals.getFailedScenarios()} failed]"""
    }

    private String getLongSummary() {
        return """${getLabeledCount(featureTotals.getCompletedFeatures(), 'feature')} completed.
    Scenarios [${formatCount(featureTotals.getCompletedScenarios())} completed, ${formatCount(featureTotals.getFailedSteps())} failed]
        Steps [${formatCount(featureTotals.getCompletedSteps())} completed, ${formatCount(featureTotals.getFailedSteps())} failed, ${formatCount(featureTotals.getSkippedSteps())} skipped, ${formatCount(featureTotals.getPendingSteps())} pending]"""
    }

    private static String formatCount(long count) {
        return count.toString().padLeft(5)
    }

    private static String getLabeledCount(long count, String noun) {
        String labeledCount = "$count $noun"
        if (count != 0) {
            labeledCount += 's'
        }

        return labeledCount
    }

    void beforeSuite(int totalFeatures) {
        featureTotals.setTotalFeatures(totalFeatures)
        progressLogger = progressLoggerFactory.newOperation(CucumberTestResults)
        progressLogger.setDescription('Run tests')
        progressLogger.started()
        progressLogger.progress(shortSummary)
    }

    void afterSuite() {
        progressLogger.completed()
        if (hadFailures()) {
            logger.error(longSummary)
        }
    }

    private boolean hadFailures() {
        return (featureTotals.getFailedScenarios() > 0 || featureTotals.getFailedSteps() > 0)
    }

}
