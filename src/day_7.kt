import java.io.File
import kotlin.math.pow

class Day7 {

    private val input = fileParser("input/day7.txt")
    fun task1() {
        val cardStrengths = mapOf(
            'A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11, 'T' to 10, '9' to 9,
            '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2
        )
        val hands = mutableListOf<Hand>()
        for (line in input) {
            val (hand, bid) = line.split(" ")
            hands.add(Hand(hand, bid.toInt(), cardStrengths, 1))
        }
        hands.sort()
        var totalWinnings = 0
        for ((i, card) in hands.withIndex()) {
            totalWinnings += card.bid * (i + 1)
        }
        println(totalWinnings)
    }

    fun task2() {
        val cardStrengths = mapOf(
            'A' to 14, 'K' to 13, 'Q' to 12, 'J' to 1, 'T' to 10, '9' to 9,
            '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2
        )
        val hands = mutableListOf<Hand>()
        for (line in input) {
            val (hand, bid) = line.split(" ")
            hands.add(Hand(hand, bid.toInt(), cardStrengths, 2))
        }
        hands.sort()
        var totalWinnings = 0
        for ((i, card) in hands.withIndex()) {
            totalWinnings += card.bid * (i + 1)
        }
        println(totalWinnings)
    }

    private fun fileParser(fileName: String): List<String> {
        return File(fileName).readLines()
    }

    class Hand(cards: String, val bid: Int, cS: Map<Char, Int>, private val task: Int) : Comparable<Hand> {
        private var _cards: List<Char> = cards.toList()
        private val strength: Int
        private val type: Int
        private val cardStrengths = cS

        init {
            strength = calculateStrength()
            type = determineType()
        }

        private fun calculateStrength(): Int {
            var strength = 0
            for ((i, card) in _cards.withIndex()) {
                strength += (cardStrengths[card]!! * 14.0.pow((4 - i).toDouble())).toInt()
            }
            return strength
        }

        private fun determineType(): Int {
            val cardsCollection = _cards.groupingBy { it }.eachCount()

            if ('J' in _cards && task == 2) {
                val jokers = _cards.count { it == 'J' }
                if (cardsCollection.size == 1) {  // Five of a kind
                    return 7
                }

                val maxEntry = cardsCollection.filter { it.key != 'J' }.maxByOrNull { it.value }
                val commonWithJ = maxEntry?.value?.plus(jokers) ?: 0

                return when (commonWithJ) {
                    5 -> 7  // Five of a kind
                    4 -> 6  // Four of a kind
                    3 -> if (cardsCollection.size == 3) 5 else 4  // Full house or Three of a kind
                    else -> 2  // One pair
                }
            } else {
                return when (cardsCollection.size) {
                    1 -> 7  // Five of a kind
                    2 -> if (cardsCollection.any { it.value == 4 }) 6 else 5  // Four of a kind or Full house
                    3 -> if (cardsCollection.any { it.value == 3 }) 4 else 3  // Three of a kind or Two pairs
                    4 -> 2  // One pair
                    else -> 1  // High card
                }
            }
        }

        override fun compareTo(other: Hand): Int {
            return when {
                this.type != other.type -> this.type - other.type
                else -> this.strength - other.strength
            }
        }

        override fun toString(): String {
            return "$_cards"
        }
    }
}

fun main() {
    Day7().task1()
    Day7().task2()
}