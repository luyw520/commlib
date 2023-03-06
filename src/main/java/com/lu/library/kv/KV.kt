package com.lu.library.kv

interface KV {
    fun init(name:String)
    fun getInt(key: String, defaultValue: Int): Int

    fun getLong(key: String, defaultValue: Long): Long

    fun getFloat(key: String, defaultValue: Float): Float

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun getString(key: String, defaultValue: String): String?

    fun putString(key: String, defaultValue: String)

    fun putInt(key: String, defaultValue: Int)

    fun putLong(key: String, defaultValue: Long)

    fun putFloat(key: String, defaultValue: Float)

    fun putBoolean(key: String, defaultValue: Boolean)

    fun remove(key: String)

    fun clear()
    fun getAll():MutableMap<String,*>

    fun getKeys():List<String>

    fun containsKey(key:String):Boolean
}