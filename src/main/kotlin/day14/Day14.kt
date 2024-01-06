package day14

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day14.txt").readLines().map {
        val parts = it.split(" ")
        Reindeer(parts[0], parts[3].toInt(), parts[6].toInt(), parts[13].toInt())
    }

    val time = 2503

    val results = input.map { reindeer -> reindeer.name to reindeer.distanceCovered(time) }.toMap()

    println(results)
    println(results.values.max())

    val scores = input.map { it.name to 0 }.toMap().toMutableMap()

    for (tick in 1..time) {
        val tickDistanceMap = input.map { it.name to it.distanceCovered(tick) }.toMap()
        val maxDistance = tickDistanceMap.values.max()
        val leaders = tickDistanceMap.filterValues { it == maxDistance }
        leaders.forEach { name, _ -> scores[name] = scores[name]!! + 1 }
    }

    println(scores)
    println(scores.values.max())
}

data class Reindeer(val name: String, val velocity: Int, val duration: Int, val rest: Int) {
    fun distanceCovered(time: Int): Int {
        val timeBlock = duration + rest
        val cycle = time / timeBlock
        val remainder = time % timeBlock
        return cycle * (velocity * duration) + if (duration > remainder) remainder * velocity else duration * velocity
    }
}