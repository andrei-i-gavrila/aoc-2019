package ro.codespace.aoc.day8

import ro.codespace.aoc.day

fun decode(layers: List<String>): String {
    return layers[0].indices.map { pixelI ->
        layers.map { it[pixelI] }.first { it != '2' }
    }.joinToString("") { if (it == '0') " " else it.toString() }
}

fun decodeImage(width: Int, height: Int, imageText: String): String {
    return decode(imageText.chunked(width * height)).chunked(width).joinToString("\n")
}

fun main() {
    val imageText = day(8).readText()
    println(decodeImage(25, 6, imageText))
}