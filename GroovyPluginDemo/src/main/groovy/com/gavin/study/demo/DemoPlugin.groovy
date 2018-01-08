package com.gavin.study.demo

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

class DemoPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.extensions.create("demo", DemoDomain, "demo domain override")

        project.tasks.create("showHello", DemoTask.class)

        def multiDomains = project.container(MultiDomain.class)
        multiDomains.all {
            sourceDir = project.file("src/docs/${name}") // 定义默认值
        }

        project.extensions.multiDomain = multiDomains;

    }
}
