import java.io.File

class Day2 {

    private val input = fileParser("input/day2.txt")
    fun task1() {
        var possibleGames = 0
        for (line in input) {
            val (game, setsLine) = line.split(':')
            val (_, gameNumber) = game.split(' ')
            val setsList = setsLine.split(';')
            var possible = true
            for (set in setsList) {
                var greens = 0
                var reds = 0
                var blues = 0
                val dices = set.split(',')
                for (dice in dices) {
                    val (number, color) = dice.trim().split(' ')
                    when (color) {
                        "green" -> greens = number.toInt()
                        "red" -> reds = number.toInt()
                        "blue" -> blues = number.toInt()
                    }
                }
                if (reds > 12 || greens > 13 || blues > 14) {
                    possible = false
                    break
                }
            }
            if (possible) {
                possibleGames += gameNumber.toInt()
            }
        }
        println(possibleGames)
    }

    fun task2() {
        var powers = 0
        for (line in input) {
            val (_, setsLine) = line.split(':')
            val setsList = setsLine.split(';')
            val reds = mutableListOf<Int>()
            val greens = mutableListOf<Int>()
            val blues = mutableListOf<Int>()
            for (set in setsList) {
                val dices = set.split(',')
                for (dice in dices) {
                    val (number, color) = dice.trim().split(' ')
                    when (color) {
                        "green" -> greens.add(number.toInt())
                        "red" -> reds.add(number.toInt())
                        "blue" -> blues.add(number.toInt())
                    }
                }
            }
            val power = reds.maxOrNull()!! * greens.maxOrNull()!! * blues.maxOrNull()!!
            powers += power
        }
        println(powers)
    }

    private fun fileParser(fileName: String): List<String> {
        return File(fileName).readLines()
    }
}

fun main() {
    Day2().task1()
    Day2().task2()
}