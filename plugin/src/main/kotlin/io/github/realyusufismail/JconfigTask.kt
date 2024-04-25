package io.github.realyusufismail

import io.github.realyusufismail.config.JConfigPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

open class JconfigTask : DefaultTask() {
    /**
     * The JConfigPlugin to be processed by the task.
     */
    @Input
    lateinit var config: JConfigPlugin
}