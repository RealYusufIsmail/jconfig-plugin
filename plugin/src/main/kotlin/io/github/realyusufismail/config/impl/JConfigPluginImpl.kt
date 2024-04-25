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
