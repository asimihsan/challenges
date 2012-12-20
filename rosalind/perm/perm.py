
def permutations(size, acc=None, current_integer=None):
    if acc is None:
        acc = range(1, size+1)
    if len(acc) == 1:
        return [acc]
    else:
        result = []
        for i, integer in enumerate(acc):
            current_integer = integer
            newacc = acc[0:i] + acc[i+1:]
            subperms = permutations(size, newacc, current_integer)
            for subperm in subperms:
                result.append([current_integer] + subperm)
        return result

def permutations2(size, elements=None):
    if elements is None:
        elements = range(1, size+1)
    if len(elements) <= 1:
        yield elements
    else:
        for perm in permutations2(size, elements[1:]):
            for i in xrange(len(elements)):
                yield perm[:i] + elements[0:1] + perm[i:]

if __name__ == "__main__":
    integer = 5
    results = [elem for elem in permutations2(integer)]
    results.sort()
    print len(results)
    for result in results:
        print ' '.join([str(elem) for elem in result])


