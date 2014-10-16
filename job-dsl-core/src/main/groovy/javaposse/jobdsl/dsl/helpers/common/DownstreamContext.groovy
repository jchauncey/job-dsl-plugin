package javaposse.jobdsl.dsl.helpers.common

import javaposse.jobdsl.dsl.helpers.ContextHelper
import javaposse.jobdsl.dsl.helpers.Context

import static groovy.lang.Closure.DELEGATE_FIRST

class DownstreamContext implements Context {
    public static final THRESHOLD_COLOR_MAP = ['SUCCESS': 'BLUE', 'UNSTABLE': 'YELLOW', 'FAILURE': 'RED']
    public static final THRESHOLD_ORDINAL_MAP = ['SUCCESS': 0, 'UNSTABLE': 1, 'FAILURE': 2]

    private final List<DownstreamTriggerContext> triggers = []

    def trigger(String projects,
                @DelegatesTo(value = DownstreamTriggerContext, strategy = DELEGATE_FIRST) Closure closure = null) {
        trigger(projects, null, closure)
    }

    def trigger(String projects, String condition,
                @DelegatesTo(value = DownstreamTriggerContext, strategy = DELEGATE_FIRST) Closure closure = null) {
        trigger(projects, condition, false, closure)
    }

    def trigger(String projects, String condition, boolean triggerWithNoParameters,
                @DelegatesTo(value = DownstreamTriggerContext, strategy = DELEGATE_FIRST) Closure closure = null) {
        trigger(projects, condition, triggerWithNoParameters, [:], closure)
    }

    def trigger(String projects, String condition, boolean triggerWithNoParameters,
                Map<String, String> blockingThresholds,
                @DelegatesTo(value = DownstreamTriggerContext, strategy = DELEGATE_FIRST) Closure closure = null) {
        DownstreamTriggerContext downstreamTriggerContext = new DownstreamTriggerContext()
        downstreamTriggerContext.projects = projects
        downstreamTriggerContext.condition = condition ?: 'SUCCESS'
        downstreamTriggerContext.triggerWithNoParameters = triggerWithNoParameters
        downstreamTriggerContext.blockingThresholdsFromMap(blockingThresholds)

        ContextHelper.executeInContext(closure, downstreamTriggerContext)

        // Validate this trigger
        assert validDownstreamConditionNames.contains(downstreamTriggerContext.condition),
                "Trigger condition has to be one of these values: ${validDownstreamConditionNames.join(',')}"

        triggers << downstreamTriggerContext
    }

    Node createDownstreamNode(boolean isStep = false) {
        def nodeBuilder = NodeBuilder.newInstance()

        def nodeName

        if (isStep) {
            nodeName = 'hudson.plugins.parameterizedtrigger.TriggerBuilder'
        } else {
            nodeName = 'hudson.plugins.parameterizedtrigger.BuildTrigger'
        }

        def downstreamNode = nodeBuilder."${nodeName}" {
            configs {
                triggers.each { DownstreamTriggerContext trigger ->
                    def configName

                    if (isStep) {
                        configName = 'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'
                    } else {
                        configName = 'hudson.plugins.parameterizedtrigger.BuildTriggerConfig'
                    }
                    "${configName}" {
                        projects trigger.projects
                        condition trigger.condition
                        triggerWithNoParameters trigger.triggerWithNoParameters ? 'true' : 'false'

                        if (trigger.hasParameter()) {
                            configs(trigger.createParametersNode().children())
                        } else {
                            configs('class': 'java.util.Collections$EmptyList')
                        }
                        if (isStep && !trigger.blockingThresholds.isEmpty()) {
                            block {
                                trigger.blockingThresholds.each { t ->
                                    "${t.thresholdType}Threshold" {
                                        name t.thresholdName
                                        ordinal t.thresholdOrdinal
                                        color t.thresholdColor
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        downstreamNode
    }

    def validDownstreamConditionNames = [
            'SUCCESS', 'UNSTABLE', 'UNSTABLE_OR_BETTER', 'UNSTABLE_OR_WORSE', 'FAILED', 'ALWAYS'
    ]
}
