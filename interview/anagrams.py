#!/usr/bin/env python

import collections
import pprint

lookup = collections.defaultdict(list)
with open("anagrams.txt") as f_in:
    lines = (line.strip() for line in f_in)
    for line in lines:
        key = ''.join(sorted(line))
        lookup[key].append(line)
pprint.pprint(lookup.values())
