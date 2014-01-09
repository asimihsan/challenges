#!/usr/bin/env pypy

"""Another Boggle solver.

Don't peek at the old one!
"""

import collections
import pprint


class WordLookup(object):
    class Node(object):
        def __init__(self):
            self.next = collections.defaultdict(lambda: None)
            self.is_word = False

    def __init__(self):
        self.root = None
        with open("/usr/share/dict/words") as f_in:
            for line in f_in:
                self.root = self._put(self.root, line.lower().strip(), 0)

    def _put(self, node, word, d):
        if node is None:
            node = self.Node()
        if d == len(word):
            node.is_word = True
            return node
        character = word[d]
        node.next[character] = self._put(node.next[character], word, d + 1)
        return node

    def _get(self, node, word, d):
        if node is None:
            return None
        if d == len(word):
            return node
        character = word[d]
        return self._get(node.next[character], word, d + 1)

    def is_valid_word(self, word):
        node = self._get(self.root, word, 0)
        if node is None or node.is_word is False:
            return False
        else:
            return True

    def is_valid_prefix(self, prefix):
        return self._get(self.root, prefix, 0) is not None


def get_neighbors(board, cell):
    """Given a 2D array of two-tuples, each element being an integer, what
    are the neighbors of a cell, itself a two-tuple of two integers?

    Assumes board is rectangular, and assumes you do not want to wrap around
    numbers.

    Returns a generator of two-tuples, each element being an integer.
    """
    for i in xrange(cell[0] - 1, cell[0] + 2):
        for j in xrange(cell[1] - 1, cell[1] + 2):
            if i == cell[0] and j == cell[1]:
                continue
            if i < 0 or i >= len(board):
                continue
            if j < 0 or j >= len(board[i]):
                continue
            yield (i, j)


def search(board, cell, words, word_lookup, path=None):
    """Remembering the current path, recursively search all paths from
    this cell.
    """
    if path is None:
        path = []
    candidate = ''.join([board[x[0]][x[1]] for x in path])
    if not word_lookup.is_valid_prefix(candidate):
        return
    if word_lookup.is_valid_word(candidate):
        words.append(candidate)
    for neighbor in get_neighbors(board, cell):
        if neighbor not in path:
            search(board, neighbor, words, word_lookup, path + [neighbor])


def solve(board):
    """Given a Boggle board, 2D array of characters, return a list of
    words in arbitrary order."""

    words = []
    word_lookup = WordLookup()
    for i in xrange(len(board)):
        for j in xrange(len(board[i])):
            search(board, (i, j), words, word_lookup)
    return words


board = [['n', 'y', 'e', 't'],
         ['e', 'o', 'v', 'c'],
         ['n', 's', 'i', 'g'],
         ['n', 'r', 'h', 'a']]
pprint.pprint(sorted(list(set(solve(board))),
                     cmp=lambda x, y: cmp(len(y), len(x))))
