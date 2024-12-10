package oncall.model

data class WorkDay(
    val month: Int,
    val day: String,
    val date: Int,
    val worker: String,
    val isHoliday: Boolean
) {
    override fun toString(): String {
        if (isHoliday)
            return "${month}월 ${date}일 $day(휴일) $worker"
        return "${month}월 ${date}일 $day $worker"
    }
}