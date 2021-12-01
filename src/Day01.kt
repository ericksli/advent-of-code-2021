fun main() {
    fun part1(input: List<String>): Int = input
        .map { it.toInt() }
        .fold(input.first().toInt() to 0) { (previousValue, count), currentValue ->
            currentValue to if (currentValue > previousValue) count + 1 else count
        }
        .second

    fun part2(input: List<String>): Int = input
        .map { it.toInt() }
        .fold(Triple<List<Int>, Int?, Int>(emptyList(), null, 0)) { (lastThree, previousSum, count), currentValue ->
            val newLastThree = if (lastThree.size <= 3) {
                lastThree + currentValue
            } else {
                lastThree.drop(1) + currentValue
            }
            if (newLastThree.size < 3) {
                Triple(newLastThree, null, 0)
            } else {
                val newSum = newLastThree.reduce { acc, i -> acc + i }
                val newCount = if (previousSum == null) {
                    0
                } else if (newSum > previousSum) {
                    count + 1
                } else {
                    count
                }
                Triple(newLastThree, newSum, newCount)
            }
        }
        .third

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
