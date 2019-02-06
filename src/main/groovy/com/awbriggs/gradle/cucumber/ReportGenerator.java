package com.awbriggs.gradle.cucumber;

import java.io.File;
import java.util.List;

/**
 * Created by jgelais on 12/10/15.
 */
public interface ReportGenerator {
    void generateReport(List<File> jsonReports);
}
