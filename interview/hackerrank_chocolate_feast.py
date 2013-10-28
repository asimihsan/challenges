import sys


def count(N, C, M):
    chocolates = N // C
    wrappers = chocolates
    while wrappers >= M:
        more = wrappers // M
        wrappers = wrappers % M + more
        chocolates += more
    return chocolates

sys.stdin.readline()

lines = (map(int, line.strip().split()) for line in sys.stdin
         if len(line.strip()) != 0)
for line in lines:
    print(count(*line))
