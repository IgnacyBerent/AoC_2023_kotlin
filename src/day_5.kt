import java.io.File

class Day5 {

    private val input = fileParser("input/day5.txt")
    private val categories = input.first

    fun task1() {
        fun translate(catMap: List<List<Long>>, number: Long): Long {
            for ((destStart, sourceStart, range) in catMap) {
                if ((sourceStart <= number) && (number <= (sourceStart + range - 1))) {
                    return destStart + (number - sourceStart)
                }
            }
            return number
        }

        var seeds = input.second

        for (category in categories) {
            val newSeeds: MutableList<Long> = mutableListOf()
            for (seed in seeds) {
                newSeeds.add(translate(category, seed))
            }
            seeds = newSeeds
        }
        println(seeds.min())
    }

    fun task2() {

        val seeds = input.second
        val seedsRanges: MutableList<Pair<Long, Long>> = mutableListOf()
        for (i in seeds.indices step 2) {
            seedsRanges.add(Pair(seeds[i], seeds[i] + seeds[i + 1]))
        }
        for (category in categories) {
            val newSeedsRanges: MutableList<Pair<Long, Long>> = mutableListOf()
            while (seedsRanges.isNotEmpty()) {
                val (seedStart, seedEnd) = seedsRanges.removeAt(0)
                var isBreak = false
                for ((destStart, sourceStart, range) in category){
                    val overlapStart = maxOf(seedStart, sourceStart)
                    val overlapEnd = minOf(seedEnd, sourceStart + range)
                    if (overlapStart < overlapEnd) {
                        newSeedsRanges.add(Pair(overlapStart - sourceStart + destStart, overlapEnd - sourceStart + destStart))
                        if (overlapStart > seedStart) {
                            seedsRanges.add(Pair(seedStart, overlapStart))
                        }
                        if (overlapEnd < seedEnd) {
                            seedsRanges.add(Pair(overlapEnd, seedEnd))
                        }
                        isBreak = true
                        break
                    }
                }
                if (!isBreak) {
                    newSeedsRanges.add(Pair(seedStart, seedEnd))
                }
            }
            seedsRanges.clear()
            seedsRanges.addAll(newSeedsRanges)
        }
        seedsRanges.sortBy { it.first }
        println(seedsRanges[0].first)
    }

    private fun fileParser(fileName: String): Pair<List<List<List<Long>>>, List<Long>> {
        val input = File(fileName).readLines()
        val seeds: List<Long> = input[0].split(':')[1].trim().split(' ').map { it.toLong() }
        val categories: MutableList<List<List<Long>>> = mutableListOf()
        var newCategory: MutableList<List<Long>> = mutableListOf()
        for ((i, line) in input.withIndex()) {
            if (i == 0 || i == 1) {
                continue
            } else if (line.trim().isEmpty()) {
                categories.add(newCategory)
            } else if (line[0].isLetter()) {
                newCategory = mutableListOf()
            } else {
                newCategory.add(line.split(' ').map { it.toLong() })
            }
        }
        categories.add(newCategory)
        return Pair(categories, seeds)
    }

}

fun main() {
    Day5().task1()
    Day5().task2()
}