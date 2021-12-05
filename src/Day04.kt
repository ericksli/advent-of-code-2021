fun main() {

    val pattern = Regex("""(\d+)""")
    val itemsPerRow = 5

    data class Bingo(
        val numbers: List<Int>,
        val boards: List<List<Int>>,
    )

    fun parseBingo(input: List<String>): Bingo {
        val numbers = input.first().split(',').map(String::toInt)
        val boards = buildList {
            val tempBoard = mutableListOf<Int>()
            input.drop(2).forEach { line ->
                if (line.isEmpty()) {
                    add(tempBoard.toList())
                    tempBoard.clear()
                } else {
                    val row = pattern.findAll(line)
                        .map { it.value.toInt() }
                        .toList()
                    tempBoard.addAll(row)
                }
            }
            add(tempBoard)
        }
        return Bingo(numbers, boards)
    }

    fun getCoordinates(index: Int): Pair<Int, Int> {
        val row = index / itemsPerRow
        val column = index - row * itemsPerRow
        return Pair(row, column)
    }

    fun isEndGame(marked: List<Int>): Boolean {
        val rowGroup = marked.groupBy { getCoordinates(it).first }
        rowGroup.forEach { (_, freq) -> if (freq.size == itemsPerRow) return true }
        val columnGroup = marked.groupBy { getCoordinates(it).second }
        columnGroup.forEach { (_, freq) -> if (freq.size == itemsPerRow) return true }
        return false
    }

    fun part1(input: List<String>): Int {
        val bingo = parseBingo(input)
        val markedBoards = listOf(*(0 until bingo.boards.size).map { mutableListOf<Int>() }.toTypedArray())
        bingo.numbers.forEachIndexed { numberIndex, number ->
            bingo.boards.forEachIndexed { boardIndex, board ->
                val position = board.indexOf(number)
                if (position >= 0) {
                    markedBoards[boardIndex].add(position)
                }
                if (isEndGame(markedBoards[boardIndex])) {
                    val calledNumbers = bingo.numbers.take(numberIndex + 1)
                    val sum = board.asSequence()
                        .filter { it !in calledNumbers }
                        .sum()
                    return sum * number
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val bingo = parseBingo(input)
        val markedBoards = listOf(*(0 until bingo.boards.size).map { mutableListOf<Int>() }.toTypedArray())
        val finishedBoardIndexes = mutableSetOf<Int>()
        bingo.numbers.forEachIndexed { numberIndex, number ->
            bingo.boards.forEachIndexed { boardIndex, board ->
                if (boardIndex !in finishedBoardIndexes) {
                    val position = board.indexOf(number)
                    if (position >= 0) {
                        markedBoards[boardIndex].add(position)
                    }
                    if (isEndGame(markedBoards[boardIndex])) {
                        val calledNumbers = bingo.numbers.take(numberIndex + 1)
                        if (finishedBoardIndexes.size == bingo.boards.size - 1) {
                            val sum = board.asSequence()
                                .filter { it !in calledNumbers }
                                .sum()
                            return sum * number
                        }
                        finishedBoardIndexes.add(boardIndex)
                    }
                }
            }
        }
        return -1
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
