#!/usr/bin/env python

from string import Template
import os
import random
import sys

CURRENT_DIR = os.path.dirname(__file__)
TEMPL = Template("${timestamp},${symbol},${number},${price}\n")
start_timestamp = 10000


def do_symbol(symbol):
    timestamp = start_timestamp
    with open("%s.txt" % symbol.lower(), "w") as f_out:
        for i in xrange(500):
            timestamp += random.randint(1, 10)
            f_out.write(TEMPL.substitute(
                timestamp=timestamp,
                symbol=symbol,
                number=random.randint(1, 200),
                price=random.random() * 100))


def main():
    map(do_symbol, ["AAPL", "IBM", "GOOG", "AMZN"])


if __name__ == '__main__':
    sys.exit(main())
