#!/usr/bin/env python -u

# ----------------------------------------------------------------------------
#   Reference: ftp://ftp.inf.ethz.ch/pub/crypto/publications/Przyda02.pdf
# ----------------------------------------------------------------------------

import os
import sys
import random

def rgli_trial(elements, B, x_best):
    xs = []

    # ------------------------------------------------------------------------
    #   First phase: randomized selection.
    # ------------------------------------------------------------------------
    elements_random = elements[:]
    random.shuffle(elements_random)
    for element in elements_random:
        if element <= abs(sum(xs) - B):
            xs.append(element)
    # ------------------------------------------------------------------------

    # ------------------------------------------------------------------------
    #   Second phase: local improvement.
    # ------------------------------------------------------------------------
    for i in xrange(len(xs)):
        if abs(sum(xs) - B):
            break
        xs_not_in_solution = [elem for elem in elements if elem not in xs]
        xs_not_in_solution.sort(reverse=True)
        for x_alt in xs_not_in_solution:
            xs_alt = xs[:]
            xs_alt[i] = x_alt
            if abs(sum(xs_alt) - B) < abs(sum(xs) - B):
                xs[i] = xs_alt
                break
    # ------------------------------------------------------------------------

    # ------------------------------------------------------------------------
    #   x_best update
    # ------------------------------------------------------------------------
    delta_xs = abs(sum(xs) - B)
    delta_x_best = abs(sum(x_best) - B)
    if delta_xs < delta_x_best:
        x_best = xs
    # ------------------------------------------------------------------------

    return (xs, x_best)

def rgli(elements, B, number_of_trials):
    x_best = []
    for i in xrange(number_of_trials):
        (_, x_best) = rgli_trial(elements, B, x_best)
    return x_best

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
    number_of_trials = 5
    best_subset = rgli(weights, best_weight, number_of_trials)

    lookup = dict([(weight, i) for (i, weight) in enumerate(weights)])
    for element in best_subset:
        sys.stdout.write("%s\n" % (lookup[element] + 1, ))
        sys.stdout.flush()

if __name__ == "__main__":
    main()
