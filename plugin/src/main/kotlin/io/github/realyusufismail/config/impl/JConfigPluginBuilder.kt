package io.github.realyusufismail.config.impl

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.jconfig.classes.JConfigException
import io.github.realyusufismail.jconfig.classes.JsonEntry
import java.io.File

class JConfigPluginBuilder {
    var fileName: String = "config.json" // default file
    var configPath: String = "./" // default config path
    var fullConfigPath: String = "$configPath$fileName" // default full config path
    private var extension = ".json"

    @Throws(JConfigException::class)
    fun build(): JConfigPlugin {
        val mapper = ObjectMapper()

        if (!File(configPath).exists()) {
            throw JConfigException("Directory path does not exist!")
        }

        if (!File(configPath).isDirectory) {
            throw JConfigException("Directory path is not a directory!")
        }


        if (!fileName.endsWith(extension)) {
            throw JConfigException("JConfig only supports JSON files!")
        }

        val json = File(fullConfigPath)

        if (!json.exists()) {
            throw JConfigException("Config file does not exist!")
        }

        return try {
            val root = mapper.readTree(json)
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