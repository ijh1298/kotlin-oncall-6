package oncall.service

import oncall.model.Day
import oncall.model.WorkDay

object OncallService {
    private val endDateOfMonth = listOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private var lastDayWorker = ""

    fun getMonthlySchedule(
        startDay: Day,
        month: Int,
        weekdayOrder: ArrayDeque<String>,
        holidayOrder: ArrayDeque<String>
    ): List<WorkDay> {
        val schedule = mutableListOf<WorkDay>()
        var today: Day = startDay

        for (date in 1..endDateOfMonth[month]) {
            schedule += makeDaySchedule(month, today, date, weekdayOrder, holidayOrder)
            today = Day.entries[(today.ordinal + 1) % 7]
        }
        return schedule
    }

    private fun makeDaySchedule(
        month: Int,
        day: Day,
        date: Int,
        weekdayOrder: ArrayDeque<String>,
        holidayOrder: ArrayDeque<String>
    ): WorkDay {
        val isTodayHoliday = checkHoliday(month, date)
        var candidateWorker = ""
        if (day.isWeekend) {
            val firstWorkerOfHolidayOrder = holidayOrder.removeFirst()
            if (lastDayWorker == firstWorkerOfHolidayOrder) {
                candidateWorker = holidayOrder.removeFirst() // 이틀 연속 근무일 경우 다음 근무자를 먼저
                holidayOrder.addFirst(firstWorkerOfHolidayOrder)
                holidayOrder.addLast(candidateWorker)
                return WorkDay(month, day.name, date, candidateWorker, isTodayHoliday)
            }
            holidayOrder.addLast(firstWorkerOfHolidayOrder)
            return WorkDay(month, day.name, date, firstWorkerOfHolidayOrder, isTodayHoliday)
        }

        val firstWorkerOfWeekdayOrder = weekdayOrder.removeFirst()
        if (lastDayWorker == firstWorkerOfWeekdayOrder) {
            candidateWorker = weekdayOrder.removeFirst() // 이틀 연속 근무일 경우 다음 근무자를 먼저
            weekdayOrder.addFirst(firstWorkerOfWeekdayOrder)
            weekdayOrder.addLast(candidateWorker)
            return WorkDay(month, day.name, date, candidateWorker, isTodayHoliday)
        }
        weekdayOrder.addLast(firstWorkerOfWeekdayOrder)
        return WorkDay(month, day.name, date, firstWorkerOfWeekdayOrder, isTodayHoliday)
    }

    private fun checkHoliday(month: Int, date: Int): Boolean {
        val holidays = listOf(1 to 1, 3 to 1, 5 to 5, 6 to 6, 8 to 15, 10 to 3, 10 to 9, 12 to 25)
        return holidays.any { month == it.first && date == it.second }
    }
}