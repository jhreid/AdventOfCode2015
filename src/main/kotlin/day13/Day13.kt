package day13

import day09.permute
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day13.txt").readLines().map {
        val parts = it.split(" ")
        val name = parts[0]
        val happiness = when (parts[2]) {
            "lose" -> -parts[3].toInt()
            else -> parts[3].toInt()
        }
        val nextTo = parts[10].removeSuffix(".")
        Guest(name, happiness, nextTo)
    }.toMutableList()

    val names = input.map { it.name }.toMutableSet()
    names.forEach { name ->
        input.add(Guest("me", 0, name))
        input.add(Guest(name, 0, "me"))
    }
    names.add("me")

    val perms = permute(names.toList())

    val happiness = perms.map { arrangement ->
        var total = 0
        for (i in arrangement.indices) {
            val next = (i + 1) % arrangement.size
            val guest1 = input.find { g -> g.name == arrangement[i] && g.nextTo == arrangement[next] }
            val guest2 = input.find { g -> g.name == arrangement[next] && g.nextTo == arrangement[i] }
            total += (guest1!!.happiness + guest2!!.happiness)
        }
        arrangement to total
    }.toMap()

    println(happiness.values.max())
}

data class Guest(val name: String, val happiness: Int, val nextTo: String)