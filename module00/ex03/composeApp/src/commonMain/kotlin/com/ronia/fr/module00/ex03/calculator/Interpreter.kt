package com.ronia.fr.module00.ex03.calculator

sealed class Token {
    data class Num(val value: Double, val raw: String) : Token()
    data class Op(val op: Char) : Token()
}

class Lexer(private val input: String) {
    private var pos = 0

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        while (pos < input.length) {
            when {
                input[pos].isWhitespace() -> pos++
                input[pos] in "+-*/" -> {
                    val ch = input[pos]
                    val prev = tokens.lastOrNull()
                    if (ch == '-' && (prev == null || prev is Token.Op)) {
                        val num = readSignedNumber()
                        tokens.add(num)
                    } else {
                        tokens.add(Token.Op(ch))
                        pos++
                    }
                }
                input[pos].isDigit() || input[pos] == '.' -> tokens.add(readNumber())
                else -> throw IllegalArgumentException("'${input[pos]}'")
            }
        }
        return tokens
    }

    private fun readSignedNumber(): Token.Num {
        var sign = 1
        while (pos < input.length && (input[pos] == '+' || input[pos] == '-')) {
            if (input[pos] == '-') sign *= -1
            pos++
        }
        val start = pos
        while (pos < input.length && (input[pos].isDigit() || input[pos] == '.')) pos++
        val digits = input.substring(start, pos)
        if (digits.isEmpty()) throw IllegalArgumentException("expected digit")
        val value = digits.toDouble() * sign
        val raw = (if (sign < 0) "-" else "") + digits
        return Token.Num(value, raw)
    }

    private fun readNumber(): Token.Num {
        val start = pos
        while (pos < input.length && (input[pos].isDigit() || input[pos] == '.')) pos++
        val raw = input.substring(start, pos)
        return Token.Num(raw.toDouble(), raw)
    }
}

class Parser(private val tokens: List<Token>) {
    private var pos = 0

    fun parse(): Double = parseExpr()

    private fun parseExpr(): Double {
        var result = parseTerm()
        while (pos < tokens.size && tokens[pos] is Token.Op) {
            val op = (tokens[pos] as Token.Op).op
            if (op != '+' && op != '-') break
            pos++
            val right = parseTerm()
            result = if (op == '+') result + right else result - right
        }
        return result
    }

    private fun parseTerm(): Double {
        var result = parseFactor()
        while (pos < tokens.size && tokens[pos] is Token.Op) {
            val op = (tokens[pos] as Token.Op).op
            if (op != '*' && op != '/') break
            pos++
            val right = parseFactor()
            result = if (op == '*') result * right
            else {
                if (right == 0.0) throw ArithmeticException("Division by zero")
                result / right
            }
        }
        return result
    }

    private fun parseFactor(): Double {
        val tok = tokens.getOrNull(pos)
            ?: throw IllegalArgumentException("")
        if (tok !is Token.Num)
            throw IllegalArgumentException("")
        pos++
        return tok.value
    }
}

fun evaluate(expression: String): Double {
    val tokens = Lexer(expression).tokenize()
    return Parser(tokens).parse()
}

