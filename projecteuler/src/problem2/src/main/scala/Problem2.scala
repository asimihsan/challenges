package com.euler.problem2.main

import scala.annotation.tailrec

class Problem2 {
    var term_1 = 1
    var term_2 = 2

    def nextFibonacciTerm: Int = {
        val term_3 = term_1 + term_2
        term_1 = term_2
        term_2 = term_3
        term_2
    }

    def getFibonacciTerms(limit: Int): List[Int] = {
        val initial = List(term_1, term_2)
        val numbers = for {
            i <- 1 to limit - 2
            x = nextFibonacciTerm
        } yield x
        initial ++ numbers
    }

    def getAnswer(limit: Int): Int = {
        getAnswer(limit, term_2)
    }

    @tailrec final def getAnswer(limit: Int, acc: Int): Int = {
        nextFibonacciTerm
        if (term_2 > limit)
            return acc
        if (term_2 % 2 == 0)
            getAnswer(limit, acc + term_2)
        else
            getAnswer(limit, acc)
    }
}

object Problem2 {
    def main(args: Array[String]) {
        val problem2 = new Problem2
        val answer = problem2.getAnswer(limit = 4000000)
        println("Problem 2 answer: %s".format(answer))
    }
} // object Problem2

// vim: set ts=4 sw=4 et:
