package oncall.controller

import oncall.validator.MonthDayValidator
import oncall.view.InputView

class OncallController(
    private val inputView: InputView = InputView(),
) {
    private var month: Int = 1
    private var day = "월"

    fun run() {
        setMonthDay()
    }

    private fun setMonthDay() {
        val rawMonthDay = inputView.inputMonthDay()
        loopUntilValid {
            MonthDayValidator(rawMonthDay).validate()
        }
        val parsed = rawMonthDay.split(',').map { it }
        month = parsed[0].toInt()
        day = parsed[1]
    }

    private fun <T> loopUntilValid(action: () -> T): T {
        while (true) {
            try {
                return action()
            } catch (e: IllegalArgumentException) {
                println(e.message + '\n')
            }
        }
    }
}