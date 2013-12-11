def anagrams(arr, input_string):
    """What is an anagram? A string that is a permutation of another.
    
    Rather than exhaustively generate all permuatations of input_string
    and comparse them to arr, use a small dictionary to count letters
    per word in arr vs. input_string.
    """
    count = {}
    for char in input_string:
        count[char] = count.get(char, 0) + 1
    return_value = []
    for i, candidate in enumerate(arr):
        candidate_count = {}
        for char in candidate:
            candidate_count[char] = candidate_count.get(char, 0) + 1
        if count == candidate_count:
            return_value.append(i)
    return return_value
    
print anagrams(['fred', 'food', 'derf'], 'dref')
