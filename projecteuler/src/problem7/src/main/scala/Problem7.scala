package com.euler.problem7.main

import scala.annotation.tailrec
import scala.math
import scala.collection.mutable.ListBuffer

object Problem7 {
    def primeNumbers: Iterator[Int] = {
        class Counter(start: Int, step: Int) extends Iterator[Int] {
            var count = start - step
            val hasNext = true
            def next = { count += step; count }
        }

        class PrimeNumberIterator extends Iterator[Int] {
            def not_divisible(i: Int, xs: ListBuffer[Int]): Boolean = xs.forall(i % _ != 0)
            var primes = ListBuffer(2)
            val hasNext = true
            def next = {
                var count = new Counter(start = primes.last + 1, step = 1)
                count.find(not_divisible(_, primes)) match {
                    case Some(i) => { primes += i; i }
                    case _       => -1
                }
            }
        }
        new PrimeNumberIterator
    }

    def getElementAtIndex(iterable: Iterator[Int], index: Int): Int = {
        @tailrec
        def _getElementAtIndex(iterable: Iterator[Int], index: Int, acc: Int): Int = {
            if    (acc == (index - 1)) iterable.next()
            else  { iterable.next(); _getElementAtIndex(iterable, index, acc + 1) }
        }
        _getElementAtIndex(iterable, index, 0)
    }

    def getAnswer(index: Int): Int = {
        getElementAtIndex(primeNumbers, index - 1)
    }

    def main(args: Array[String]) {
        val answer = getAnswer(10001)
        println("Problem 7 answer: %s".format(answer))
    }
} // object Problem7

// vim: set ts=4 sw=4 et:
