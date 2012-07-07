package com.euler.problem2.test

import com.euler.problem2.main.Problem2

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class Problem2Suite extends FunSuite with BeforeAndAfter {

    var problem2: Problem2 = _

    before {
        problem2 = new Problem2
    }

    test("first 10 Fibonnaci numbers sum") {
        val sum = problem2.getAnswer(limit = 100)
        val terms = List(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
        val actual_sum = terms.filter(x => x % 2 == 0).sum
        assert(sum === actual_sum)
    }
}

// vim: set ts=4 sw=4 et:
