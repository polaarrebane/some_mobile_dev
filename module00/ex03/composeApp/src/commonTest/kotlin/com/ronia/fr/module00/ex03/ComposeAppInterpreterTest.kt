package com.ronia.fr.module00.ex03

import com.ronia.fr.module00.ex03.calculator.evaluate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ComposeAppInterpreterTest {

    @Test
    fun interpreterMustCalculate2plus2() {
        val result = evaluate("2+2")
        assertEquals(4.0, result)
    }

    @Test
    fun interpreterMustCalculate12times12() {
        val result = evaluate("12*12")
        assertEquals(144.0, result)
    }

    @Test
    fun interpreterMustCalculateConstant() {
        assertEquals(42.0, evaluate("42"))
        assertEquals(-42.0, evaluate("-42"))
        assertEquals(42.0, evaluate("42.0"))
        assertEquals(0.0, evaluate("0"))
        assertEquals(1234567.0, evaluate("1234567"))
    }

    @Test
    fun interpreterMustCalculateNegation() {
        val result = evaluate("-----42")
        assertEquals(-42.0, result)
    }

    @Test
    fun interpreterMustCalculate12div4() {
        val result = evaluate("12/4.0")
        assertEquals(3.0, result)
    }

    @Test
    fun interpreterMustNotDivideByZero() {
        assertFailsWith(
            exceptionClass = ArithmeticException::class,
            block = {
                evaluate("12/0")
            }
        )
    }

    @Test
    fun interpreterMustNotDoStrangeThings() {
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("12//0")
            }
        )
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("+0")
            }
        )
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("1++1")
            }
        )
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("1..0")
            }
        )
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("whut")
            }
        )
        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                evaluate("")
            }
        )
    }
}