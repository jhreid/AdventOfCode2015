package day04

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val key = "iwrupvqb"

    val md = MessageDigest.getInstance("MD5")
    var hash = ""
    var count = 0

    while (!hash.startsWith("000000")) {
        count ++
        hash = BigInteger(1, md.digest((key + count.toString()).toByteArray())).toString(16).padStart(32, '0')
    }

    println(count)
}