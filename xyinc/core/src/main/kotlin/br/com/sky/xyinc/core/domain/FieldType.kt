package br.com.sky.xyinc.core.domain

enum class FieldType {
    INTEGER {
        override fun accepts(value: Any): Boolean {
            return value is Int || value is Long
        }
    },
    DECIMAL {
        override fun accepts(value: Any): Boolean {
            return value is Int || value is Double || value is Float
        }
    },
    DOUBLE {
        override fun accepts(value: Any): Boolean {
            return value is Int || value is Double || value is Float
        }
    },
    TEXT {
        override fun accepts(value: Any): Boolean {
            return value is String
        }
    };

    abstract fun accepts(value: Any): Boolean
}