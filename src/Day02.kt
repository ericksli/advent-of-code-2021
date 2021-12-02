fun main() {
    fun part1(input: List<String>) = input
        .map { it.split(" ").run { this[0] to this[1].toInt() } }
        .fold(0 to 0) { (horizontal, depth), (command, value) ->
            when (command) {
                "forward" -> horizontal + value to depth
                "down" -> horizontal to depth + value
                "up" -> horizontal to depth - value
                else -> throw IllegalStateException()
            }
        }
        .let { (horizontal, depth) -> horizontal * depth }

    fun part2(input: List<String>) = input
        .map { it.split(" ").run { this[0] to this[1].toInt() } }
        .fold(Triple(0, 0, 0)) { (horizontal, aim, depth), (command, value) ->
            when (command) {
                "down" -> Triple(horizontal, aim + value, depth)
                "up" -> Triple(horizontal, aim - value, depth)
                "forward" -> Triple(horizontal + value, aim, depth + aim * value)
                else -> throw IllegalStateException()
            }
        }
        .let { (horizontal, _, depth) -> horizontal * depth }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
