// WITH_RUNTIME
// WITH_COROUTINES
import helpers.*
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*
import kotlin.test.assertEquals

suspend fun suspendHere() = if(coroutineContext != EmptyCoroutineContext) "$coroutineContext != $EmptyCoroutineContext" else "OK"

fun builder(c: suspend () -> String): String {
    var fromSuspension: String? = null

    c.startCoroutine(object: Continuation<String> {
        override val context: CoroutineContext
            get() =  EmptyCoroutineContext

        override fun resumeWithException(exception: Throwable) {
            fromSuspension = "Exception: " + exception.message!!
        }

        override fun resume(value: String) {
            fromSuspension = value
        }
    })

    return fromSuspension as String
}

fun box(): String {
    val res = builder { suspendHere() }
    if (res != "OK") { println(res); return "fail 1"}
    return "OK"
}
