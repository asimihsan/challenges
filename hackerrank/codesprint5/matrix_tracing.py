"""
Given below is a words from the English dictionary arranged as a matrix

MATHE
ATHEM
THEMA
HEMAT
EMATI
MATIC
ATICS

Tracing the matrix is starting from the top left position and at each
step move either RIGHT or DOWN, to reach the bottom right of the matrix.
It is assured that any such tracing generates the same word. How many
such tracings can be possible for a given word of length m+n-1 written
as a matrix of size m * n?

## Input Format

The first line of input contains an integer T. T test cases follow in
each line.

Each line contains 2 space separated integers m & n indicating that the
matrix written has m rows and each row has n characters.

## Constraints

1 <= T <= 10 ** 3
1 <= m,n <= 10 ** 6

## Output Format

Print the number of ways (S) the word can be traced as explained in the
problem statement. If the number is larger than 109+7, print S mod (10^9
+ 7) for each testcase in a newline.

## Sample Input

    1
    2 3

## Sample Output

    3
"""

import math
import sys


def multiplicity(n, p):
    """Return the power of the prime number p in the
    factorization of n!"""
    if p > n:
        return 0
    if p > n // 2:
        return 1
    q, m = n, 0
    while q >= p:
        q //= p
        m += q
    return m


def _primes(n):
    """Generate a list of the prime numbers [2, 3, ... m] where
    m is the largest prime <= n."""
    n = n + 1
    sieve = range(n)
    sieve[:2] = [0, 0]
    for i in xrange(2, int(math.sqrt(n)) + 1):
        if sieve[i]:
            for j in xrange(i ** 2, n, i):
                sieve[j] = 0
    # Filter out the composites, which have been replaced by 0's
    return [p for p in sieve if p]

_all_primes = _primes(2000000)


def primes(n):
    return [p for p in _all_primes if p and p <= n]


def powproduct(ns, mod=10 ** 9 + 7):
    """Compute the explicit value of a factored integer
    given as a list of (base, exponent) pairs."""
    if not ns:
        return 1
    units = 1
    multi = {}
    for base, exp in ns.iteritems():
        if exp == 1:
            units *= base
            units %= mod
        else:
            if exp % 2:
                units *= base
                units %= mod
            multi[base] = exp // 2
    return units * powproduct(multi) ** 2


def get_prime_multiplicities(n):
    return_value = {}
    for p in primes(n):
        mult = multiplicity(n, p)
        if mult > 0:
            return_value[p] = mult
    return return_value


def multiply_prime_multiplicities(first, second):
    for (prime, mult) in second.iteritems():
        first[prime] = first.get(prime, 0) + mult
    return first


def divide_prime_multiplicities(first, second):
    for (prime, mult) in second.copy().iteritems():
        if prime not in first:
            continue
        if first[prime] == second[prime]:
            del first[prime]
            del second[prime]
        elif first[prime] > second[prime]:
            first[prime] -= mult
            del second[prime]
        else:
            second[prime] -= first[prime]
            del first[prime]
    return (first, second)


def factorial(numerators, denomenators):
    ns = {}
    for n in numerators:
        pms = get_prime_multiplicities(n)
        ns = multiply_prime_multiplicities(ns, pms)
    ds = {}
    for d in denomenators:
        pms = get_prime_multiplicities(d)
        ds = multiply_prime_multiplicities(ds, pms)
    (ns, ds) = divide_prime_multiplicities(ns, ds)
    n = powproduct(ns)
    d = powproduct(ds)
    return n // d


def solve(r, c):
    """In order to reach the bottom-right cell of an r x c grid, using
    only right and down, note that one possible solution is to go down
    by (r - 1) then right by (c - 1). Then note that the answer is
    actually the number of permutations of this result.

    For example for r = 2, c = 3, one possible answer is:

        DRR

    and hence the answer is all possible permutations of this string,
    i.e.

        3! / (2! * 1!) = 3
    """
    result = factorial([r + c - 2], [r - 1, c - 1])
    print(result % (10 ** 9 + 7))


def main():
    lines = (line.strip() for line in sys.stdin)
    split_lines = (map(int, line.split()) for line in lines
                   if len(line.split()) > 1)
    map(lambda x: solve(x[0], x[1]), split_lines)

if __name__ == "__main__":
    sys.exit(main())
