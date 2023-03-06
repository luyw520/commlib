package com.lu.library.kv

import com.blankj.utilcode.util.SPUtils

class SpImpl : KV {
    private var kv:SPUtils
    init {
        kv = SPUtils.getInstance()
    }
    override fun init(name: String) {
        kv =  SPUtils.getInstance(name)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
       return kv.getInt(key,defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return kv.getLong(key,defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return kv.getFloat(key,defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return kv.getBoolean(key,defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String? {
        return kv.getString(key,defaultValue)
    }

    override fun putString(key: String, defaultValue: String) {
         kv.put(key,defaultValue)
    }

    override fun putInt(key: String, defaultValue: Int) {
        kv.put(key,defaultValue)
    }

    override fun putLong(key: String, defaultValue: Long) {
        kv.put(key,defaultValue)
    }

    override fun putFloat(key: String, defaultValue: Float) {
        kv.put(key,defaultValue)
    }

    override fun putBoolean(key: String, defaultValue: Boolean) {
        kv.put(key,defaultValue)
    }

    override fun remove(key: String) {
        kv.remove(key)
    }

    override fun clear() {
        kv.clear()
    }

    override fun getAll(): MutableMap<String, *>  = kv.all
    override fun getKeys(): List<String> {
        return kv.all.keys.toList()
    }

    override fun containsKey(key: String) = kv.contains(key)
}