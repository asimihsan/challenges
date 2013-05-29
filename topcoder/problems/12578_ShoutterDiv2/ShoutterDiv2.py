#!/usr/bin/env python

from __future__ import division

import collections
import itertools


Interval = collections.namedtuple("Interval", ["start", "end", "index"])
Tree = collections.namedtuple("Tree", ["left", "root", "right", "root_median"])


class HashableInterval(Interval):
    def __eq__(self, other):
        if not isinstance(other, HashableInterval):
            return False
        return self.index == other.index

    def __hash__(self):
        return hash(self.index)

    def __cmp__(self, other):
        return cmp(self.index, other.index)

    def is_left_of_point(self, point):
        return (self.start < point) and (self.end < point)

    def is_right_of_point(self, point):
        return (self.start > point) and (self.end > point)


def do_intervals_overlap(first, second):
    return (second.start <= first.end) and (second.end >= first.start)


def median(elements):
    length = len(elements)
    half_length = int(length / 2)
    if length % 2 == 0:
        return (elements[half_length - 1] + elements[half_length]) / 2
    else:
        return elements[half_length]


def count(s, t):
    return count_naive(s, t)
    #return count_interval_tree(s, t)


def count_interval_tree(s, t):
    intervals = [HashableInterval(start=a, end=b, index=i)
                 for (i, (a, b)) in enumerate(zip(s, t))]
    root = Tree(left=[], root=intervals, right=[],
                root_median=median_of_intervals(intervals))
    tree = get_tree(root)
    import ipdb; ipdb.set_trace()
    pass


def median_of_intervals(intervals):
    xs = sorted([i.start for i in intervals] + [i.end for i in intervals])
    return median(xs)


def get_tree(tree):
    left = []
    root = []
    right = []
    for interval in tree.root:
        if interval.is_left_of_point(tree.root_median):
            left.append(interval)
        elif interval.is_right_of_point(tree.root_median):
            right.append(interval)
        else:
            root.append(interval)
    if len(left) > 0:
        left = get_tree(Tree(left=[], root=left, right=[],
                             root_median=median_of_intervals(left)))
    else:
        left = []
    if len(right) > 0:
        right = get_tree(Tree(left=[], root=right, right=[],
                              root_median=median_of_intervals(right)))
    else:
        right = []
    return Tree(left=left, root=root, right=right,
                root_median=median_of_intervals(root))


def count_naive(s, t):
    """O(n^2) approach where we go through each interval and compare it
    to each interval."""

    intervals = [HashableInterval(start=a, end=b, index=i)
                 for (i, (a, b)) in enumerate(zip(s, t))]
    overlapping_intervals = set()
    for (i1, i2) in itertools.product(intervals, repeat=2):
        if i1 == i2:
            continue
        if do_intervals_overlap(i1, i2):
            if i1 < i2:
                overlapping_intervals.add((i1, i2))
            else:
                overlapping_intervals.add((i2, i1))
    return len(overlapping_intervals)


def test_median():
    from nose.tools import eq_
    eq_(median([0]), 0)
    eq_(median([1, 2, 3, 4]), 2.5)
    eq_(median([1, 2, 3, 4, 5]), 3)


def test_do_intervals_overlap():
    from nose.tools import eq_
    i1 = Interval(start=1, end=3, index=0)
    i2 = Interval(start=2, end=4, index=1)
    i3 = Interval(start=4, end=6, index=2)
    eq_(do_intervals_overlap(i1, i2), True)
    eq_(do_intervals_overlap(i1, i3), False)
    eq_(do_intervals_overlap(i2, i3), True)


def test_count():
    from nose.tools import eq_
    eq_(count([1, 2, 4], [3, 4, 6]), 2)
    eq_(count([0, 0, 0], [1, 1, 1]), 3)

if __name__ == "__main__":
    test_count()
