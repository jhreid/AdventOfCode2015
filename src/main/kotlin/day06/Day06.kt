package day06

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day06.txt").readLines()

    val lights = Array(1000){Array<Int>(1000){0}}

    input.forEach { line ->
        when {
            line.startsWith("turn on ") -> {
                val coords = line.removePrefix("turn on ").split(" through ").map {
                    val (x, y) = it.split(",").map { it.toInt() }
                    Point(x, y)
                }
                for (x in coords[0].x..coords[1].x) {
                    for (y in coords[0].y..coords[1].y) {
                        lights[x][y] += 1
                    }
                }
            }
            line.startsWith("turn off ") -> {
                val coords = line.removePrefix("turn off ").split(" through ").map {
                    val (x, y) = it.split(",").map { it.toInt() }
                    Point(x, y)
                }
                for (x in coords[0].x..coords[1].x) {
                    for (y in coords[0].y..coords[1].y) {
                        if (lights[x][y] > 0) lights[x][y] -= 1
                    }
                }
            }
            line.startsWith("toggle ") -> {
                val coords = line.removePrefix("toggle ").split(" through ").map {
                    val (x, y) = it.split(",").map { it.toInt() }
                    Point(x, y)
                }
                for (x in coords[0].x..coords[1].x) {
                    for (y in coords[0].y..coords[1].y) {
                        lights[x][y] += 2
                    }
                }
            }
        }
    }

    lights.forEach { println(it.joinToString("")) }

    println(lights.flatten().sum())
}

data class Point(val x: Int, val y: Int)