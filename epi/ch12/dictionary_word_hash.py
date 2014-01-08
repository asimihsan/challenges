#!/usr/bin/env python

# EPI Q12.1: design a hash function that is suitable for words in a
# dictionary.
#
# Let's assume all words are lower-case. Note that all the ASCII
# character codes are adjacent, and yet we want a function that
# uniformly distributes over the space of a 32 bit signed integer.
#
# We can't simply use a cryptographic hash function because these are
# unacceptably slow. We could use CRC32, and it might be decent, but
# what is more intuitive?

import operator
import string


class DictionaryWord(object):
    __slots__ = ('value', 'primes')

    primes = {'a': 2, 'b': 3, 'c': 5, 'd': 7, 'e': 11, 'f': 13, 'g': 17,
              'h': 19, 'i': 23, 'j': 29, 'k': 31, 'l': 37, 'm': 41,
              'n': 43, 'o': 47, 'p': 53, 'q': 59, 'r': 61, 's': 67,
              't': 71, 'u': 73, 'v': 79, 'w': 83, 'x': 89, 'y': 97,
              'z': 101}

    def __init__(self, value):
        self.value = value

    def __hash__(self):
        """A hash function must be consistent with equals (any two
        objects which are equal must have the same hash code). A
        hash function should ideally uniformly distribute its output
        as a signed integer; Python chops off the sign bit and
        does the modulus into the dictionary for us.
        """
        # With Douglas Adams on our side this takes longer than a
        # minute. Ouch!
        #return 42

        # I know summing the chars is bad, but how bad? 19s!
        #return sum(ord(x) for x in self.value)

        # How about assigning a prime number per letter, then returning
        # the product? Takes 3.5s.
        #return reduce(operator.mul, (self.primes[x] for x in self.value))

        # What about the product of chars? 3.4s! Better than primes!
        #return reduce(operator.mul, (ord(x) for x in self.value))

        # How about a Java hashCode approach? Sum chars but mult
        # by Mersenne prime each time. 3.0s!
        # Likely better because more peturbation on single char
        # changes.
        result = 17
        for x in self.value:
            result = 31 * result + ord(x)
        return result

        # With the system default it takes 2.2s
        #return hash(self.value)


if __name__ == "__main__":
    lookup = set()
    with open("/usr/share/dict/words") as f_in:
        lines = (line.strip() for line in f_in
                 if all(elem in string.ascii_lowercase for elem in line.strip()))
        for line in lines:
            lookup.add(DictionaryWord(line))
