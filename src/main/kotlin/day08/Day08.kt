package day08

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day08.txt").readLines()

    val parsed = input.map { line ->
        line.removePrefix("\"")
            .removeSuffix("\"")
            .replace("""\\""", """\""")
            .replace("\\\"", "\"")
            .replace("""\\x[a-f0-9]{2}""".toRegex(), " ")
    }
    val rawSize = input.map { it.length }.sum()
    val actualSize = parsed.map { it.length }.sum()
    //parsed.forEachIndexed { index, s -> println("${input[index]}, ${input[index].length} - $s ${s.length}") }

    println(rawSize - actualSize)

    val encodedSize = input.map { encodedLength(it) }.sum()
    println(encodedSize - rawSize)
}

private fun encodedLength(s: String): Int {
    return s.removePrefix("\"")
        .removeSuffix("\"").replace("\\\"", "    ")
        .replace("""\\x[a-f0-9]{2}""".toRegex(), "     ")
        .replace("\\", "  ")
        .length + 6
}