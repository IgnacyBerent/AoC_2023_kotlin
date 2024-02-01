import java.io.File


class Day1 {

    private val input = fileParser("input/day1.txt")
    fun task1() {

        fun findNumbers(line: String): Int {
            val numbers = line.filter { it.isDigit() }.map { it.toString().toInt() }
            return makeWholeNumber(numbers)
        }

        val numbersSum = input.sumOf { findNumbers(it) }
        println(numbersSum)


    }

    fun task2() {

        fun findNumbers(line: String): Int {
            val numsWord: Map<String, Int> = mapOf(
                "one" to 1,
                "two" to 2,
                "three" to 3,
                "four" to 4,
                "five" to 5,
                "six" to 6,
                "seven" to 7,
                "eight" to 8,
                "nine" to 9
            )
            val numbers: MutableList<Int> = mutableListOf()
            for ((i, char) in line.withIndex()) {
                if (char.isDigit()) {
                    numbers.addLast(char.toString().toInt())
                } else {
                    var word = ""
                    for (j in 0..6) {
                        try {
                            line[i + j]
                        } catch (e: IndexOutOfBoundsException) {
                            break
                        }
                        if (line[i + j].isLetter()) {
                            word += line[i + j]
                        } else {
                            break
                        }
                        if (numsWord.containsKey(word)) {
                            numbers.addLast(numsWord[word])
                            break
                        }
                    }
                }
            }
            return makeWholeNumber(numbers)
        }

        val numbersSum = input.sumOf { findNumbers(it) }
        println(numbersSum)
    }

    private fun makeWholeNumber(numbers: List<Int>): Int {
        return if (numbers.size == 1) {
            (numbers[0].toString() + numbers[0].toString()).toInt()
        } else {
            (numbers[0].toString() + numbers.last().toString()).toInt()
        }
    }

    private fun fileParser(fileName: String): List<String> {
        return File(fileName).readLines()
    }
}

fun main() {
    Day1().task1()
    Day1().task2()
}