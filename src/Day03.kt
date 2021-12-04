fun main() {
    fun part1(input: List<String>): Int {
        val bitLength = input.first().length
        val initial = (0 until bitLength).map { 0 }
        val bitCounts = input.fold(initial) { sum, binary ->
            val newSum = sum.toMutableList()
            binary.toCharArray().forEachIndexed { index, c ->
                if (c == '1') {
                    newSum[index] = newSum[index] + 1
                }
            }
            newSum
        }
        val gammaRate = bitCounts.fold("") { acc, oneCount ->
            acc + if (oneCount > input.size - oneCount) "1" else "0"
        }
        val epsilonRate = gammaRate.toCharArray()
            .map { if (it == '1') '0' else '1' }
            .joinToString("")
        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun bitsAtPosition(list: List<String>, position: Int) = list.map { it[position] }

    fun mostOrLeastCommonValue(bits: List<Char>, sameCountFallback: Char, isMostCommon: Boolean): Char {
        val zeroCount = bits.count { it == '0' }
        val oneCount = bits.size - zeroCount
        return when {
            isMostCommon && zeroCount > oneCount -> '0'
            isMostCommon && zeroCount < oneCount -> '1'
            !isMostCommon && zeroCount > oneCount -> '1'
            !isMostCommon && zeroCount < oneCount -> '0'
            else -> sameCountFallback
        }
    }

    fun findNumber(list: List<String>, position: Int, target: Char, isMostCommon: Boolean): String {
        val bits = bitsAtPosition(list, position)
        val mostOrLeastCommonValue = mostOrLeastCommonValue(bits, target, isMostCommon)
        val filtered = list.filter { it[position] == mostOrLeastCommonValue }
        return if (filtered.size > 1) {
            findNumber(filtered, position + 1, target, isMostCommon)
        } else {
            filtered.first()
        }
    }

    fun part2(input: List<String>): Int {
        val oxygen = findNumber(input, 0, '1', true)
        val co2 = findNumber(input, 0, '0', false)
        return oxygen.toInt(2) * co2.toInt(2)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
