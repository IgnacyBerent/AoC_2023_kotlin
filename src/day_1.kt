
fun fileParser(fileName: String): List<String> {
    return object {}.javaClass.getResource(fileName).readText().split("\n")
}


class Task1 {
    fun solution() {

    }
}

fun main() {
    Task1().solution()
}