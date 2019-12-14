package ro.codespace.aoc.day14

import ro.codespace.aoc.day


fun main() {
    val recipes = day("14").readLines().map { readRecipe(it) }

    val recipeBook = recipes.associateBy { it.produce.name }

    val oreAmount = 1000000000000

    var low = 0L
    var high = 1000000000L


    while (high > low) {
        val mid = (low + high)/2
        val oreNeededForMid = oreNeededForFuel(recipeBook, mid)
        if (oreNeededForMid < oreAmount) {
            low = mid+1
        } else {
            high = mid
        }
    }
    println(low-1)
}