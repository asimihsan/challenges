package com.euler.problem4.main

import scala.annotation.tailrec
import scala.math

object Problem5 {
    val primes = List(2, 3, 5, 7, 11, 13, 17, 19)
    def count(from: Int, step: Int): Iterator[Int] = {
        class Counter(from: Int, step: Int) extends Iterator[Int] {
            var count = from - step
            def hasNext = true
            def next = { count += step; count }
        }
        new Counter(from, step)
    }
    def divisible(i: Int, xs: List[Int]): Boolean = xs.forall(i % _ == 0)
    def getAnswer(max: Int): Int = {
        val xs: List[Int] = (primes.filter(_ <= max) :::
                             (1 to max).toList)
                            .distinct
                            .filter(max % _ != 0)
        val our_count: Iterator[Int] = count(from = max, step = max)
        our_count.find(divisible(_, xs)) match {
            case Some(i) => i
            case None    => -1
        }
    }

    def main(args: Array[String]) {
        val answer = getAnswer(20)
        println("Problem 5 answer: %s".format(answer))
    }
} // object Problem2

// vim: set ts=4 sw=4 et:
