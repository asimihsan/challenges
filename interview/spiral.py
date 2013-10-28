#!/usr/bin/env python

"""
Write a function that accepts four arguments. The first two arguments are the
size of the grid (h x w), filled with ascending integers from left to right,
top to bottom, starting from 1. The next two arguments are is the starting
positions, the row (r) and column (c).

Return an array of integers obtained by spiraling outward anti-clockwise from
the r and c, starting upward.

f(5, 5, 3, 3)

// Should return:
// [ 13, 8, 7, 12, 17, 18, 19,
// 194, 9, 4, 3, 2, 1, 6,
// 11, 16, 21, 22, 23, 24,
// 25, 20, 15, 10, 5 ]

f(2, 4, 1, 2) // [ 2, 1, 5, 6, 7, 3, 8, 4 ]
"""

from __future__ import division

import math


def f(h, w, r, c):
    get_value = lambda x: w * (x[0] - 1) + c
    next_direction = {
        'up': 'left',
        'left': 'down',
        'down': 'right',
        'right': 'up',
    }

    def get_new_location(cnt, direction, curr):
        if direction == 'up':
            return (curr[0] - cnt, curr[1])
        elif direction == 'left':
            return (curr[0], curr[1] - cnt)
        elif direction == 'down':
            return (curr[0] + cnt, curr[1])
        else:
            return (curr[0], curr[1] + cnt)

    if c <= math.ceil(w / 2):
        end = (1, w)
    else:
        end = (h, 1)
    curr = (r, c)
    cnt = 0
    direction = 'up'
    return_value = []
    while curr != end:
        print curr
        if curr[0] >= 1 and curr[0] < w or curr[1] >= 1 and curr[1] < h:
            return_value.append(get_value(curr))
        if direction == 'down' or direction == 'up':
            cnt += 1
        curr = get_new_location(cnt, direction, curr)
        direction = next_direction[direction]
        import ipdb; ipdb.set_trace()
    return_value.append(get_value(curr))

print f(2, 4, 1, 2)
