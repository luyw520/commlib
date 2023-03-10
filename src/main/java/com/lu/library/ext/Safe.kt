package com.lu.library.ext



inline fun <T> T.safeApply(
    log: Boolean = true,
    unsafe: T.() -> Unit
): T {
    try {
        unsafe()
    } catch (e: Throwable) {
        if (log) {
            e.printStackTrace()
        }
    }
    return this
}

inline fun <T, R> T.safeLet(
    unsafe: (T) -> R,
    success: (R) -> Unit = {},
    failed: (Throwable) -> Unit = {}
) {
    try {
        success(unsafe(this))
    } catch (e: Throwable) {
        failed(e)
    }
}

inline fun <T, R> T.safeLet(
    log: Boolean = true,
    defVal: R,
    unsafe: (T) -> R
): R {
    return try {
        unsafe(this)
    } catch (e: Throwable) {
        if (log) {
            e.printStackTrace()
        }
        defVal
    }
}

inline fun <T, R> T.safeLetOrNull(
    log: Boolean = true,
    unsafe: (T) -> R
): R? {
    return try {
        unsafe(this)
    } catch (e: Throwable) {
        if (log) {
            e.printStackTrace()
        }
        null
    }
}