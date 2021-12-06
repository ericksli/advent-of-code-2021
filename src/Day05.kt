import kotlin.math.max

fun main() {

    val pattern = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")

    fun parse(input: List<String>, filterDiagonal: Boolean) = input
        .flatMap {
            val result = pattern.matchEntire(it)!!
            val x1 = result.groupValues[1].toInt()
            val y1 = result.groupValues[2].toInt()
            val x2 = result.groupValues[3].toInt()
            val y2 = result.groupValues[4].toInt()

            val xRange = (if (x1 < x2) x1..x2 else x1 downTo x2).toList()
            val yRange = (if (y1 < y2) y1..y2 else y1 downTo y2).toList()

            val isHorizontal = xRange.size == 1
            val isVertical = yRange.size == 1
            if (filterDiagonal && !(isHorizontal xor isVertical)) {
                emptyList()
            } else {
                (0 until max(xRange.size, yRange.size)).map { i ->
                    val x = if (i in xRange.indices) xRange[i] else xRange.last()
                    val y = if (i in yRange.indices) yRange[i] else yRange.last()
                    x to y
                }
            }
        }
        .groupBy { it }
        .filterValues { it.size >= 2 }
        .count()

    fun part1(input: List<String>): Int = parse(input, true)

    fun part2(input: List<String>): Int = parse(input, false)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
