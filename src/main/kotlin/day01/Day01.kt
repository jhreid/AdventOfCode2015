package day01

import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("input/day01.txt").readText()

    val result = input.foldIndexed(0){ index, acc, c -> when (c) {
        '(' -> acc + 1
        ')' -> {
            if (acc == 0) println(index + 1)
            acc - 1
        }
        else -> acc
    } }

    println(result)


}