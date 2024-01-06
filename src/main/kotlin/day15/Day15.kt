package day15

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val ingredients = Path("input/day15.txt").readLines().map {
        val parts = it.split(" ")
        Ingredient(
            parts[0].removeSuffix(":"),
            parts[2].removeSuffix(",").toInt(),
            parts[4].removeSuffix(",").toInt(),
            parts[6].removeSuffix(",").toInt(),
            parts[8].removeSuffix(",").toInt(),
            parts[10].toInt(),
            )
    }

    println(ingredients)
    val scores = mutableMapOf<Quad<Int, Int, Int, Int>, Int>()

    for (i in 0..100) {
        for (j in 0..100 - i) {
            for (k in 0..100 - i - j) {
                for (l in 0..100 - i - j - k) {
                    val capacity = ingredients[0].capacity * i + ingredients[1].capacity * j + ingredients[2].capacity * k + ingredients[3].capacity * l
                    val durability = ingredients[0].durability * i + ingredients[1].durability * j + ingredients[2].durability * k + ingredients[3].durability * l
                    val flavor = ingredients[0].flavor * i + ingredients[1].flavor * j + ingredients[2].flavor * k + ingredients[3].flavor * l
                    val texture = ingredients[0].texture * i + ingredients[1].texture * j + ingredients[2].texture * k + ingredients[3].texture * l
                    val calories = ingredients[0].calories * i + ingredients[1].calories * j + ingredients[2].calories * k + ingredients[3].calories * l

                    if (capacity > 0 && durability > 0 && flavor > 0 && texture > 0 && calories == 500) {
                        val score = capacity * durability * flavor * texture
                        scores.put(Quad(i, j, k, l), if (score > 0) score else 0)
                    }
                }
            }
        }
    }

    println(scores.values.max())
}

data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)

data class Quad<T, U, V, W>(val first: T, val second: U, val third: V, val fourth: W)