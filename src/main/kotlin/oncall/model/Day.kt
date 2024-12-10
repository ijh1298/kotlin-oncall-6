package oncall.model

enum class Day(
    val isWeekend: Boolean
) {
    월(false),
    화(false),
    수(false),
    목(false),
    금(false),
    토(true),
    일(true)
}