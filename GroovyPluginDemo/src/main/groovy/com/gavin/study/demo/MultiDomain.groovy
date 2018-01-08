package com.gavin.study.demo

class MultiDomain {
    String name
    File sourceDir
    MultiDomain(String name){
        this.name = name
    }

    def sourceDir(String dir){
        this.sourceDir = new File(dir)
    }
}
