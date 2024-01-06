package day18

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    var lights = Path("input/day18.txt").readLines().map { it.toCharArray().map { c -> if (c == '#') 1 else 0 }.toIntArray() }.toTypedArray()
    lights[0][0] = 1
    lights[0][lights[0].size - 1] = 1
    lights[lights.size - 1][0] = 1
    lights[lights.size - 1][lights[0].size - 1] = 1

    for (i in 1..100) {
        val nextLights = Array(lights.size) { IntArray(lights[0].size) { 0 } }
        for (row in lights.indices) {
            for (col in lights.indices) {
                val litNeighbours = countLitNeighbours(lights, row, col)
                if (lights[row][col] == 1 && (litNeighbours < 2 || litNeighbours > 3))
                    nextLights[row][col] = 0
                else if (lights[row][col] == 0 && litNeighbours == 3)
                    nextLights[row][col] = 1
                else
                    nextLights[row][col] = lights[row][col]
            }
        }
        nextLights[0][0] = 1
        nextLights[0][lights[0].size - 1] = 1
        nextLights[lights.size - 1][0] = 1
        nextLights[lights.size - 1][lights[0].size - 1] = 1
        lights = nextLights
    }

    val total = lights.fold(0){ acc, ints -> acc + ints.sum() }
    println(total)
}

val deltas = listOf(Delta(-1, -1), Delta(-1, 0), Delta(-1, 1),
    Delta(0, -1), Delta(0, 1),
    Delta(1, -1), Delta(1, 0), Delta(1, 1))

fun countLitNeighbours(lights: Array<IntArray>, row: Int, col: Int): Int {
    var count = 0
    for (delta in deltas) {
        if (row + delta.row < 0 || row + delta.row >= lights.size ||
            col + delta.col < 0 || col + delta.col >= lights[0].size)
            continue

        count += lights[row + delta.row][col + delta.col]
    }
    return count
}

data class Delta(val row: Int, val col: Int)
