package day05

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day05.txt").readLines()

    println(input.filter { isNice1(it) }.size)
    println(input.filter { isNice2(it) }.size)
}

private fun isNice2(s: String): Boolean {
    return repeatSkipOne(s) && pairNoOverlap(s)
}

private fun pairNoOverlap(s: String): Boolean {
    for (i in 0..<s.length - 3) {
        if (s.substring(i + 2).contains(s.substring(i, i + 2))) return true
    }
    return false
}

private fun repeatSkipOne(s: String): Boolean {
    for (i in 0..<s.length - 2) {
        if (s[i] == s[i + 2]) return true
    }
    return false
}

private fun isNice1(s: String): Boolean {
    return hasNoBadPairs(s) && threeVowels(s) && hasDouble(s)
}

private fun threeVowels(s: String): Boolean {
    val vowels = arrayOf('a', 'e', 'i', 'o', 'u')
    return s.toCharArray().filter { vowels.contains(it) }.size >= 3
}

private fun hasDouble(s: String): Boolean {
    for (i in 0..<s.length - 1) {
        if (s[i] == s[i + 1]) return true
    }
    return false
}

private fun hasNoBadPairs(s: String): Boolean {
    val badPairs = listOf("ab", "cd", "pq", "xy")
    badPairs.forEach {
        if (s.contains(it)) return false
    }
    return true
}