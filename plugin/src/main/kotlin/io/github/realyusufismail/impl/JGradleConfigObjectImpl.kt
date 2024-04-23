package io.github.realyusufismail.impl

import com.fasterxml.jackson.databind.JsonNode
import io.github.realyusufismail.JGradleConfig
import io.github.realyusufismail.JGradleConfigObject
import io.github.realyusufismail.jconfig.classes.JConfigObjectImpl
import io.github.realyusufismail.jconfig.classes.JsonEntry
import java.math.BigDecimal
import java.math.BigInteger

class JGradleConfigObjectImpl(private val value: JsonNode) : JGradleConfigObject {
    override val asString: String
        get() {
            if (value.isTextual) {
                return value.asText()
            } else {
                throw ClassCastException("Cannot get $value as String")
            }
        }

    override val asInt: Int
        get() {
            if (value.isInt) {
                return value.asInt()
            } else {
                throw ClassCastException("Cannot get $value as Int")
            }
        }

    override val asBigInt: BigInteger
        get() {
            if (value.isBigInteger) {
                return value.bigIntegerValue()
            } else {
                throw ClassCastException("Cannot get $value as BigInteger")
            }
        }

    override val asDouble: Double
        get() {
            if (value.isDouble) {
                return value.asDouble()
            } else {
                throw ClassCastException("Cannot get $value as Double")
            }
        }

    override val asBoolean: Boolean
        get() {
            if (value.isBoolean) {
                return value.asBoolean()
            } else {
                throw ClassCastException("Cannot get $value as Boolean")
            }
        }

    override val asByte: Byte
        get() {
            if (value.isBinary) {
                return value.binaryValue()[0]
            } else {
                throw ClassCastException("Cannot get $value as Byte")
            }
        }

    override val asShort: Short
        get() {
            if (value.isShort) {
                return value.shortValue()
            } else {
                throw ClassCastException("Cannot get $value as Short")
            }
        }

    override val asLong: Long
        get() {
            if (value.isLong) {
                return value.asLong()
            } else {
                throw ClassCastException("Cannot get $value as Long")
            }
        }

    override val asFloat: Float
        get() {
            if (value.isFloat) {
                return value.floatValue()
            } else {
                throw ClassCastException("Cannot get $value as Float")
            }
        }

    override val asChar: Char
        get() {
            if (value.isTextual && value.asText().length == 1) {
                return value.asText()[0]
            } else {
                throw ClassCastException("Cannot get $value as Char")
            }
        }

    override val asNumber: Number
        get() {
            if (value.isNumber) {
                return value.numberValue()
            } else {
                throw ClassCastException("Cannot get $value as Number")
            }
        }

    override val asDecimal: BigDecimal
        get() {
            if (value.isBigDecimal) {
                return value.decimalValue()
            } else {
                throw ClassCastException("Cannot get $value as BigDecimal")
            }
        }

    override val asJsonEntry: JsonEntry
        get() = throw UnsupportedOperationException("Cannot get $value as JsonEntry")

    override val asJsonNode: JsonNode
        get() = value

    override val parseAsString: String
        get() = value.toString()
}