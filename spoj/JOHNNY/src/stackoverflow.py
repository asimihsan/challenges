#!/usr/bin/env python

'''
http://stackoverflow.com/questions/3420937/algorithm-to-find-which-number-in-a-list-sum-up-to-a-certain-number

'''

import os
import sys

def stackoverflow(x_list, target):
    memo = dict()
    result, _ = g(x_list, x_list, target, memo)
    return (sum(result), result)

def g(v, w, S, memo):
    subset = []
    id_subset = []
    for i, (x, y) in enumerate(zip(v, w)):
        # Check if there is still a solution if we include v[i]
        if f(v, i + 1, S - x, memo) > 0:
            subset.append(x)
            id_subset.append(y)
            S -= x
    return subset, id_subset

def f(v, i, S, memo):
    if i >= len(v):
        return 1 if S == 0 else 0
    if (i, S) not in memo:    # <-- Check if value has not been calculated.
        count = f(v, i + 1, S, memo)
        count += f(v, i + 1, S - v[i], memo)
        memo[(i, S)] = count  # <-- Memoize calculated result.
    return memo[(i, S)]       # <-- Return memoized value.

def get_weights():
    number_of_weights = int(sys.stdin.readline().strip())
    weights = []
    for i in xrange(number_of_weights):
        weights.append(int(sys.stdin.readline().strip()))
    return weights

def main():
    weights = get_weights()
    total_weight = sum(weights)
    best_weight = total_weight / 2
    subset = stackoverflow(weights, best_weight)
    for element in subset:
        sys.stdout.write("%s\n" % (weights.index(element), ))
        sys.stdout.flush()

if __name__ == "__main__":
    main()
