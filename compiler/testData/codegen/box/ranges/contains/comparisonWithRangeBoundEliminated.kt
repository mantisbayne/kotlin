fun test1() =
        5 in Math.abs(-1) .. 10

fun test2() =
        5 in 1 .. Math.abs(-10)

fun box(): String {
    if (!test1()) return "Fail 1"
    if (!test2()) return "Fail 2"

    return "OK"
}