from pprint import pprint
import operator
import itertools

def build_suffix_array(string):
    result = []
    for i in xrange(len(string)):
        result.append(string[i:])
    return result

def lcp(*strings):
    return_value = []
    for letters in itertools.izip(*strings):
        if len(set(letters)) != 1:
            break
        return_value.append(letters[0])
    return ''.join(return_value)

def longest_common_substring(strings):
    # -------------------------------------------------------------------------
    #   Build a suffix array for each string. Then merge the suffixes into a
    #   dictionary that indicates what string they came from.
    # -------------------------------------------------------------------------
    suffix_array = {}
    for i, string in enumerate(strings):
        string_suffix_array = build_suffix_array(string)
        for suffix_array_entry in string_suffix_array:
            if suffix_array_entry not in suffix_array:
                suffix_array[suffix_array_entry] = set()
            suffix_array[suffix_array_entry].add(i)
    # -------------------------------------------------------------------------

    # -------------------------------------------------------------------------
    #   Transform the suffix_array dictionary into a sorted suffix array,
    #   using tuples to retain which strings they came from.
    # -------------------------------------------------------------------------
    sorted_suffix_array = sorted(suffix_array.items(), key=operator.itemgetter(0))
    # -------------------------------------------------------------------------

    # -------------------------------------------------------------------------
    #   Every substring for a given string appears somewhere as a prefix
    #   in its suffix array. (Take a breath and take this in!)
    #
    #   Look for adjacent blocks of suffixes such that a block contains
    #   suffixes in all strings. Whilst building up this contiguous block
    #   if we encounter a longer suffix for a given string make a note of it.
    # -------------------------------------------------------------------------
    useful_suffixes = set()
    for i in xrange(len(sorted_suffix_array) - 1):
        current_suffixes = {}
        for string_index in sorted_suffix_array[i][1]:
            current_suffixes[string_index] = sorted_suffix_array[i][0]
        for j in xrange(i, len(sorted_suffix_array)):
            string_suffix = sorted_suffix_array[j][0]
            string_indexes = sorted_suffix_array[j][1]
            for string_index in string_indexes:
                current_suffix = current_suffixes.get(string_index, "")
                if len(string_suffix) > len(current_suffix):
                    current_suffixes[string_index] = string_suffix
            if len(current_suffixes) == len(strings):
                break

        if len(current_suffixes) == len(strings):
            useful_suffixes.add(tuple(current_suffixes.values()))
    # -------------------------------------------------------------------------

    useful_suffixes = sorted(list(useful_suffixes))
    longest_common_prefixes = [lcp(*suffixes)
                               for suffixes in useful_suffixes]
    return max(longest_common_prefixes, key=len)

if __name__ == "__main__":
    lines = []
    with open("rosalind_lcs.txt") as f:
        lines = [line.strip() for line in f if len(line.strip()) != 0]

    #lines = ["ABCABCABCABCABCABC", "DEFDEFDEFDEFABCABCDEFDEF", "GHGGHGHGHGHGHGABCDHDHDH"]
    #lines = ["GATTACA", "TAGACCA", "ATACA"]

    print longest_common_substring(lines)
