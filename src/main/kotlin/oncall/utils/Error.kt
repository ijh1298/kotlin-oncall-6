package oncall.utils

enum class Error(
    val msg: String
) {
    INVALID_MONTH_DAY("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."),

    INVALID_WORKER("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."),
    NOT_CONTAINED_ALL_WORKER("[ERROR] 평일 근무자가 모두 휴일 근무자에 포함되어야 합니다. 다시 입력해 주세요.")
}