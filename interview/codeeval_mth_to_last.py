"""
Write a program to determine the Mth to last element of a list.

The first argument will be a text file containing a series of space delimited
characters followed by an integer representing a index into the list (1
based), one per line. E.g.

a b c d 4
e f g h 2

Print to stdout, the Mth element from the end of the list, one per line. If
the index is larger than the list size, ignore that input. E.g.

a
g
"""

import sys
with open(sys.argv[1], 'r') as f_in:
    for test in f_in:
        elems = test.split()
        (chars, m) = (elems[:-1], int(elems[-1]))
        if m > len(chars):
            continue
        print chars[-m]
