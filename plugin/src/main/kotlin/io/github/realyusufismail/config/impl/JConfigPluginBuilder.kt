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