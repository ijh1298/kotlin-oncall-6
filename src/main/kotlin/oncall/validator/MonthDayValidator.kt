package oncall.validator

import oncall.utils.Error

class MonthDayValidator(
    private val rawMonthDay: String
) {
    private lateinit var monthDay: Pair<Int, String>
    private val days = setOf("월", "화", "수", "목", "금", "토", "일")

    fun validate() {
        require(isValid()) { Error.INVALID_MONTH_DAY.msg }
        setMonthDay()
        require(isInRange()) { Error.INVALID_MONTH_DAY.msg }
    }

    private fun isValid() = Regex("^\\d+,.$").matches(rawMonthDay)

    private fun setMonthDay() {
        val parsed = rawMonthDay.split(',').map { it }
        monthDay = parsed[0].toInt() to parsed[1]
    }

    private fun isInRange() = monthDay.first in 1..12 && monthDay.second in days
}