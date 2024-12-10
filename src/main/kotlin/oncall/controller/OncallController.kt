package oncall.controller

import oncall.model.WorkDay
import oncall.validator.MonthDayValidator
import oncall.validator.WorkerValidator
import oncall.view.InputView

class OncallController(
    private val inputView: InputView = InputView(),
) {
    private var month: Int = 1
    private var day = "월"
    private lateinit var weekdayOrder: List<String>
    private lateinit var holidayOrder: List<String>

    fun run() {
        setMonthDay()
        setWeekdayOrder()
        setHolidayOrder()
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

    private fun setWeekdayOrder() {
        val rawWorkers = inputView.inputWeekdays()
        loopUntilValid {
            WorkerValidator(rawWorkers).validateWeekdayWorkers()
        }
        val parsed = rawWorkers.split(',').map { it }
        weekdayOrder = parsed
    }

    private fun setHolidayOrder() {
        val rawWorkers = inputView.inputHolidays()
        loopUntilValid {
            WorkerValidator(rawWorkers).validateHolidayWorkers(weekdayOrder)
        }
        val parsed = rawWorkers.split(',').map { it }
        holidayOrder = parsed
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