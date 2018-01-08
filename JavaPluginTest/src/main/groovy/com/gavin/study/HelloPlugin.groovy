package com.gavin.study

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.Task
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.artifacts.result.DefaultResolvedDependencyResult
import org.gradle.api.internal.artifacts.result.DefaultUnresolvedDependencyResult
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.StandardOutputListener
import org.gradle.api.tasks.TaskState

class HelloPlugin implements Plugin<Project> {

    // 在project执行 apply 当前插件的时候，会回调此方法
    @Override
    void apply(Project project) {
        project.extensions.create('hello', HelloExtension, 'hello, man') // 后面的参数，是HelloExtension构造函数的参数

        project.task('helloOut') << {
            println project.extensions.hello.sayHi
        }


        // 多域名的管理配置
        def multiDomain = project.container(MultiDomain) // container()方法内部会创建MultiDomain带有一个String参数的实例
        multiDomain.all {
            sourceDir = project.file("src/docs/${name}") // 配置默认的 sourceDir
        }
        project.extensions.multiDomain = multiDomain // 设置到 project.extensions上面

        project.task('printMultiDomain') << {
            project.extensions.multiDomain.each { domain ->
                println domain.name + ' : ' + domain.sourceDir.absolutePath
            }
        }

        project.getGradle().addProjectEvaluationListener(new ProjectEvaluationListener() {
            @Override
            void beforeEvaluate(Project p) {

            }

            @Override
            void afterEvaluate(Project p, ProjectState projectState) {

            }
        })

        // task 执行 构建task依赖图的完成的回调函数
        project.getGradle().addListener(new TaskExecutionGraphListener(){

            // 构建task依赖图 完成的回调， 回调参数graph可以获取到需要构建的任务的所有依赖
            @Override
            void graphPopulated(TaskExecutionGraph taskExecutionGraph) {
                taskExecutionGraph.allTasks.each { task ->
                    // 获取到当前构建图的所有task
                    println "task name is ${task.getName()}"
                }
            }
        })

        // task 执行的回调函数
        project.getGradle().addListener(new TaskExecutionListener(){
            @Override
            void beforeExecute(Task task) {

            }

            @Override
            void afterExecute(Task task, TaskState taskState) {

            }
        })

        project.getGradle().addListener(new DependencyResolutionListener(){
            @Override
            void beforeResolve(ResolvableDependencies resolvableDependencies) {

            }

            @Override
            void afterResolve(ResolvableDependencies resolvableDependencies) { // 处理依赖完成的回调函数
                // 下面例子： 检测release构建如果含有snapshot包或beta包，就会抛出异常
                def projectPath = resolvableDependencies.path.toLowerCase()
                // 对应的是releaseCompileClassPath task
                if(projectPath.contains("releasecompile")){
                    resolvableDependencies.resolutionResult.allDependencies.each { dependecy ->
                        if(dependecy instanceof DefaultUnresolvedDependencyResult){ // 没有处理的依赖
                        }else if (dependecy instanceof DefaultResolvedDependencyResult){
                            String selected = dependecy.selected
                            def from = dependecy.from
                            if(selected != null && (selected.toLowerCase().contains('snapshot') || selected.toLowerCase().contains('beta'))){
                                String errorMsg = "${selected} from ${from} contains a snapshot or beta version"
                                throw new IllegalStateException(errorMsg)
                            }
                        }
                    }
                }
            }
        })

        project.getGradle().addListener(new TaskActionListener() {
            @Override
            void beforeActions(Task task) {

            }

            @Override
            void afterActions(Task task) {
                if (task instanceof HelloTask && task.getActions().size() > 0){
                    task.getActions().each {action ->
                        println "Hello Plugin action : "
                        action.execute(task)
                    }
                }
            }
        })

        project.getTasks().create("myHelloz", HelloTask.class)


        /*
        使用如下：
        multiDomain {
            custom{
                sourceDir = file('src/docs/cus')
            }
            second { // 默认虚招到 src/docs/second 目录
            }
        }
        */

        project.getGradle().addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {
                println 'HelloPlugin :: buildStart'
            }

            @Override
            void settingsEvaluated(Settings settings) {
                println 'HelloPlugin :: settingsEvaluated'
            }

            @Override
            void projectsLoaded(Gradle gradle) {
                println 'HelloPlugin :: projectsLoaded'
            }

            @Override
            void projectsEvaluated(Gradle gradle) {
                println 'HelloPlugin :: projectsEvaluated'
            }

            @Override
            void buildFinished(BuildResult buildResult) {
                println 'HelloPlugin :: buildFinished'
            }
        })


        project.getGradle().taskGraph.whenReady { taskGraph ->
            taskGraph.afterTask { task ->
                println "=========whenReady : taskGraph : afterTask : ${task.getName()}=========="
            }
        }

        project.getGradle().taskGraph.addTaskExecutionListener(new TaskExecutionListener() {
            @Override
            void beforeExecute(Task task) {
                println "=========taskGraph : TaskExecutionListener : beforeExcute : ${task.getName()}==========="
            }

            @Override
            void afterExecute(Task task, TaskState taskState) {
                println "=========taskGraph : TaskExecutionListener : afterExcute : ${task.getName()}==========="
            }
        })

        project.getGradle().taskGraph.addTaskExecutionGraphListener(new TaskExecutionGraphListener() {
            @Override
            void graphPopulated(TaskExecutionGraph taskExecutionGraph) {

            }
        })



    }


}