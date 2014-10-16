package javaposse.jobdsl.dsl.helpers.publisher

import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.helpers.Context
import javaposse.jobdsl.dsl.helpers.ContextHelper

import static groovy.lang.Closure.DELEGATE_FIRST as DF

class ArchiveJUnitContext implements Context {
    final TestDataPublishersContext testDataPublishersContext
    boolean retainLongStdout = false

    ArchiveJUnitContext(JobManagement jobManagement) {
        testDataPublishersContext = new TestDataPublishersContext(jobManagement)
    }

    void retainLongStdout(boolean retain = true) {
        retainLongStdout = retain
    }

    void testDataPublishers(@DelegatesTo(value = TestDataPublishersContext, strategy = DF) Closure closure) {
        ContextHelper.executeInContext(closure, testDataPublishersContext)
    }
}
