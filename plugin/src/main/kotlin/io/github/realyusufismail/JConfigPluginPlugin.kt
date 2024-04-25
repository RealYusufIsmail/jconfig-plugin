package io.github.realyusufismail

import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.config.impl.JConfigPluginBuilder
import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * A Gradle plugin for configuring JGradleConfig and creating tasks.
 */
class JConfigPluginPlugin: Plugin<Project> {
    /**
     * Applies the plugin to the given project.
     *
     * @param project The project to which the plugin is applied.
     */
    override fun apply(project: Project) {
        project.extensions.create("jconfig", JConfigPlugin::class.java)

        project.tasks.register("jconfigTask", JconfigTask::class.java) { task ->
            task.config = JConfigPluginBuilder().build()
        }
    }
}