package com.gavin.study

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

class HelloTask extends DefaultTask {

    String customProperty = 'default value for hello task'

    @TaskAction
    def helloAction(){
        println customProperty
    }

    @TaskAction
    def hello2(){
        println 'I am second hello ....'
    }
}



