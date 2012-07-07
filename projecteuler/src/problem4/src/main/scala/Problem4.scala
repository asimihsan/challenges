package com.euler.problem4.main

import scala.annotation.tailrec
import scala.math

object Problem4 {
    def isPalindrome(x: Int): Boolean = {
        val string = x.toString
        string.reverseIterator.sameElements(string.iterator)
    }

    /*
    def getPairs(minimum: Int, maximum: Int) = {
        def _pairs1(i: Int, j: Int, maximum: Int): Stream[(Int, Int)] = {
            if (i == maximum && j == maximum) Stream.empty
            else if (j == maximum) (i, j) #:: _pairs1(i + 1, 0, maximum)
            else (i, j) #:: _pairs1(i, j + 1, maximum)
        }

        def _pairs2(minimum: Int, maximum: Int) = {
            for (i <- minimum to maximum view;
                 j <- minimum to maximum view)
                 yield (i, j)
        }
        _pairs2(minimum, maximum)
    }
    */

    def getPairs(minimum: Int, maximum: Int) = {
        for (i <- minimum to maximum view;
             j <- minimum to maximum view)
             yield (i, j)
    }

    def getAnswer(numberOfDigits: Int): Int = {
        val maximum = math.pow(10, numberOfDigits).toInt
        val minimum = math.pow(10, numberOfDigits - 1).toInt
        val products = for {
                           pair <- getPairs(minimum, maximum)
                           product = pair match { case (i, j) => i * j } 
                           if isPalindrome(product)
                       } yield product
        products.par.max
    }

    def main(args: Array[String]) {
        val answer = getAnswer(4)
        println("Problem 4 answer: %s".format(answer))
    }
} // object Problem4

// vim: set ts=4 sw=4 et:
