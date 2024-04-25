/*
 * Copyright 2024 Yusuf Arfan Ismail (RealYusufIsmail)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail

import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.config.impl.JConfigPluginBuilder
import io.github.realyusufismail.jconfig.classes.JConfigException
import org.gradle.api.Plugin
import org.gradle.api.Project

/** A Gradle plugin for configuring JGradleConfig and creating tasks. */
class JConfigPluginPlugin : Plugin<Project> {
    /**
     * Applies the plugin to the given project.
     *
     * @param project The project to which the plugin is applied.
     */
    override fun apply(project: Project) {
        val extension =
            project.extensions.create("jconfigExtension", JConfigPluginExtension::class.java)

        project.plugins.withType(JConfigPluginPlugin::class.java) {
            val configFile = project.file(extension.configFileName)

            if (!configFile.exists()) {
                throw JConfigException("Config file does not exist!")
            }

            val jConfigPlugin = JConfigPluginBuilder().build(configFile)

            project.extensions.add(JConfigPlugin::class.java, "jconfig", jConfigPlugin)

            project.tasks.register("jconfigTask", JconfigTask::class.java) { task ->
                task.config = jConfigPlugin
            }
        }
    }
}
