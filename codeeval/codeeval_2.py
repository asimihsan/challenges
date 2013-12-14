import heapq
import operator
import sys

with open(sys.argv[1], 'r') as f_in:
    next_line = f_in.readline().strip()
    if len(next_line) == 0:
        sys.exit(0)
    N = int(next_line)
    longest_lines = [(0, None)] * N
    heapq.heapify(longest_lines)

    lines = (line.strip() for line in f_in if len(line.strip()) > 0)
    for line in lines:
        if len(line) > longest_lines[0][0]:
            heapq.heapreplace(longest_lines, (len(line), line))

longest_lines.sort(key=operator.itemgetter(0), reverse=True)
print '\n'.join(elem[1] for elem in longest_lines
                if elem[1] is not None)
