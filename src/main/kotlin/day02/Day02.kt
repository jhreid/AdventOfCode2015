package day02

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day02.txt").readLines().map { it.split("x").toList() }

    val paper = input.map { line ->
        val (a, b, c) = line.map { it.toInt() }
        val areas = listOf(a * b, b * c, c * a)
        areas.sorted().first() + areas.sum() * 2
    }.sum()

    println(paper)

    val ribbon = input.map { line ->
        val (a, b, c) = line.map { it.toInt() }
        listOf(2 * (a + b), 2 * (b + c), 2 * (c + a)).min() + a * b * c
    }.sum()

    println(ribbon)
}