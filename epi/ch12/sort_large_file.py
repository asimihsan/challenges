#!/usr/bin/env pypy

"""Sort a large file that does not fit in memory, composed of lines
of varying length. Sort in O(nlogn) but such that memory usage is
capped at a reasonably small level.
"""

import heapq
import os


class HeapElement(object):
    def __init__(self, filepath):
        self.f_in = open(filepath)

    def __iter__(self):
        return self

    def next(self):
        line = self.f_in.readline()
        if line == '':
            raise StopIteration
        if not line.endswith('\n'):
            line = line + '\n'
        return line

    def close(self):
        self.f_in.close()

    def __del__(self):
        self.close()


def sort_file(input_file, output_file):
    input_size = os.stat(input_file).st_size
    R = max(input_size / (2000 * 1024), 16)
    chunk_size = input_size / R
    temp_files = []
    with open(input_file) as f_in:
        for i in xrange(R):
            temp_file = "temp_%05d.txt" % i
            with open(temp_file, "w") as f_out:
                contents = f_in.read(chunk_size) + f_in.readline()
                contents = contents.splitlines()
                contents.sort()
                f_out.write('\n'.join(contents))
            temp_files.append(HeapElement(temp_file))
    with open(output_file, 'w') as f_out:
        f_out.writelines(heapq.merge(*temp_files))


if __name__ == "__main__":
    sort_file("large_file.txt", "large_file_sorted.txt")
