package com.euler.problem4.test

import com.euler.problem4.main.Problem5

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class Problem5Suite extends FunSuite with BeforeAndAfter {
    test("count works") { assert( Problem5.count(10, 10).take(5).toList === List(10, 20, 30, 40, 50) ) }
    test("smallest number divisible 1->10") {
        assert(Problem5.getAnswer(10) === 2520)
    }
}

// vim: set ts=4 sw=4 et:
