def permutations(input):
    if len(input) == 1:
        return [input]
    letter = input[0]
    return_value = []
    for permutation in permutations(input[1:]):
        elems = list(permutation)
        for i in xrange(len(elems)+1):
            temp = elems[:]
            temp.insert(i, letter)
            return_value.append(''.join(temp))
    return return_value


print permutations('ABC')
