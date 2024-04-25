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
package io.github.realyusufismail.config.impl

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.jconfig.classes.JConfigException
import io.github.realyusufismail.jconfig.classes.JsonEntry
import java.io.File

class JConfigPluginBuilder {
    @Throws(JConfigException::class)
    fun build(configFile: File): JConfigPlugin {
        val mapper = ObjectMapper()

        // Check if the config file exists
        if (!configFile.exists()) {
            throw JConfigException("Config file does not exist!")
        }

        // Check if the config file is a directory
        if (configFile.isDirectory) {
            throw JConfigException("Config file is a directory!")
        }

        // Check if the config file is a JSON file
        if (!configFile.name.endsWith(".json")) {
            throw JConfigException("JConfig only supports JSON files!")
        }

        return try {
            val root = mapper.readTree(configFile)
            val entries: MutableList<JsonEntry> = ArrayList()
            val it = root.fields()
            while (it.hasNext()) {
                val (key, value) = it.next()
                entries.add(JsonEntry(key, value))
            }

            JConfigPluginImpl(entries)
        } catch (e: Exception) {
            throw JConfigException("Error reading JSON file: ${e.message}")
        }
    }
}
