package javaposse.jobdsl.dsl

import static groovy.lang.Closure.DELEGATE_FIRST

interface DslFactory {
    Job job(@DelegatesTo(value = Job, strategy = DELEGATE_FIRST) Closure closure)

    Job job(Map<String, Object> arguments, @DelegatesTo(value = Job, strategy = DELEGATE_FIRST) Closure closure)

    View view(@DelegatesTo(value = View, strategy = DELEGATE_FIRST) Closure closure)

    View view(Map<String, Object> arguments, @DelegatesTo(value = View, strategy = DELEGATE_FIRST) Closure closure)

    Folder folder(@DelegatesTo(value = Folder, strategy = DELEGATE_FIRST) Closure closure)

    ConfigFile configFile(@DelegatesTo(value = ConfigFile, strategy = DELEGATE_FIRST) Closure closure)

    ConfigFile configFile(Map<String, Object> arguments,
                          @DelegatesTo(value = ConfigFile, strategy = DELEGATE_FIRST) Closure closure)

    /**
     * Schedule a job to be run later. Validation of the job name isn't done until after the DSL has run.
     * @param jobName the name of the job to be queued
     */
    void queue(String jobName)

    /**
     * Schedule a job to be run later.
     * @param job the job to be queued
     */
    void queue(Job job)

    InputStream streamFileFromWorkspace(String filePath)

    String readFileFromWorkspace(String filePath)

    String readFileFromWorkspace(String jobName, String filePath)
}
