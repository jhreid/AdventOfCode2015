package day03

import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("input/day03.txt").readText()

    val houses = input.toCharArray().fold(Pair(Point(0, 0), mutableSetOf(Point(0, 0)))) {
            acc, c ->
        val current = acc.first
        val visited = acc.second
        val next = nextPoint(c, current)
        visited.add(next)
        Pair(next, visited)
    }.second.size

    println(houses)

    val houses2 = input.toCharArray().foldIndexed(Triple(Point(0, 0), Point(0, 0), mutableSetOf(Point(0, 0)))) {
            index, acc, c ->
        val santa = acc.first
        val robo = acc.second
        val visited = acc.third
        if (index % 2 == 0) {
            val next = nextPoint(c, santa)
            visited.add(next)
            Triple(next, robo, visited)
        } else {
            val next = nextPoint(c, robo)
            visited.add(next)
            Triple(santa, next, visited)
        }
    }.third.size

    println(houses2)
}

private fun nextPoint(c: Char, current: Point): Point {
    val next = when (c) {
        'v' -> Point(current.x, current.y - 1)
        '^' -> Point(current.x, current.y + 1)
        '<' -> Point(current.x - 1, current.y)
        '>' -> Point(current.x + 1, current.y)
        else -> current
    }
    return next
}

data class Point(val x: Int, val y: Int)