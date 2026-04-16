package com.ronia.fr.module00.ex03.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

const val MSX_INPUT_LENGTH = 15

class Calculator (
    var state: State = State.Initial
) {
    private var _states = ArrayDeque<State>()

    init {
        _states.addLast(state)
        state = State.Integer(currentNumber = "0")
    }

    var currentInput by mutableStateOf("0")
        private set

    var result by mutableStateOf("0")
        private set

    fun onDigit(digit: Int){
        if (notOverflow()) {
            val oldState = state
            state = transition(state, Event.Digit(digit))
            if(oldState != state){
                _states.addLast(oldState)
            }
            refreshCurrentInput()
        }
    }

    fun onAction(action: String){
        if (notOverflow()) {
            val oldState = state
            val operationSymbol = when (action) {
                "+" -> OperationSymbol.PLUS
                "-" -> OperationSymbol.MINUS
                "*" -> OperationSymbol.MUL
                "/" -> OperationSymbol.DIV
                else -> error("")
            }
            state = transition(state, Event.Operation(operationSymbol))
            if (oldState != state) {
                _states.addLast(oldState)
            }
            refreshCurrentInput()
        }
    }

    fun onDot(){
        if (notOverflow()) {
            _states.addLast(state)
            state = transition(state, Event.Separator("."))
        }
        refreshCurrentInput()
    }

    fun onAC(){
        result = "0"
        currentInput = "0"
        state = State.Integer(currentNumber = "0")
        _states.clear()
    }

    fun onC(){
        if (!_states.isEmpty()){
            state = _states.removeLast()
        }
        refreshCurrentInput()
    }

    fun onEqual(){
        try {
            var expression = stateAsString(state)
            if (expression == ""){
                expression = "0"
            }
            val r = evaluate(expression)
            result = r.toString()
        } catch (e: ArithmeticException) {
            result = "Error"
        }
        catch (e: IllegalArgumentException) {
            result = "Error"
        }
        finally {
            state = State.Integer(currentNumber = "0")
            _states.clear()
            refreshCurrentInput()
        }
    }

    private fun notOverflow(): Boolean{
        return currentInput.length < MSX_INPUT_LENGTH
    }

    private fun refreshCurrentInput(){
        currentInput = stateAsString(state)
        if (currentInput == ""){
            currentInput = "0"
        }
    }
}