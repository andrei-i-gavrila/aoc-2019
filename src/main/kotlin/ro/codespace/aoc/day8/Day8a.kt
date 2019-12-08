package ro.codespace.aoc.day8

import ro.codespace.aoc.day


fun main() {
    val width = 25
    val height = 6

    val imageText = day(8).readText()

    val layers = imageText.chunked(width * height)


    println(layers.minBy { it.count { it == '0' } }?.let {
        println(it)
        it.count { it == '2' } * it.count { it == '1' }
    })

}