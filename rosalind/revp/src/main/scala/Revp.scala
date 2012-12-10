package com.rosalind.revp.main

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object Revp {
  // Note: these are Strings, not Chars. Hence
  // StringBuilder needs ++=, not +=.
  private val MAPPING = Map("A" -> "T",
                            "T" -> "A",
                            "G" -> "C",
                            "C" -> "G")
  private val minimum_size = 4;
  private val maximum_size = 12;

  def print_reverse_palindrome_result(result: Array[(Int, Int)]): Unit = {
    for (elem <- result) {
      val (i, j) = elem
      println(i + " " + j)
    }
  }

  def main(args: Array[String]) {
    val revp = new Revp
    val result =
    revp.get_reverse_palindromes("GCGCAGCCGTTGAAGTTCATTGTGTGGAACGTCAAGCAGAATTTCAGTCTAACGATAGTGAAGACTGGACGAGAACCTAGGAGTTTTGAAATGCATGTCTGGGAGTTCCTGTATAGCAGTGCCCTGTTAGTCCTATGTCAGGCGGGGGTGGTACTCGCAGAGAAACTTGGGAGGGGTATTTCACTCCCGACAGCGTGACTGGGCAAGGTAGTTGACGCCCGGGCTTATTGGTGGAGTGTACGGAGGGCAGCCACGACTCCCCGACGGCCAATGCATGTATGCCATATGAAATCCAACGAATAAACAAGCAACTCTGCCAGCGGTAGACCTCATTTCTAGTCTAACTAAGGCTGTATGCAAAATCCCTACCAATTTCGCTTAAAGCTGGTTAGTCTGCGAAGGCCTTCGAGCTCCAAGGGACAAGTGAAACGCTACCTCAGGCAGGGCGCGGTGAGTCTAACTGCCTGCGATTTTACATACTCACGCGGAACCTGCGCAGGATGAATCGGTATACTCGTTATGAGGCTGTGGTTCGGGCTGCTTACTCGCCGTTCGAGCAGGAGCCCATAACTCGTTCCAGCTACATACCCGGCGCCCCTGGCTACCCGACAGGATAGAGGGTCATCGGTGCAACGCGTACGATACCCAAATATGTAAAGCCCTGACATACGGGTCTGGATCCTACGTGTGATAACAAATAATCGCAAATTACAAACGTTGTCGACGCAAGGTGGCATGAAGCCCACTAGAACCCAGACCAGTAAAGGTACGTTGCTACCCCAATTGTGACCTTGCCTGCGCGCG")
    print_reverse_palindrome_result(result)
  } // main
}

class Revp {
  @tailrec
  final def get_reverse_complement(string: String,
                             complemented: StringBuilder = new StringBuilder): String = {
    if (string.size == 0) complemented.reverse.toString()
    else {
      complemented ++= Revp.MAPPING(string.substring(0, 1))
      get_reverse_complement(string.substring(1, string.size), complemented)
    }
  }
    
  def get_reverse_palindromes(string: String): Array[(Int, Int)] = {
    val return_value = ArrayBuffer[(Int, Int)]()
    for (i <- 0 to string.size - 1) {
      for (j <- i + 1 to string.size) {
        val substring = string.substring(i, j)
        val reverse_complemented_substring = get_reverse_complement(substring)
        if ((substring.size <= Revp.maximum_size) &&
            (substring.size >= Revp.minimum_size) && 
            (substring == reverse_complemented_substring)) {
          val element = (i+1, j-i)  // with respect to a 1-indexed string.
          return_value += element
        }
      }
    }
    return_value.toArray
  } // get_reverse_palindromes
}

// vim: set ts=4 sw=4 et:
