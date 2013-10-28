import string
import sys

all_ascii_lowercase = set(string.ascii_lowercase)
with open(sys.argv[1], 'r') as f_in:
    for line in f_in:
        letters = set(letter for letter in line.lower().strip()
                      if letter in all_ascii_lowercase)
        missing = all_ascii_lowercase - letters
        if len(missing) == 0:
            print 'NULL'
        else:
            print ''.join(sorted(list(missing)))
