package com.euler.problem1.test

import org.scalatest.FunSuite
import com.euler.problem1.main.Problem1

class Problem1Suite extends FunSuite {
    test("sum of natural numbers that are multiples of 3 and 5 up to 10 is 23") {
        val sum = Problem1.sumNaturalNumbers(10)
        assert(sum === 23)
    }
}

// vim: set ts=4 sw=4 et:
