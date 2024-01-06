package day19

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day19.txt").readLines()
    val molecule = input.last()

    var subs = input.dropLast(2).map { it.split(" => ").toList() }

    val molecules = mutableSetOf<String>()

    subs.forEach { (source, substitution) ->
        val indexes = molecule.indexesOf(source)
        indexes.forEach {start ->
            molecules.add(molecule.replaceRange(start, start + source.length, substitution))
        }
    }

    var target = molecule
    var part2 = 0
    while (target != "e") {
        var tmp = target
        for ((a, b) in subs) {
            if (!target.contains(b))
                continue

            target = target.replaceFirst(b, a)
            part2 += 1
        }

        if (tmp == target) {
            target = molecule
            part2 = 0
            subs = subs.shuffled()
        }
    }

    println(molecules.size)
    println(part2)
}

public fun String?.indexesOf(substr: String): List<Int> {
    return this?.let {
        val regex = Regex(substr)
        regex.findAll(this).map { it.range.start }.toList()
    } ?: emptyList()
}