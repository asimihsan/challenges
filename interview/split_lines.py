#!/usr/bin/env python

import unittest


def split_lines(text, length):
    """Split text along word boundaries bounded by a maximum column
    length."""
    return_value = []
    current_line = []
    current_length = 0
    for word in text.split():
        if current_length > 0 and current_length + len(word) + 1 > length:
            return_value.append(" ".join(current_line))
            current_line = []
            current_length = 0
        current_line.append(word)
        current_length += len(word) + 1
    return_value.append(" ".join(current_line))
    return return_value


class SplitLinesTests(unittest.TestCase):
    def test_shortest_than_maximum(self):
        self.assertEqual(split_lines('Short', 10), ['Short'])

    def test_text_of_exactly_max_length(self):
        self.assertEqual(split_lines('Short', 5), ['Short'])

    def test_text_one(self):
        self.assertEqual(split_lines('This is a fairly long text', 6),
                         ['This', 'is a', 'fairly', 'long', 'text'])

    def test_extremely_long_word_is_untouched(self):
        self.assertEqual(split_lines('abracadabra!!!', 6), ['abracadabra!!!'])

if __name__ == "__main__":
    unittest.main()
