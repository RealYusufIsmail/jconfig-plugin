package io.github.realyusufismail.config.impl

import com.fasterxml.jackson.databind.JsonNode
import io.github.realyusufismail.config.JConfigPlugin
import io.github.realyusufismail.config.JConfigPluginObject
import io.github.realyusufismail.jconfig.classes.JsonEntry

class JConfigPluginImpl(entries: List<JsonEntry>) : JConfigPlugin {
    private var mapEntries: Map<String, Any> = HashMap()
    private var jsonEntries: Set<JsonEntry> = HashSet()


    init {
        this.mapEntries = JsonEntry.toMap(entries)
        jsonEntries = this.mapEntries.map { JsonEntry(it.key, it.value) }.toSet()
    }

    override val entries: Set<JsonEntry>
        get() = jsonEntries

    val values: Set<Map.Entry<String, JConfigPluginObject>>
        get() {
            val map = HashMap<String, JConfigPluginObject>()
            for (entry in jsonEntries) {
                map[entry.key] = getAsJConfigObject(entry.key)!!
            }
            return map.entries
        }

    override fun get(key: String): JsonNode? {
        val value =
            mapEntries[key] ?: throw NoSuchElementException("No value present for key: $key")

        return if (value is JsonNode) {
            value
        } else {
            null
        }
    }


    override fun set(key: String, value: Any) {
        mapEntries = mapEntries.toMutableMap().apply { put(key, value) }
        jsonEntries = mapEntries.map { JsonEntry(it.key, it.value) }.toSet()
    }

    override fun set(key: String, value: JConfigPluginObject) {
        set(key, value.asJsonEntry)
    }

    override fun contains(key: String): Boolean {
        return mapEntries.containsKey(key)
    }
}