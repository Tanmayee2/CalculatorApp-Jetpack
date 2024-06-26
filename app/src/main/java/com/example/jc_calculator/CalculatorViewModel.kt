package com.example.jc_calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Percentage -> findPercent()
            is CalculatorAction.PlusMinus -> plusMinus()
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
        } else if (!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number1 = state.number2 + ".")
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(number1 = state.number1 + number)
        } else if (state.number2.length < MAX_NUM_LENGTH) {
            state = state.copy(number2 = state.number2 + number)
        }
    }

    private fun findPercent() {
        if (state.operation == null && state.number1.isNotBlank()) {
            val number1 = state.number1.toDoubleOrNull()
            if (number1 != null) {
                val result = number1 / 100
                state = state.copy(number1 = result.toString())
            }
        } else if (state.number2.isNotBlank()) {
            val number2 = state.number2.toDoubleOrNull()
            if (number2 != null) {
                val result = number2 / 100
                state = state.copy(number2 = result.toString())
            }
        }
    }

    private fun plusMinus() {
        if (state.operation == null && state.number1.isNotBlank()) {
            val number1 = state.number1.toDoubleOrNull()
            if (number1 != null) {
                state = state.copy(number1 = (-number1).toString())
            }
        } else if (state.number2.isNotBlank()) {
            val number2 = state.number2.toDoubleOrNull()
            if (number2 != null) {
                state = state.copy(number2 = (-number2).toString())
            }
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}
