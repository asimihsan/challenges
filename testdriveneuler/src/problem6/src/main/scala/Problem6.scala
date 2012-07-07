package com.euler.problem4.main

import scala.annotation.tailrec
import scala.math

object Problem6 {
    def sumOfSquares(limit: Int): Int = (1 to limit).map(i => i * i).sum
    def squareOfSum(limit: Int): Int = math.pow(1 to limit sum, 2).toInt
    def getAnswer(limit: Int): Int = squareOfSum(limit) - sumOfSquares(limit)

    def main(args: Array[String]) {
        val answer = getAnswer(100)
        println("Problem 6 answer: %s".format(answer))
    }
} // object Problem6

// vim: set ts=4 sw=4 et:
