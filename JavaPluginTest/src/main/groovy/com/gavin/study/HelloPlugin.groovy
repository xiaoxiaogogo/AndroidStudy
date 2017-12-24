package com.gavin.study

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.internal.artifacts.result.DefaultResolvedDependencyResult
import org.gradle.api.internal.artifacts.result.DefaultUnresolvedDependencyResult
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

        project.beforeEvaluate {
            project.getTasks().create('helloSecond', HelloSecondTask)
        }
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