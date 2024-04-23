package io.github.realyusufismail

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class JconfigTask: DefaultTask() {
    lateinit var config: JGradleConfig
}
