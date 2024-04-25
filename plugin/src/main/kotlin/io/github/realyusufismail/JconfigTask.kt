package io.github.realyusufismail

import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.config.impl.JConfigPluginBuilder
import org.gradle.api.DefaultTask

open class JconfigTask : DefaultTask() {
    /**
     * The JGradleConfig to be processed by the task.
     */
    var config: JConfigPlugin = JConfigPluginBuilder().build()
}