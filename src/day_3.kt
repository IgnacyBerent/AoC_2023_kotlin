import java.io.File

class Day3 {

    private val linesMatrix: List<List<String>> = fileParser("input/day3.txt")


    fun task1() {
        val symbols = listOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '_',
            '+',
            '-',
            '=',
            '{',
            '}',
            '[',
            ']',
            '|',
            '\\',
            ':',
            ';',
            '"',
            '\'',
            '<',
            '>',
            ',',
            '?',
            '/',
            '`',
            '~'
        )

        fun findSymbols(line: List<String>): List<Int> {
            return line.mapIndexedNotNull { index, char -> if (char.single() in symbols) index else null }
        }

        fun findPartNumbers(numbers: List<Map<Pair<Int, Int>, Int>>, symbolsCords: List<Pair<Int, Int>>): List<Int> {
            val partNumbers = mutableListOf<Int>()
            for (numWithCoords in numbers) {
                for ((coords, num) in numWithCoords) {
                    val (xPos, yPos) = coords
                    val numLength = num.toString().length
                    for (addY in -1..1) {
                        var found = false
                        for (addX in -1..<numLength + 1) {
                            if (Pair(xPos + addX, yPos + addY) in symbolsCords) {
                                partNumbers.add(num)
                                found = true
                                break
                            }
                        }
                        if (found) break
                    }
                }
            }
            return partNumbers
        }


        val numbersCoords = linesMatrix.mapIndexed { yPos, matrixLine ->
            findNumbers(matrixLine).mapKeys { (xPos, _) -> Pair(xPos, yPos) }
        }
        val symbolsCoords = linesMatrix.flatMapIndexed { yPos, matrixLine ->
            findSymbols(matrixLine).map { xPos -> Pair(xPos, yPos) }
        }
        val partNumbers = findPartNumbers(numbersCoords, symbolsCoords)
        println(partNumbers.sum())
    }

    fun task2() {
        fun findStars(line: List<String>): List<Int> {
            return line.mapIndexedNotNull { index, char -> if (char == "*") index else null }
        }
        fun findGearNumbers(numbers: List<Map<Pair<Int, Int>, Int>>, symbolsCords: List<Pair<Int, Int>>): List<Pair<Int, Pair<Int, Int>>> {
            val gearNumbers = mutableListOf<Pair<Int, Pair<Int, Int>>>()
            for (numWithCoords in numbers) {
                for ((coords, num) in numWithCoords) {
                    val (xPos, yPos) = coords
                    val numLength = num.toString().length
                    for (addY in -1..1) {
                        for (addX in -1..<numLength + 1) {
                            val newCoords = Pair(xPos + addX, yPos + addY)
                            if (newCoords in symbolsCords) {
                                gearNumbers.add(Pair(num, newCoords))
                            }
                        }
                    }
                }
            }
            return gearNumbers
        }
        fun calcGearRatios(gearNumbers: List<Pair<Int, Pair<Int, Int>>>): Int {
            val numbers = gearNumbers.map { it.first }
            val coords = gearNumbers.map { it.second }
            val ratios = mutableListOf<Int>()
            for (i in numbers.indices) {
                for (j in i + 1..<numbers.size) {
                    if (coords[i] == coords[j]) {
                        ratios.add(numbers[i] * numbers[j])
                    }
                }
            }
            return ratios.sum()
        }

        val numbersCoords = linesMatrix.mapIndexed { yPos, matrixLine ->
            findNumbers(matrixLine).mapKeys { (xPos, _) -> Pair(xPos, yPos) }
        }
        val symbolsCoords = linesMatrix.flatMapIndexed { yPos, matrixLine ->
            findStars(matrixLine).map { xPos -> Pair(xPos, yPos) }
        }
        val gearsNumbers = findGearNumbers(numbersCoords, symbolsCoords)
        println(calcGearRatios(gearsNumbers))
    }

    private fun findNumbers(line: List<String>): Map<Int, Int> {
        val numbers = mutableListOf<Int>()
        val xPositions = mutableListOf<Int>()
        var i = 0
        while (i < line.size) {
            val wholeNumber = mutableListOf<String>()
            if (line[i].toIntOrNull() != null) {
                xPositions.add(i)
                var j = 0
                while (j < 4) {
                    if (i + j >= line.size || line[i + j].toIntOrNull() == null) {
                        numbers.add(wholeNumber.joinToString("").toInt())
                        break
                    }
                    wholeNumber.add(line[i + j])
                    j += 1
                }
                i += j
            } else {
                i += 1
            }
        }
        return xPositions.zip(numbers).toMap()
    }

    private fun fileParser(fileName: String): List<List<String>> {
        val lines = File(fileName).readLines()
        return lines.map { it.toCharArray().map { char -> char.toString() } }
    }
}

fun main() {
    Day3().task1()
    Day3().task2()
}