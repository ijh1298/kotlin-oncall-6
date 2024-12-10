package oncall.validator

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class WorkerValidatorTest {
    @ParameterizedTest
    @ValueSource(
        strings = ["이름이길어요,두두,고고"]
    )
    fun `근무자 닉네임 1~5자 아닌 범위 예외 처리`(input: String) {
        assertThrows<IllegalArgumentException> { WorkerValidator(input).validateWorkers() }
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["하나,둘,셋"]
    )
    fun `근무자 인원 5~35명 아닌 범위 예외 처리`(input: String) {
        assertThrows<IllegalArgumentException> { WorkerValidator(input).validateWorkers() }
    }

    @ParameterizedTest
    @ValueSource(
        strings = ["하나,하나,둘,셋"]
    )
    fun `근무자 이름 중복 예외 처리`(input: String) {
        assertThrows<IllegalArgumentException> { WorkerValidator(input).validateWorkers() }
    }
}