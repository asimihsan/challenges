package com.euler.problem4.test

import com.euler.problem4.main.Problem4

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class Problem4Suite extends FunSuite with BeforeAndAfter {
    test("isPalindrome identifies palindromes") {
        val palindrome = 9009
        assert(Problem4.isPalindrome(palindrome))
    }

    test("isPalindrome rejects non-palindromes") {
        val nonPalindrome = 9010
        assert(!Problem4.isPalindrome(nonPalindrome))
    }

    test("largest palindrome from product of two 2-digit numbers is 9009") {
        assert(Problem4.getAnswer(2) === 9009)
    }
}

// vim: set ts=4 sw=4 et:
