package oncall.utils

enum class Error(
    val msg: String
) {
    INVALID_MONTH_DAY("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."),
}