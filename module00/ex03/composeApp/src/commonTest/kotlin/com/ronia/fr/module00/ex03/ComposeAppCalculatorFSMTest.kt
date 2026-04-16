package com.ronia.fr.module00.ex03

import com.ronia.fr.module00.ex03.calculator.Event
import kotlin.test.Test
import kotlin.test.assertEquals
import com.ronia.fr.module00.ex03.calculator.State
import com.ronia.fr.module00.ex03.calculator.stateAsString
import com.ronia.fr.module00.ex03.calculator.transition
import com.ronia.fr.module00.ex03.calculator.MAX_DIGITS
import com.ronia.fr.module00.ex03.calculator.OperationSymbol

class ComposeAppCalculatorFSMTest {

    @Test
    fun calculatorMustAllowEnteringInteger() {
        var state: State = State.Initial

        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(2))
        state = transition(state, Event.Digit(3))
        state = transition(state, Event.Digit(4))
        state = transition(state, Event.Digit(5))

        assertEquals("12345", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringLeadingZeros() {
        var state: State = State.Initial

        state = transition(state, Event.Digit(0))
        assertEquals("", stateAsString(state))

        state = transition(state, Event.Digit(0))
        state = transition(state, Event.Digit(1))
        assertEquals("1", stateAsString(state))

        state = State.Integer(currentNumber = "0")
        state = transition(state, Event.Digit(0))
        assertEquals("0", stateAsString(state))

        state = State.Integer(currentNumber = "0")
        state = transition(state, Event.Digit(5))
        assertEquals("5", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringLongIntegers(){
        var expectedString = ""
        for (i in 1..MAX_DIGITS ){
            expectedString += "1"
        }

        var state: State = State.Initial
        for (i in 1..MAX_DIGITS+5 ){
            state = transition(state, Event.Digit(1))
        }

        assertEquals(expectedString, stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringReal() {
        var state: State = State.Initial
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(2))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Digit(4))
        state = transition(state, Event.Digit(5))

        assertEquals("12.45", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringLeadingSeparator() {
        var state: State = State.Initial
        state = transition(state, Event.Separator("."))
        assertEquals("", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringTrailingSeparator() {
        var state: State = State.Initial
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Operation(OperationSymbol.PLUS))
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        assertEquals("1.", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringLeadingMinus() {
        var state: State = State.Initial
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        assertEquals("-", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringLeadingOperation() {
        var state: State = State.Initial
        state = transition(state, Event.Operation(OperationSymbol.PLUS))
        assertEquals("", stateAsString(state))
        state = transition(state, Event.Operation(OperationSymbol.MUL))
        assertEquals("", stateAsString(state))
        state = transition(state, Event.Operation(OperationSymbol.DIV))
        assertEquals("", stateAsString(state))
    }

    @Test
    fun calculatorMustNotAllowEnteringMultipleSeparator() {
        var state: State = State.Initial
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(2))
        assertEquals("1.12", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringPlus(){
        var state: State = State.Integer("123")
        state = transition(state, Event.Operation(OperationSymbol.PLUS))
        assertEquals("123+", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringMinus(){
        var state: State = State.Initial
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Digit(4))
        state = transition(state, Event.Digit(2))
        assertEquals("-1.42", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringMul(){
        var state: State = State.Integer("123")
        state = transition(state, Event.Operation(OperationSymbol.MUL))
        assertEquals("123*", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringDiv(){
        var state: State = State.Integer("123")
        state = transition(state, Event.Operation(OperationSymbol.DIV))
        assertEquals("123/", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowChangingOperationSymbol(){
        var state: State = State.Integer("123")
        state = transition(state, Event.Operation(OperationSymbol.PLUS))

        state = transition(state, Event.Operation(OperationSymbol.DIV))
        assertEquals("123/", stateAsString(state))

        state = transition(state, Event.Operation(OperationSymbol.MUL))
        assertEquals("123*", stateAsString(state))

        state = transition(state, Event.Operation(OperationSymbol.PLUS))
        assertEquals("123+", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringNegation(){
        var state: State = State.Integer("123")
        state = transition(state, Event.Operation(OperationSymbol.PLUS))
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(2))
        assertEquals("123+-12", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringLeadingManyMinuses() {
        var state: State = State.Initial
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(1))
        assertEquals("1---11", stateAsString(state))
    }

    @Test
    fun calculatorMustAllowEnteringExpressions(){
        var state: State = State.Initial
        state = transition(state, Event.Digit(0))
        state = transition(state, Event.Digit(1))
        state = transition(state, Event.Digit(2))
        state = transition(state, Event.Digit(3))
        state = transition(state, Event.Digit(0))
        state = transition(state,  Event.Operation(OperationSymbol.PLUS))
        state = transition(state, Event.Digit(5))
        state = transition(state, Event.Digit(7))
        state = transition(state,  Event.Operation(OperationSymbol.PLUS))
        state = transition(state,  Event.Operation(OperationSymbol.MINUS))
        state = transition(state, Event.Digit(8))
        state = transition(state, Event.Digit(1))
        state = transition(state,  Event.Operation(OperationSymbol.DIV))
        state = transition(state,  Event.Operation(OperationSymbol.MUL))
        state = transition(state, Event.Digit(4))
        state = transition(state, Event.Separator("."))
        state = transition(state, Event.Digit(2))
        state = transition(state,  Event.Operation(OperationSymbol.DIV))
        state = transition(state, Event.Digit(0))

        assertEquals("1230+57+-81*4.2/0", stateAsString(state))
    }
}