package io.letDrink.localbar

import org.junit.Test
import kotlin.math.roundToInt

class Finance {
    val price = 11_000_000
    val startMortagePayment = 1_100_000

    val mortagePayment = 99_824
    val eachMounth = 40_000

    val yearLong = 15
    val depositePercent = 5.toDouble()

    @Test
    fun score() {
        val mortageSum = yearLong * mortagePayment * 12
        println("суммпа по ипотеке: " + formatSumm(mortageSum))
        println("ежемес по ипотеке: " + formatSumm(mortagePayment))
        println("ежемес аренда: " + formatSumm(eachMounth))
        println("годовой процент вклада: 5")
        println()

        var capital = startMortagePayment

        (1..yearLong).forEach {

            println("сумма на $it год :" + formatSumm(capital))

            val depositYearAcceptance =
                depositYearAcceptanceWithD(capital, depositePercent)

            println("проценты за $it год :" + formatSumm(depositYearAcceptance))

            val diff = (mortagePayment - eachMounth) * 12

            println("cэкономлено на аренде: " + formatSumm(diff))

            capital += depositYearAcceptance + diff
            println()
        }

        println("ОБЩАЯ ВЫРУЧКА: " + formatSumm(capital))
    }

    fun depositYearAcceptanceWithD(sum: Int, percent: Double): Int {
        val percentForYear = scorePercents(sum, percent).roundToInt()
        return percentForYear
    }

    fun formatSumm(sum: Int) = String.format("%,d", sum)

    private fun scorePercents(sum: Int, double: Double) = (sum / 100) * double
}