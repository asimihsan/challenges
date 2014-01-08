#!/usr/bin/env pypy

# Q12.7: given a dictionary return a data structure that partitions
# the dictionary into subsets of words that are anagrams of one another.
#
# Assign a prime number per character. Products of anagrams are
# identical. Use a dictionary to map particular products to a list
# of anagrams. Relies heavily on Python's transparent support for
# arbitrary precision integer arithmetic.
#
# Book solution: sort the word by character before hashing. More
# elegant!

import operator
import pprint
import re

if __name__ == "__main__":
    primes = {'a': 2, 'b': 3, 'c': 5, 'd': 7, 'e': 11, 'f': 13, 'g': 17,
              'h': 19, 'i': 23, 'j': 29, 'k': 31, 'l': 37, 'm': 41,
              'n': 43, 'o': 47, 'p': 53, 'q': 59, 'r': 61, 's': 67,
              't': 71, 'u': 73, 'v': 79, 'w': 83, 'x': 89, 'y': 97,
              'z': 101}
    anagrams = {}
    with open("/usr/share/dict/words") as f_in:
        lines = (line.strip() for line in f_in)
        for line in lines:
            if re.match("^[a-z]+$", line):
                product = reduce(operator.mul, (primes[x] for x in line))
                if product not in anagrams:
                    anagrams[product] = []
                anagrams[product].append(line)
    lists = sorted(anagrams.values())

    # To debug it only print out anagram lists with at least two
    # anagrams
    pprint.pprint([x for x in lists if len(x) > 1])

    #pprint.pprint(lists)
