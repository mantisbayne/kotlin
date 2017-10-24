// WITH_RUNTIME
// WITH_COROUTINES
import helpers.*
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*
import kotlin.test.assertEquals

suspend fun suspendHere() = if(coroutineContext != EmptyCoroutineContext) "$coroutineContext != $EmptyCoroutineContext" else "OK"

suspend fun multipleArgs(a: Any, b: Any, c: Any) = if(coroutineContext != EmptyCoroutineContext) "$coroutineContext != $EmptyCoroutineContext" else "OK"

class Controller {
    suspend fun controllerSuspendHere() = if(coroutineContext != EmptyCoroutineContext) "$coroutineContext != $EmptyCoroutineContext" else "OK"

    suspend fun controllerMultipleArgs(a: Any, b: Any, c: Any) = if(coroutineContext != EmptyCoroutineContext) "$coroutineContext != $EmptyCoroutineContext" else "OK"

    fun builder(c: suspend Controller.() -> String): String {
        var fromSuspension: String? = null

        c.startCoroutine(this, object: Continuation<String> {
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
}

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
    var res = builder { suspendHere() }
    if (res != "OK") { println(res); return "fail 1"}
    res = builder { multipleArgs(1,1,1) }
    if (res != "OK") { println(res); return "fail 2"}
    val c = Controller()
    res = c.builder { controllerSuspendHere() }
    if (res != "OK") { println(res); return "fail 3"}
    res = c.builder { controllerMultipleArgs(1,1,1) }
    if (res != "OK") { println(res); return "fail 4"}
    return "OK"
}
