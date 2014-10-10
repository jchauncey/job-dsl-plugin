package javaposse.jobdsl.dsl

interface DslFactory {
    Job job(@DelegatesTo(Job) Closure closure)

    Job job(Map<String, Object> arguments, @DelegatesTo(Job) Closure closure)

    View view(@DelegatesTo(View) Closure closure)

    View view(Map<String, Object> arguments, @DelegatesTo(View) Closure closure)

    Folder folder(@DelegatesTo(Folder) Closure closure)

    ConfigFile configFile(@DelegatesTo(ConfigFile) Closure closure)

    ConfigFile configFile(Map<String, Object> arguments, @DelegatesTo(ConfigFile) Closure closure)

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
