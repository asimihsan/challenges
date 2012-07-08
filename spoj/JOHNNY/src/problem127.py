#!/usr/bin/env python -u

import os
import sys

def get_weights():
    number_of_weights = int(sys.stdin.readline().strip())
    weights = []
    for i in xrange(number_of_weights):
        weights.append(int(sys.stdin.readline().strip()))
    return weights

def merge_lists(*input_lists):
    rv = set()
    for input_list in input_lists:
        rv.update(input_list)
    return sorted(list(rv))

def trim_list(input_list, delta):
    m = len(input_list)
    rv = [input_list[0]]
    last = input_list[0]
    threshold = 1 + delta
    for element in input_list[1:]:
        if element > last * threshold:
            rv.append(element)
            last = element
    return rv

def approximate_subset_sum(elements, t, epsilon, return_sub_lists=False):
    n = len(elements)
    delta = epsilon / (2 * n)
    sub_lists = [[0]]
    for i in xrange(1, n+1):
        sub_list = sub_lists[i-1]
        x = elements[i-1]
        bumped_sub_list = [sub_list_elem + x for sub_list_elem in sub_list]
        l_i = merge_lists(sub_list, bumped_sub_list)
        l_i = trim_list(l_i, delta)
        l_i = [elem for elem in l_i if elem <= t]
        sub_lists.append(l_i)
    z_star = max(sub_lists[-1])
    if return_sub_lists:
        return (z_star, sub_lists)
    else:
        return z_star

def approximate_subset_sum_and_respective_elements(elements, t, epsilon):
    (actual_z_star, sub_lists) = approximate_subset_sum(elements, t, epsilon, return_sub_lists = True)
    sub_list = sub_lists[-1][1:-1]
    positions = sorted([elements.index(elem) for elem in sub_list])
    return (actual_z_star, positions)

def main():
    weights = get_weights()
    total_weight = sum(weights)
    best_weight = total_weight / 2
    epsilon = 2 * len(weights) - 1
    (approximate_subset_sum, respective_element_indices) = \
        approximate_subset_sum_and_respective_elements(weights, best_weight, epsilon)
    for index in sorted(respective_element_indices):
        sys.stdout.write("%s\n" % (index + 1, ))
        sys.stdout.flush()

if __name__ == "__main__":
    main()
