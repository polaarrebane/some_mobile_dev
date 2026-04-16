/**
 * This file contains a finite state machine for inputting a valid expression.
 */
package com.ronia.fr.module00.ex03.calculator

/*
stateDiagram
    Initial --> Integer : event(Digit)
    Initial --> Integer : event(OperationSymbol(Minus))

    Integer --> Integer : event(Digit)
    Integer --> Real : event(Separator)
    Integer --> Operation : event(OperationSymbol)

    Real --> Real : event(Digit)
    Real --> Operation : event(OperationSymbol)

    Operation --> Operation : event(OperationSymbol)
    Operation --> Number : event(Digit)
 */

const val MAX_DIGITS = 7  // Maximum length of a number

enum class OperationSymbol(val value: String) {
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/")
}

sealed class Event {
    data class Digit(val digit: Int) : Event()
    data class Operation(val operationSymbol: OperationSymbol) : Event()
    data class Separator(val symbol: String) : Event()
}

sealed class State {
    object Initial : State()
    data class Integer(
        val value: String = "",
        val currentNumber: String = ""
    ) : State()

    data class Real(
        val value: String = "",
        val integerPart: String = "",
        val separator: String = ".",
        val fractionalPart: String = "",
    ) : State()

    data class Operation(
        val value: String = "",
        val lastOperation: String = ""
    ) : State()
}

fun transition(state: State, event: Event): State {
    return when (state) {
        is State.Initial -> when (event) {
            is Event.Digit -> {
                val d = event.digit.toString()
                if (d == "0") {
                    state
                } else {
                    State.Integer(currentNumber = d)
                }
            }
            is Event.Operation -> {
                if (event.operationSymbol == OperationSymbol.MINUS){
                    State.Operation(lastOperation = "-")
                } else {
                    state
                }
            }
            else -> state
        }

        is State.Integer -> when (event) {
            is Event.Digit -> {
                if(state.currentNumber == "0"){
                    State.Integer(
                        value = state.value,
                        currentNumber = event.digit.toString()
                    )
                } else if (state.currentNumber.length < MAX_DIGITS) {
                    val d = event.digit.toString()
                    State.Integer(
                        value = state.value,
                        currentNumber = state.currentNumber + d
                    )
                } else {
                    state
                }
            }

            is Event.Separator -> {
                State.Real(
                    value = state.value,
                    integerPart = state.currentNumber,
                    separator = event.symbol,
                    fractionalPart = ""
                )
            }

            is Event.Operation -> {
                State.Operation(
                    value = state.value + state.currentNumber,
                    lastOperation = event.operationSymbol.value
                )
            }
        }

        is State.Real -> when (event) {
            is Event.Digit -> {
                if (state.fractionalPart.length < MAX_DIGITS) {
                    val d = event.digit.toString()
                    State.Real(
                        value = state.value,
                        integerPart = state.integerPart,
                        separator = state.separator,
                        fractionalPart = state.fractionalPart + d
                    )
                } else {
                    state
                }
            }

            is Event.Separator -> state

            is Event.Operation -> {
                if(state.fractionalPart == ""){
                    state
                } else {
                    var value = state.value
                    value += state.integerPart
                    value += state.separator
                    value += state.fractionalPart
                    State.Operation(
                        value = value,
                        lastOperation = event.operationSymbol.value
                    )
                }
            }
        }

        is State.Operation -> when (event) {
            is Event.Digit -> {
                State.Integer(
                    value = state.value + state.lastOperation,
                    currentNumber = event.digit.toString()
                )
            }

            is Event.Operation -> {
                if (event.operationSymbol == OperationSymbol.MINUS){
                    State.Operation(
                        value = state.value + state.lastOperation,
                        lastOperation = "-"
                    )
                } else {
                    State.Operation(
                        value = state.value,
                        lastOperation = event.operationSymbol.value
                    )
                }
            }

            is Event.Separator -> state
        }
    }
}

fun stateAsString(state: State): String {
    return when (state) {
        is State.Initial -> ""
        is State.Integer -> state.value + state.currentNumber
        is State.Real -> state.value + state.integerPart + state.separator + state.fractionalPart
        is State.Operation -> state.value + state.lastOperation
    }
}
