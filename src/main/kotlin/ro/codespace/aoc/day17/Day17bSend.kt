package ro.codespace.aoc.day17

import ro.codespace.aoc.Program
import ro.codespace.aoc.Vector2
import ro.codespace.aoc.day

fun main() {
    val code = day(17).readLine().split(",").map { it.toInt() }.toMutableList()

    code[0] = 2
    val all = "R,12,L,8,R,6,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,6,R,6,R,8,R,6"
    val mainRoutine = "C,C,B,A,C,A,C,B,A,B"
    val A = "L,8,R,8,R,6,R,12"
    val B = "R,12,L,6,R,6,R,8,R,6"
    val C = "R,12,L,8,R,6"
    val video = "n"
    val input = listOf(mainRoutine, A, B, C, video).joinToString("\n", postfix = "\n").iterator()


//    canFitPrint(transform(all), transform(A), transform(B), transform(C))
//    println()
//    println()

    Program(code, {
        input.nextChar().toInt().also {
            println(it.toChar())
        }
    }, { print(it.toInt().toChar()) }).let {
        it.solve()
        println(it.lastOutput)
    }
}

fun canFitPrint(all: List<String>, A: List<String>, B: List<String>, C: List<String>): Boolean {
    if (all.isEmpty()) return true
    var result = false
    if (canPrefix(A, all)) {
        val canFitA = canFitPrint(all.subList(A.size, all.size), A, B, C)
        if (canFitA) {
            print("A,")
        }
        result = result || canFitA
    }
    if (canPrefix(B, all)) {
        val canFitB = canFitPrint(all.subList(B.size, all.size), A, B, C)
        if (canFitB) {
            print("B,")
        }
        result = result || canFitB
    }
    if (canPrefix(C, all)) {
        val canFitC = canFitPrint(all.subList(C.size, all.size), A, B, C)
        if (canFitC) {
            print("C,")
        }
        result = result || canFitC
    }
    return result
}

fun transform(it: String): List<String> {
    return it.split(",")
}