package com.euler.problem3.test

import com.euler.problem3.main.Problem3
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import scala.math.BigInt

class Problem3Suite extends FunSuite with BeforeAndAfter {

    var problem3: Problem3 = _
    before {
        problem3 = new Problem3
    }

    test("prime factors of 13195") {
        val input = BigInt(13195)
        val largestPrimeFactor = Problem3.primeFactors2(input)
        val numbers = List(29, 13, 7, 5)
        assert(largestPrimeFactor === numbers.max)
    }

    def compareSeqs(list1: Seq[_], list2: Seq[_]): Boolean = {
        (list1, list2).zipped.forall(_.equals(_))
    } // compareBigNumberLists
}

// vim: set ts=4 sw=4 et:
