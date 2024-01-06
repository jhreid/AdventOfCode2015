package day11

val straights = listOf("abc", "bcd", "cde", "def", "efg", "fgh", "ghi", "hij", "ijk", "jkl", "klm", "lmn", "mno",
    "nop", "opq", "pqr", "qrs", "rst", "stu", "tuv", "uvw", "vwx", "wxy", "xyz")
fun main() {
    var input = "hxbxwxba"

    while (!goodPassword(input)) {
        input = incrementPassword(input)
    }
    println(input)

    input = incrementPassword(input)
    while (!goodPassword(input)) {
        input = incrementPassword(input)
    }
    println(input)
}

private fun incrementPassword(password: String): String {
    var carry = true
    val chars = password.reversed().toCharArray()
    var index = 0
    while (carry) {
        chars[index] = chars[index].inc()
        if (chars[index] > 'z') {
            chars[index] = 'a'
            index++
        } else {
            carry = false
        }
    }
    return chars.reversed().joinToString("")
}

private fun goodPassword(password: String): Boolean {
    return containsStraight(password) && noIOL(password) && twoPairs(password)
}

private fun containsStraight(password: String): Boolean {
    straights.forEach { straight ->
        if (password.contains(straight)) return true
    }
    return false
}

private fun noIOL(password: String): Boolean {
    return !(password.contains('i') || password.contains('o') || password.contains('l'))
}

private fun twoPairs(password: String): Boolean {
    var count = 0
    var found = ' '
    for (i in 0..<password.length - 1) {
        if (found != password[i] && password[i] == password[i + 1]) {
            found = password[i]
            count++
            if (count == 2) return true
        }
    }
    return false
}