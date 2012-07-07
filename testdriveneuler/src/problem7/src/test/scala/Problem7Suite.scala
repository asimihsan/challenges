package com.euler.problem7.test

import com.euler.problem7.main.Problem7

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class Problem7Suite extends FunSuite with BeforeAndAfter {
    test("getElementAtIndex") {
        val elems = List(1,2,3,4,5)
        assert(Problem7.getElementAtIndex(elems iterator, 1) === 1)
        assert(Problem7.getElementAtIndex(elems iterator, 2) === 2)
        assert(Problem7.getElementAtIndex(elems iterator, 3) === 3)
        assert(Problem7.getElementAtIndex(elems iterator, 4) === 4)
        assert(Problem7.getElementAtIndex(elems iterator, 5) === 5)
    }

    test("6th prime") { assert(Problem7.getAnswer(6) === 13) }
}

// vim: set ts=4 sw=4 et:
