package io.github.realyusufismail

import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.config.impl.JConfigPluginBuilder
import io.github.realyusufismail.jconfig.classes.JConfigException
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
        project.plugins.withType(JConfigPluginPlugin::class.java) {
            val configFile = project.file("config.json")
            if (!configFile.exists()) {
                throw JConfigException("Config file does not exist!")
            }

            val jConfigPlugin = JConfigPluginBuilder().build(configFile)

            project.extensions.add("jconfig", jConfigPlugin)

            project.tasks.register("jconfigTask", JconfigTask::class.java) { task ->
                task.config = jConfigPlugin
            }
        }
    }
}