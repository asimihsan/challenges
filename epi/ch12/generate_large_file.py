#!/usr/bin/env pypy

"""Generate a large file of lines of varying length, unsorted."""

import random
import string

with open('large_file.txt', 'w') as f_out:
    for i in xrange(1000000):
        length = random.randint(1, 78)
        line = [random.choice(string.ascii_lowercase) for i in xrange(length)]
        f_out.write("%s\n" % ''.join(line))
