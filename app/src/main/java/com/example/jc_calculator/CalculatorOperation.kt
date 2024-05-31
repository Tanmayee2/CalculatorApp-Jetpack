package com.example.jc_calculator

sealed class CalculatorOperation (val symbol: String) {
    object Add: CalculatorOperation ("+")
    object Subtract: CalculatorOperation ("-")
    object Multiply: CalculatorOperation ("x")
    object Divide: CalculatorOperation("/")
}