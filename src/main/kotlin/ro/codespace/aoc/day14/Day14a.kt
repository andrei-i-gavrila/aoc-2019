package ro.codespace.aoc.day14

import ro.codespace.aoc.day
import java.util.*
import kotlin.math.ceil
import kotlin.math.min

data class Ingredient(val name: String, val quantity: Long)
data class Recipe(val produce: Ingredient, val requirements: List<Ingredient>)

fun main() {
    val recipes = day("14").readLines().map { readRecipe(it) }

    val recipeBook = recipes.associateBy { it.produce.name }

    println(oreNeededForFuel(recipeBook, 1))
}

fun oreNeededForFuel(recipeBook: Map<String, Recipe>, fuelCount: Long): Long {
    val q = LinkedList<Pair<String, Long>>()

    val stock = mutableMapOf<String, Long>()

    var oreCounter = 0L
    q.addFirst("FUEL" to fuelCount)

    while (q.isNotEmpty()) {
        val (ingredient, initialQuantityNeeded) = q.pollLast()

        val quantityNeeded = if (ingredient in stock) {
            val takenFromStock = min(initialQuantityNeeded, stock[ingredient]!!)
            stock[ingredient] = stock[ingredient]!! - takenFromStock
            initialQuantityNeeded - takenFromStock
        } else {
            initialQuantityNeeded
        }


        if (ingredient == "ORE") {
            oreCounter += quantityNeeded
            continue
        }

        val targetRecipe = recipeBook.getValue(ingredient)

        val producedQuantity = targetRecipe.produce.quantity

        val timesNeeded = ceil(quantityNeeded.toDouble() / producedQuantity).toLong()
        targetRecipe.requirements.forEach {
            q.addFirst(it.name to timesNeeded * it.quantity)
        }
        val overProduced = timesNeeded * producedQuantity - quantityNeeded
        if (overProduced > 0) {
            stock.merge(ingredient, overProduced) { a, b ->
                a + b
            }
        }
    }
    return oreCounter
}

fun readRecipe(it: String): Recipe {
    val ingredients = Regex("""(\d+)\s([A-Z]+)""").findAll(it).map {
        Ingredient(it.groupValues[2], it.groupValues[1].toLong())
    }.toList()

    return Recipe(ingredients.last(), ingredients.dropLast(1))
}