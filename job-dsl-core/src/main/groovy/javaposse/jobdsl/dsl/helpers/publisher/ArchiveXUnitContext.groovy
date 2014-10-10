package javaposse.jobdsl.dsl.helpers.publisher

import javaposse.jobdsl.dsl.helpers.Context
import javaposse.jobdsl.dsl.helpers.ContextHelper

class ArchiveXUnitContext implements Context {
    ArchiveXUnitThresholdContext failedThresholdsContext = new ArchiveXUnitThresholdContext()
    ArchiveXUnitThresholdContext skippedThresholdsContext = new ArchiveXUnitThresholdContext()
    ThresholdMode thresholdMode = ThresholdMode.NUMBER
    int timeMargin = 3000
    List<ArchiveXUnitResultFileContext> resultFiles = []

    void failedThresholds(@DelegatesTo(ArchiveXUnitThresholdContext) Closure thresholdsClosure) {
        ContextHelper.executeInContext(thresholdsClosure, failedThresholdsContext)
    }

    void skippedThresholds(@DelegatesTo(ArchiveXUnitThresholdContext) Closure thresholdsClosure) {
        ContextHelper.executeInContext(thresholdsClosure, skippedThresholdsContext)
    }

    void thresholdMode(ThresholdMode thresholdMode) {
        this.thresholdMode = thresholdMode
    }

    void timeMargin(int timeMargin) {
        this.timeMargin = timeMargin
    }

    void aUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('AUnitJunitHudsonTestType', resultFileClosure)
    }

    void boostTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('BoostTestJunitHudsonTestType', resultFileClosure)
    }

    void cTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('CTestType', resultFileClosure)
    }

    void check(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('CheckType', resultFileClosure)
    }

    void cppTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('CppTestJunitHudsonTestType', resultFileClosure)
    }

    void cppUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('CppUnitJunitHudsonTestType', resultFileClosure)
    }

    void embUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('EmbUnitType', resultFileClosure)
    }

    void fpcUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('FPCUnitJunitHudsonTestType', resultFileClosure)
    }

    void googleTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('GoogleTestType', resultFileClosure)
    }

    void jUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('JUnitType', resultFileClosure)
    }

    void msTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('MSTestJunitHudsonTestType', resultFileClosure)
    }

    void mbUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('MbUnitType', resultFileClosure)
    }

    void nUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('NUnitJunitHudsonTestType', resultFileClosure)
    }

    void phpUnit(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('PHPUnitJunitHudsonTestType', resultFileClosure)
    }

    void qTestLib(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('QTestLibType', resultFileClosure)
    }

    void unitTest(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('UnitTestJunitHudsonTestType', resultFileClosure)
    }

    void valgrind(@DelegatesTo(ArchiveXUnitResultFileContext) Closure resultFileClosure) {
        addResultFile('ValgrindJunitHudsonTestType', resultFileClosure)
    }

    void customTool(@DelegatesTo(ArchiveXUnitCustomToolContext) Closure resultFileClosure) {
        ArchiveXUnitResultFileContext resultFileContext = new ArchiveXUnitCustomToolContext()
        ContextHelper.executeInContext(resultFileClosure, resultFileContext)

        resultFiles << resultFileContext
    }

    private void addResultFile(String type, Closure resultFileClosure) {
        ArchiveXUnitResultFileContext resultFileContext = new ArchiveXUnitResultFileContext(type)
        ContextHelper.executeInContext(resultFileClosure, resultFileContext)

        resultFiles << resultFileContext
    }

    static enum ThresholdMode {
        NUMBER(1),
        PERCENT(2)

        final int xmlValue

        ThresholdMode(int xmlValue) {
            this.xmlValue = xmlValue
        }
    }
}
