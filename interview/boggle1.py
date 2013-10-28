"""
Solve boggle!
"""

boggle = [['a', 'r', 'e'],
          ['d', 'f', 'g'],
          ['d', 'f', 'g']]
# 'add', 'egg', 'fad', ...
# (any square board, letters don't 'wrap over the edge'

# 1.  for each letter on board, DFS to neighbours.
# 2.  if prefix is valid only then continue on path.
# 2a. if prefix is not valid then do not continue on path.
# 3.  if current path is valid word then yield it.

# 1.
# (0, 0) -> (0, 1), (1, 0), (1, 1)
# (1, 1) -> 8 squares (diagonal are neighbours)


def neighbours_for_index(index, arr):
    """Returns a list of two-tuples for coordinates
    that are neighbours on a square board.
    
    Diagonals are neighbours. Do not wrap over the edges.
    """
    rows = cols = len(arr)
    neighbours = []
    for i in xrange(index[0] - 1, index[0] + 2):
        for j in xrange(index[1] - 1, index[1] + 2):
            if i == index[0] and j == index[1]:
                continue
            if i < 0 or i >= rows:
                continue
            if j < 0 or j >= cols:
                continue
            neighbours.append((i, j))
    return neighbours


class TrieNode(object):
    __slots__ = ('key', 'is_word', 'next')

    def __init__(self, key, is_word=False):
        self.key = key
        self.is_word = is_word
        self.next = {}
        
    def __eq__(self, other):
        if not isinstance(other, TrieNode):
            return False
        return self.key == other.key
    
    def __hash__(self):
        return hash(self.key)
        
    def __str__(self):
        return '{key: %s, is_word: %s}' % (self.key, self.is_word)
        
    def __repr__(self):
        return str(self)
        
    def __contains__(self, key):
        return TrieNode(key) in self.next
        
    def __getitem__(self, key):
        return self.next[TrieNode(key)]
        

class Trie(object):
    def __init__(self):
        self.root = TrieNode(key=None, is_word=False)
        
    def put(self, word):
        node = self.root
        for character in word:
            if character not in node:
                new_node = TrieNode(key=character, is_word=False)
                node.next[new_node] = new_node
                node = new_node
            else:
                node = node[character]
        node.is_word = True
        
    def is_prefix(self, input_string):
        node = self.root
        for character in input_string:
            if character not in node:
                return False
            node = node[character]
        return True
        
    def is_word(self, input_string):
        node = self.root
        for character in input_string:
            if character not in node:
                return False
            node = node[character]
        return node.is_word


trie = Trie()
with open('/usr/share/dict/words') as f_in:
    lines = (line.strip().lower() for line in f_in
             if len(line.strip()) > 0)
    for line in lines:
        trie.put(line)

def is_valid_word(input_string):
    return trie.is_word(input_string)
    

def is_valid_prefix(input_string):
    return trie.is_prefix(input_string)


def search(coord, arr, path=None):
    if path is None:
        path = []
    path.append(coord)
    candidate = ''.join(arr[i][j] for (i, j) in path)
    if is_valid_word(candidate):
        yield candidate
    if not is_valid_prefix(candidate):
        return
    for neighbour in neighbours_for_index(coord, arr):
        if neighbour not in path:
            for word in search(neighbour, arr, path[:]):
                yield word


def generate_boggle_words(boggle):
    for i in xrange(len(boggle)):
        for j in xrange(len(boggle)):
            for word in search((i, j), boggle):
                yield word


print sorted(list(set([word for word in generate_boggle_words(boggle)])))
