package ro.codespace.aoc.day13

import processing.core.PApplet
import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day
import ro.codespace.aoc.day7.ProgramRunner
import java.util.concurrent.LinkedBlockingQueue


class SpaceArcade : PApplet() {

    val gameState = mutableMapOf<Vector2, Int>()
    var outputCounter = 0
    var score = 0
    var outputX = 0
    var outputY = 0
    var paddlePosition = 0

    override fun settings() {
        size(800, 800)
    }

    override fun setup() {
        val code = day(13).readLine().split(",").map { it.toInt() }.toMutableList()
        code[0] = 2
        val runner = ProgramRunner(Program(code.toList(), this::programReaderProvider, this::programOutputListener))
        Thread(runner).start()

        frameRate(10f)
    }

    fun programReaderProvider(): Int {
//        Thread.sleep(100)
        val paddle = gameState.entries.first { it.value == 3 }.key
        val ball = gameState.entries.first { it.value == 4 }.key
        return when {
            ball.x == paddle.x -> 0
            ball.x > paddle.x -> 1
            else -> -1
        }
    }

    fun programOutputListener(it: String) {
        when (outputCounter % 3) {
            0 -> outputX = it.toInt()
            1 -> outputY = it.toInt()
            2 -> {
                if (outputX == -1 && outputY == 0) {
                    score = it.toInt()
                    kotlin.io.println("score ${it.toInt()}")

                } else {
                    gameState[Vector2(outputX, outputY)] = it.toInt()
                    kotlin.io.println("output $outputX, $outputY, ${it.toInt()}")

                }
            }
        }
        outputCounter++
    }

    override fun draw() {
        loadPixels()
        for (y in 0 until height) {
            for (x in 0 until width) {
                pixels[y * width + x] = when (gameState[Vector2(x, y) / 15]) {
                    0 -> color(0)
                    1 -> color(100)
                    2 -> color(0, 0, 255)
                    3 -> color(0, 255, 0)
                    4 -> color(255, 0, 0)
                    else -> color(255)
                }
            }
        }
        updatePixels()
    }
}


fun main() {
    PApplet.main(SpaceArcade::class.java)
}