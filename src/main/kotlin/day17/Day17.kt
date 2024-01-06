package day17

import kotlin.io.path.Path
import kotlin.io.path.readLines


fun main() {
    val input = Path("input/day17.txt").readLines().map { it.toInt() }

    val subs = subsets(input).filter { it!!.sum() == 150 }
    val shortest = subs.map { it!!.size }.min()
    println(subs.count{ it!!.size == shortest })
}

fun calcSubset(input: List<Int>, res: MutableList<List<Int>?>, subset: MutableList<Int>, index: Int) {
    res.add(ArrayList(subset))

    for (i in index until input.size) {
        subset.add(input[i])

        calcSubset(input, res, subset, i + 1)

        subset.removeAt(subset.size - 1)
    }
}

fun subsets(input: List<Int>): List<List<Int>?> {
    val subset: MutableList<Int> = ArrayList()
    val res: MutableList<List<Int>?> = ArrayList()

    val index = 0
    calcSubset(input, res, subset, index)

    return res
}
