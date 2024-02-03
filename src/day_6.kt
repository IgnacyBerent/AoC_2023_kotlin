import java.io.File
import kotlin.math.sqrt
import kotlin.math.pow

class Day6 {

    private val input = fileParser("input/day6.txt")
    fun task1() {
        val times = input[0].trim().split(':')[1].trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
        val distances = input[1].trim().split(':')[1].trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
        var wins: Long = 1
        for ((time, distance) in times.zip(distances)) {
            wins *= calculateWins(distance, time)
        }
        println(wins)
    }

    fun task2() {
        val time = input[0].trim().split(':')[1].trim().replace(" ", "").toLong()
        val distance = input[1].trim().split(':')[1].trim().replace(" ", "").toLong()
        val wins = calculateWins(distance, time)
        println(wins)
    }

    private fun <T: Number> calculateWins(s: T, t: T): Long {
        return x2(s, t) - x1(s, t)
    }

    private fun <T: Number> x1(s: T, t: T): Long {
        val sDouble = s.toDouble()
        val tDouble = t.toDouble()
        return ((tDouble - sqrt((tDouble.pow(2) - 4*sDouble)))/2).toLong()
    }

    private fun <T: Number> x2(s: T, t: T): Long {
        val sDouble = s.toDouble()
        val tDouble = t.toDouble()
        return ((tDouble + sqrt((tDouble.pow(2) - 4*sDouble)))/2).toLong()
    }

    private fun fileParser(fileName: String): List<String> {
        return File(fileName).readLines()
    }
}

fun main() {
    Day6().task1()
    Day6().task2()
}