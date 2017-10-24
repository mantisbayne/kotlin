// !DIAGNOSTICS: -UNUSED_PARAMETER

import kotlin.coroutines.experimental.intrinsics.coroutineContext

fun ordinal() {
    <!ILLEGAL_SUSPEND_VAL_ACCESS!>coroutineContext<!>
}

suspend fun named() {
    coroutineContext
}

class A {
    val coroutineContext = kotlin.coroutines.experimental.intrinsics.<!ILLEGAL_SUSPEND_VAL_ACCESS!>coroutineContext<!>
}

class Controller {
    fun ordinal() {
        <!ILLEGAL_SUSPEND_VAL_ACCESS!>coroutineContext<!>
    }

    suspend fun named() {
        coroutineContext
    }

    suspend fun severalArgs(s: String, a: Any) {
        coroutineContext
    }
}
