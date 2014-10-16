package javaposse.jobdsl.dsl.helpers.common

import javaposse.jobdsl.dsl.helpers.Context
import javaposse.jobdsl.dsl.helpers.ContextHelper

import static groovy.lang.Closure.DELEGATE_FIRST

class BuildPipelineContext implements Context {
    List<Node> parameterNodes = []

    def parameters(@DelegatesTo(value = DownstreamTriggerContext, strategy = DELEGATE_FIRST) Closure closure) {
        DownstreamTriggerContext downstreamTriggerContext = new DownstreamTriggerContext()
        ContextHelper.executeInContext(closure, downstreamTriggerContext)
        parameterNodes.addAll(downstreamTriggerContext.createParametersNode().children())
    }
}
