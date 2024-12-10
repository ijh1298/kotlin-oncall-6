package oncall.validator

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MonthDayValidatorTest {
    @ParameterizedTest
    @ValueSource(
        strings = ["11월,월요일", "1,목요일", "5,Monday", "3,,월"]
    )
    fun `월, 일 입력 유효성 검사`(input: String) {
        assertThrows<IllegalArgumentException> { MonthDayValidator(input).validate() }
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["13,월", "500,목", "5,감", "0,월"]
    )
    fun `월, 일 입력 범위 검사`(input: String) {
        assertThrows<IllegalArgumentException> { MonthDayValidator(input).validate() }
    }
}