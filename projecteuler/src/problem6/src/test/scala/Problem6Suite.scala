package com.euler.problem4.test

import com.euler.problem4.main.Problem6

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class Problem6Suite extends FunSuite with BeforeAndAfter {
    test("sum of squares -> 10") { assert(Problem6.sumOfSquares(10) === 385) }
    test("square of sum -> 10") { assert(Problem6.squareOfSum(10) === 3025) }
    test("answer for 10 = 2640") { assert(Problem6.getAnswer(10) === 2640) }
}

// vim: set ts=4 sw=4 et:
