package io.github.realyusufismail

import com.fasterxml.jackson.databind.JsonNode
import io.github.realyusufismail.impl.JGradleConfigObjectImpl
import io.github.realyusufismail.jconfig.classes.JsonEntry

interface JGradleConfig {
    /**
     * Gets all the entries in the config file.
     *
     * @return A list of all the entries in the config file.
     */
    val entries: Set<JsonEntry>

    /**
     * Gets the value of the key from the config file.
     *
     * @param key The key of the value.
     */
    operator fun get(key: String): JsonNode?

    /**
     * Gets the value of the key from the config file.
     *
     * @param key The key of the value.
     * @param defaultValue The default value to return if the key does not exist.
     * @return The value of the key.
     */
    operator fun get(key: String, defaultValue: Any): Any {
        return get(key) ?: defaultValue
    }

    /**
     * Gets the value of the key from the config file.
     *
     * @param key The key of the value.
     * @return The value of the key.
     */
    fun getAsJConfigObject(key: String): JGradleConfigObject? {
        return get(key)?.let { JGradleConfigObjectImpl(it) }
    }

    /**
     * Gets the value of the key from the config file.
     *
     * @param key The key of the value.
     * @param defaultValue The default value to return if the key does not exist.
     * @return The value of the key.
     */
    fun getAsJConfigObject(key: String, defaultValue: Any): Any {
        return getAsJConfigObject(key) ?: defaultValue
    }

    /**
     * Used to set a value in the config file.
     *
     * @param key The key of the value.
     * @param value The value to set.
     * @return The value of the key.
     */
    operator fun set(key: String, value: Any)

    /**
     * Used to set a value in the config file.
     *
     * @param key The key of the value.
     * @param value The value to set.
     * @return The value of the key.
     */
    operator fun set(key: String, value: JGradleConfigObject)

    /**
     * Used to check if the value is present
     *
     * @param key The key of the value.
     * @return True if the value is present, false otherwise.
     */
    operator fun contains(key: String): Boolean
}