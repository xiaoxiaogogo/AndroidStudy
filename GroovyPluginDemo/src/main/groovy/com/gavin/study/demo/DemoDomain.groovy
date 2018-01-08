package com.gavin.study.demo

import org.gradle.api.tasks.Input

class DemoDomain{
    @Input
    String hello = 'hello world'
    DemoDomain(String hello){
        this.hello = hello
    }
}
