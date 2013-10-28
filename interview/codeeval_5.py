import sys


def process_seq(seq):
    for i in xrange(1, len(seq)):
        for j in xrange(1, len(seq)):
            if any(elem > len(seq) for elem in [i+j, i+2*j]):
                continue
            sub = seq[i:i+j]
            cand = seq[i+j:i+2*j]
            if sub == cand:
                print ' '.join(sub)
                return

with open(sys.argv[1], 'r') as f_in:
    lines = (line.strip() for line in f_in if len(line.strip()) > 0)
    for line in lines:
        seq = [elem for elem in line.strip().split()]
        process_seq(seq)
