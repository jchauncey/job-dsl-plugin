package javaposse.jobdsl.dsl.helpers.publisher

import javaposse.jobdsl.dsl.helpers.Context
import javaposse.jobdsl.dsl.helpers.ContextHelper

import static groovy.lang.Closure.DELEGATE_FIRST as DF

class ArchiveXUnitContext implements Context {
    ArchiveXUnitThresholdContext failedThresholdsContext = new ArchiveXUnitThresholdContext()
    ArchiveXUnitThresholdContext skippedThresholdsContext = new ArchiveXUnitThresholdContext()
    ThresholdMode thresholdMode = ThresholdMode.NUMBER
    int timeMargin = 3000
    List<ArchiveXUnitResultFileContext> resultFiles = []

    void failedThresholds(@DelegatesTo(value = ArchiveXUnitThresholdContext, strategy = DF) Closure closure) {
        ContextHelper.executeInContext(closure, failedThresholdsContext)
    }

    void skippedThresholds(@DelegatesTo(value = ArchiveXUnitThresholdContext, strategy = DF) Closure closure) {
        ContextHelper.executeInContext(closure, skippedThresholdsContext)
    }

    void thresholdMode(ThresholdMode thresholdMode) {
        this.thresholdMode = thresholdMode
    }

    void timeMargin(int timeMargin) {
        this.timeMargin = timeMargin
    }

    void aUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('AUnitJunitHudsonTestType', resultFileClosure)
    }

    void boostTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('BoostTestJunitHudsonTestType', resultFileClosure)
    }

    void cTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('CTestType', resultFileClosure)
    }

    void check(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('CheckType', resultFileClosure)
    }

    void cppTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('CppTestJunitHudsonTestType', resultFileClosure)
    }

    void cppUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('CppUnitJunitHudsonTestType', resultFileClosure)
    }

    void embUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('EmbUnitType', resultFileClosure)
    }

    void fpcUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('FPCUnitJunitHudsonTestType', resultFileClosure)
    }

    void googleTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('GoogleTestType', resultFileClosure)
    }

    void jUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('JUnitType', resultFileClosure)
    }

    void msTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('MSTestJunitHudsonTestType', resultFileClosure)
    }

    void mbUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('MbUnitType', resultFileClosure)
    }

    void nUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('NUnitJunitHudsonTestType', resultFileClosure)
    }

    void phpUnit(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('PHPUnitJunitHudsonTestType', resultFileClosure)
    }

    void qTestLib(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('QTestLibType', resultFileClosure)
    }

    void unitTest(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('UnitTestJunitHudsonTestType', resultFileClosure)
    }

    void valgrind(@DelegatesTo(value = ArchiveXUnitResultFileContext, strategy = DF) Closure resultFileClosure) {
        addResultFile('ValgrindJunitHudsonTestType', resultFileClosure)
    }

    void customTool(@DelegatesTo(value = ArchiveXUnitCustomToolContext, strategy = DF) Closure resultFileClosure) {
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
