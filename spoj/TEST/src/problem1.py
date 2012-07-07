#!/usr/bin/env python

import os
import sys

def main():
    while True:
        next_line = sys.stdin.readline()
        if not next_line:
            break
        if next_line.strip() == "42":
            break
        sys.stdout.write(next_line)
        sys.stdout.flush()

if __name__ == "__main__":
    main()

