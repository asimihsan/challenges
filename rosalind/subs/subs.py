import re

def find_locations(string, substring):
    """ Find all _overlapping_ instances of substring in string.

    By default regexp's return non-overlapping results. This is
    far simpler under-the-covers. To get overlapping results
    we need to use lookaheads, which are much more expensive."""

    result = []
    pattern = r'(?=%s)' % substring
    for match in re.finditer(pattern, string):
        result.append(match.start() + 1)
    print " ".join(str(elem) for elem in result)

if __name__ == "__main__":
    with open("rosalind_subs.txt") as f:
        lines = f.readlines()
    string = lines[0].strip()
    substring = lines[1].strip()
    find_locations(string, substring)

