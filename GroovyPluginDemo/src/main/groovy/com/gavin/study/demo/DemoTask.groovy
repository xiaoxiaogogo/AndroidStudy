package com.gavin.study.demo

import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

class DemoTask extends DefaultTask{

    @TaskAction
    void sayHello(IncrementalTaskInputs incrementalTaskInputs){



        println project.extensions.demo.hello
        def domains = project.extensions.multiDomain as NamedDomainObjectContainer<MultiDomain>
        domains.all {
            println "name : ${name}; sourceDir : ${sourceDir.path}"
        }

    }
}
