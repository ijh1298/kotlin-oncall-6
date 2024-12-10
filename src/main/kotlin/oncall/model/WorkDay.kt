package oncall.model

data class WorkDay(
    val month: Int,
    val day: String,
    val date: Int,
    val worker: String,
    val isHoliday: Boolean
) {
}