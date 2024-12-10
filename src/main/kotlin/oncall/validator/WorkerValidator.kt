package oncall.validator

import oncall.utils.Error

class WorkerValidator(
    private val rawWorkers: String
) {
    private lateinit var workers: List<String>

    fun validateWeekdayWorkers(): Boolean {
        validateWorkers()
        return true
    }

    fun validateHolidayWorkers(weekdayWorkers: List<String>): Boolean {
        validateWeekdayWorkers()
        require(isAllInWeekdayWorkers(weekdayWorkers)) { Error.NOT_CONTAINED_ALL_WORKER }
        return true
    }

    fun validateWorkers() {
        val parsed = rawWorkers.split(',').map { it }

        require(parsed.all { it.length in 1..5 }) { Error.INVALID_WORKER.msg }
        require(parsed.size in 5..35) { Error.INVALID_WORKER.msg }
        require(parsed.toSet().size == parsed.size) { Error.INVALID_WORKER.msg }
    }

    private fun isAllInWeekdayWorkers(weekdayWorkers: List<String>): Boolean {
        val holidayWorkers = rawWorkers.split(',').map { it }
        return holidayWorkers.containsAll(weekdayWorkers)
    }
}