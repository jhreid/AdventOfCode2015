package day16

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val tickerTape = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    val regex = """Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)""".toRegex()
    val aunts = Path("input/day16.txt").readLines().map {
        val matchResult = regex.find(it)
        val groups = matchResult!!.groupValues
        Aunt(groups[1].toInt(), mapOf(groups[2] to groups[3].toInt(), groups[4] to groups[5].toInt(), groups[6] to groups[7].toInt()))
    }

    val result = aunts.filter {
        var valid = true
        it.gifts.forEach { gift, value ->
            when (gift) {
                "cats", "trees" -> if (tickerTape[gift]!! > value) valid = false
                "pomeranians", "goldfish" -> if (tickerTape[gift]!! < value) valid = false
                else -> if (tickerTape[gift] != value) valid = false
            }
        }
        valid
    }

    println(result)
}

data class Aunt(val number: Int, val gifts: Map<String, Int>)