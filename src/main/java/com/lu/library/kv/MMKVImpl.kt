package com.lu.library.kv

import com.tencent.mmkv.MMKV

class MMKVImpl : KV {
    private var kv:MMKV
    init {
        kv = MMKV.defaultMMKV()
    }
    override fun init(name: String) {
        kv =  MMKV.mmkvWithID(name)
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
         kv.putString(key,defaultValue)
    }

    override fun putInt(key: String, defaultValue: Int) {
        kv.putInt(key,defaultValue)
    }

    override fun putLong(key: String, defaultValue: Long) {
        kv.putLong(key,defaultValue)
    }

    override fun putFloat(key: String, defaultValue: Float) {
        kv.putFloat(key,defaultValue)
    }

    override fun putBoolean(key: String, defaultValue: Boolean) {
        kv.putBoolean(key,defaultValue)
    }

    override fun remove(key: String) {
        kv.remove(key)
    }

    override fun clear() {
        kv.clearAll()
    }

    override fun getAll(): MutableMap<String, *> {
       return kv.all
    }

    override fun getKeys(): List<String> {
        val lis = mutableListOf<String>()
      kv.allKeys()?.forEach {
          lis+=it
      }
        return lis
    }

    override fun containsKey(key: String) = kv.containsKey(key)
}