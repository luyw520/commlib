package com.lu.library.kv

class KvImpl(spName:String, private val delete: KV = MMKVImpl()) : KV by delete {
    init {
        delete.init(spName)
    }
    companion object{
        private val kvs = mutableMapOf<String, KV>()
        fun getKV(spName:String): KV {
            return kvs[spName]?: kotlin.run {
                val kv = KvImpl(spName)
                kvs[spName] = kv
                kv
            }
        }
    }
}