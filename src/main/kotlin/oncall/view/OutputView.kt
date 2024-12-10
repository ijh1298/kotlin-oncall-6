package oncall.view

import oncall.model.WorkDay

class OutputView {
    fun printResult(monthlySchedule: List<WorkDay>) {
        monthlySchedule.forEach { println(it.toString()) }
    }
}