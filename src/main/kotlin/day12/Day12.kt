package day12

import org.json.JSONArray
import org.json.JSONObject
import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("input/day12.json").readText()

    val numbers = input.split("""[a-zA-Z:,"{}\[\]]+""".toRegex())

    println(numbers.filter { it.isNotBlank() }.sumOf { it.toInt() })

    val json = JSONArray(input)

    val total = getSum(json)
    println(total)

}

fun getSum(json: Any): Int {
    var total = 0
    when (json) {
        is JSONArray -> {
            for (i in 0 until json.length()) {
                total += getSum(json[i])
            }
        }

        is JSONObject -> {
            for (k in json.keys()) {
                if (json[k.toString()] == "red") return 0
            }
            for (k in json.keys()) {
                total += getSum(json[k.toString()])
            }
        }

        is Int -> {
            return json.toInt()
        }
    }
    return total
}

