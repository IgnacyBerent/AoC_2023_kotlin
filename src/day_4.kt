import java.io.File

class Day4 {

    private val input = fileParser("input/day4.txt")
    fun task1() {
        var totalPoints = 0
        for (line in input) {
            val (_, cards) = line.trim().split(":")
            val (winningCards, myCards) = cards.trim().split("|")
            val winningCardsList = winningCards.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val myCardsList = myCards.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            var points = 0
            for (card in myCardsList) {
                if (card in winningCardsList) {
                    points = if (points == 0) 1 else points * 2
                }
            }
            totalPoints += points
        }
        println(totalPoints)
    }

    fun task2() {
        fun calculateMatches(scratches: Pair<List<Int>, List<Int>>): Int {
            var matches = 0
            for (scratch in scratches.first) {
                if (scratch in scratches.second) {
                    matches++
                }
            }
            return matches
        }

        val listOfCards = mutableListOf<Pair<Int, Int>>()
        for (line in input) {
            val (cardCardNumber, scratches) = line.trim().split(":")
            val (_, cardNumberStr) = cardCardNumber.trim().split("\\s+".toRegex())
            val cardNumber = cardNumberStr.toInt()
            val (winningScratches, myScratches) = scratches.trim().split("|")
            val winningScratchesList =
                winningScratches.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val myScratchesList = myScratches.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val scratchesPair = Pair(winningScratchesList, myScratchesList)
            listOfCards.add(Pair(cardNumber, calculateMatches(scratchesPair)))
        }

        val cardsMap = listOfCards.associate { it.first to it.second }
        var i = 0
        while (i < listOfCards.size) {
            val (cardNumber, matches) = listOfCards[i]
            for (j in 1..matches) {
                listOfCards.add(i + j, Pair(cardNumber + j, cardsMap[cardNumber + j]!!))
            }
            i++
        }
        println(listOfCards.size)
    }

    private fun fileParser(fileName: String): List<String> {
        return File(fileName).readLines()
    }
}

fun main() {
    Day4().task1()
    Day4().task2()
}