package day09

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val edges = Path("input/day09.txt").readLines().map { line ->
        val (nodes, dist) = line.split(" = ")
        val (source, dest) = nodes.split(" to ")
        Pair(source, dest) to dist.toInt()
    }.toMap()

    val vertices = edges.keys.flatMap { setOf(it.first, it.second) }.toSet()

    val dist = Array(vertices.size){ Array<Int>(vertices.size){ Int.MAX_VALUE } }

    edges.forEach { n, distance ->
        val u = vertices.indexOf(n.first)
        val v = vertices.indexOf(n.second)
        dist[u][v] = distance
        dist[v][u] = distance
    }
    vertices.forEach { node ->
        val v = vertices.indexOf(node)
        dist[v][v] = 0
    }

    val perms = permute( vertices.indices.toList() )

    val results = perms.map { perm ->
        perm to perm.windowed(2, 1).map { window ->
            val (u, v) = window
            dist[u][v]
        }.sum()
    }.toMap()

    println(results.values.min())
    println(results.values.max())
}

fun <T> permute(input: List<T>): List<List<T>> {
    if (input.size == 1) return listOf(input)
    val perms = mutableListOf<List<T>>()
    val toInsert = input[0]
    for (perm in permute(input.drop(1))) {
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, toInsert)
            perms.add(newPerm)
        }
    }
    return perms
}
