package com.euler.problem1.main

object Problem1 {
    def sumNaturalNumbers(limit: Int) = {
        val numbers = for {
            i <- 1 until limit
            if ((i % 3 == 0) || (i % 5 == 0))
        } yield i
        numbers.sum
    } // sumNaturalNumbers

    def main(args: Array[String]) = {
        val answer = Problem1.sumNaturalNumbers(1000)
        println("Problem 1 answer: %s".format(answer))
    }
} // class Problem1

// vim: set ts=4 sw=4 et:
