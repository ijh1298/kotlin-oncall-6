package oncall.service

import oncall.model.Day
import oncall.model.WorkDay

object OncallService {
    private val endDateOfMonth = listOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private var lastDayWorker = ""

    private lateinit var today: Day
    private var month: Int = 1
    private lateinit var weekdayOrder: ArrayDeque<String>
    private lateinit var holidayOrder: ArrayDeque<String>

    fun getMonthlySchedule(
        startDay: Day,
        startMonth: Int,
        inputWeekdayOrder: ArrayDeque<String>,
        inputHolidayOrder: ArrayDeque<String>
    ): List<WorkDay> {
        today = startDay
        month = startMonth
        weekdayOrder = inputWeekdayOrder
        holidayOrder = inputHolidayOrder

        val schedule = mutableListOf<WorkDay>()
        for (date in 1..endDateOfMonth[month]) {
            schedule += getDaySchedule(month, today, date)
            today = Day.entries[(today.ordinal + 1) % 7]
        }
        return schedule
    }

    private fun getDaySchedule(month: Int, day: Day, date: Int): WorkDay {
        val isTodayHoliday = checkHoliday(month, date)
        var candidateWorker = ""
        if (day.isWeekend || isTodayHoliday)
            return makeDaySchedule(holidayOrder, day, date, isTodayHoliday)
        return makeDaySchedule(weekdayOrder, day, date, false)
    }

    private fun makeDaySchedule(order: ArrayDeque<String>, day: Day, date: Int, isTodayHoliday: Boolean): WorkDay {
        var candidateWorker = ""
        val firstWorkerOfOrder = order.removeFirst()

        if (lastDayWorker == firstWorkerOfOrder) {
            candidateWorker = order.removeFirst() // 이틀 연속 근무일 경우 다음 근무자를 먼저
            order.addFirst(firstWorkerOfOrder)
            order.addLast(candidateWorker)
            lastDayWorker = candidateWorker
            return WorkDay(month, day.name, date, candidateWorker, isTodayHoliday)
        }
        order.addLast(firstWorkerOfOrder)
        lastDayWorker = firstWorkerOfOrder
        return WorkDay(month, day.name, date, firstWorkerOfOrder, isTodayHoliday)
    }

    private fun checkHoliday(month: Int, date: Int): Boolean {
        val holidays = listOf(1 to 1, 3 to 1, 5 to 5, 6 to 6, 8 to 15, 10 to 3, 10 to 9, 12 to 25)
        return holidays.any { month == it.first && date == it.second }
    }
}