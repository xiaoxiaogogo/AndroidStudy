package com.gavin.study

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

class IncremetalTask extends DefaultTask {

    // 输入文件夹
    @InputDirectory
    def File inputDir

    // 输出文件夹
    @OutputDirectory
    def File outpurDir

    // 输入属性
    @Input
    def inputProperty

    @TaskAction
    def executeAction(IncrementalTaskInputs inputs){ // 增量的参数
        // 因为有一些情况无法准确的确定增量， 比如： gradle的版本发生了改变
        def incremental = inputs.incremental // 检查是否能对输入的文件确定增量

        inputs.outOfDate { changeFile -> // 发生改变的文件：包括内容更改 或 新增的文件
            println changeFile.file.text
            // 修改输出文件的内容
            def isAdd = changeFile.added
            def isModified = changeFile.modified
            def outFile = new File(outputDir, changeFile.file.name)
            outFile.text = changeFile.file.text // 更新内容
        }
        // 被删除的输入文件
        inputs.removed { removedFile ->
            println '被删除的增量文件: ' + removedFile.file.absolutePath
            def outFile = new File(outpurDir, removedFile.file.name)
            outFile.delete()
        }
    }

}