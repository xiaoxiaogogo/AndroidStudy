package com.gavin.study

import org.gradle.api.tasks.Input

class HelloExtension{
    @Input
    def String sayHi = 'say hi from default val'

    HelloExtension(String sayHi){
        this.sayHi = sayHi
    }
}
