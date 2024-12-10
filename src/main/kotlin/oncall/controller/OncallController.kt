package oncall.controller

import oncall.model.Day
import oncall.model.WorkDay
import oncall.service.OncallService
import oncall.validator.MonthDayValidator
import oncall.validator.WorkerValidator
import oncall.view.InputView
import oncall.view.OutputView

class OncallController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView()
) {
    private var month: Int = 1
    private var day = "월"
    private val weekdayOrder = ArrayDeque<String>()
    private val holidayOrder = ArrayDeque<String>()
    private lateinit var monthlySchedule: List<WorkDay>

    fun run() {
        setMonthDay()
        setWeekdayOrder()
        setHolidayOrder()
        monthlySchedule = OncallService.getMonthlySchedule(Day.valueOf(day), month, weekdayOrder, holidayOrder)
        outputView.printResult(monthlySchedule)
    }

    private fun setMonthDay() {
        var rawMonthDay = ""
        loopUntilValid {
            rawMonthDay = inputView.inputMonthDay()
            MonthDayValidator(rawMonthDay).validate()
        }
        val parsed = rawMonthDay.split(',').map { it }
        month = parsed[0].toInt()
        day = parsed[1]
    }

    private fun setWeekdayOrder() {
        var rawWorkers = ""
        loopUntilValid {
            rawWorkers = inputView.inputWeekdays()
            WorkerValidator(rawWorkers).validateWeekdayWorkers()
        }
        val parsed = rawWorkers.split(',').map { it }
        parsed.forEach { weekdayOrder += it }
    }

    private fun setHolidayOrder() {
        var rawWorkers = ""
        loopUntilValid {
            rawWorkers = inputView.inputHolidays()
            WorkerValidator(rawWorkers).validateHolidayWorkers(weekdayOrder)
        }
        val parsed = rawWorkers.split(',').map { it }
        parsed.forEach { holidayOrder += it }
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