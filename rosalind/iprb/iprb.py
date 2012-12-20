import itertools
import random
from decimal import Decimal
import numpy as np

def simulate(k, m, n, iterations):
    pool = []
    pool.extend(["k"] * k)
    pool.extend(["m"] * m)
    pool.extend(["n"] * n)

    probability_lookup = {
        ("k", "k"): 1,
        ("k", "m"): 1,
        ("k", "n"): 1,
        ("m", "k"): 1,
        ("m", "m"): 0.75,
        ("m", "n"): 0.5,
        ("n", "k"): 1,
        ("n", "m"): 0.5,
        ("n", "n"): 0,
    }

    number_of_dominants = 0
    for i in xrange(iterations):
        (a, b) = random.sample(pool, 2)
        probability = probability_lookup[(a, b)]
        if probability == 1:
            number_of_dominants += 1
        elif probability == 0:
            pass
        elif random.random() <= probability:
            number_of_dominants += 1
        else:
            pass
    return Decimal(number_of_dominants) / Decimal(iterations)

def calculate(k, m, n):
    counts = np.matrix([[float(k), float(m), float(n)]])
    probs = counts / sum([k, m, n])
    strings = [str(elem) for elem in [k, m, n]]
    subcounts_string = "; ".join([" ".join(strings)] * 3)
    subcounts = np.matrix(subcounts_string) - np.eye(3)
    subprobs = subcounts / (sum([k, m, n]) - 1)

    dominant = np.matrix([[1.0, 1.0, 1.0],
                         [1.0, 0.75, 0.5],
                         [1.0, 0.5, 0.0]])
    result1 = (subprobs * dominant).sum(axis=1)
    result2 = result1 * probs
    import ipdb; ipdb.set_trace()

if __name__ == "__main__":
    # = 0.65067369
    print simulate(k=16, m=25, n=29,
                   iterations=100000000)
    #calculate(2, 2, 2)


