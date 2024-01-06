package day10

fun main() {
    val input = "3113322113"

    var result = input
    for (i in 1..50) {
        result = lookAndSay(result)
    }
    println(result.length)
}

fun lookAndSay(input: String): String {
    val word = input.toCharArray()
    var count = 1
    var current = word.first()
    val result = mutableListOf<Char>()
    var index = 1
    while (index < word.size) {
        if (word[index] != current) {
            result.add(count.digitToChar())
            result.add(current)
            current = word[index]
            count = 1
            index++
        } else {
            index++
            count++
        }
    }
    result.add(count.digitToChar())
    result.add(current)
    return result.joinToString("")
}