import random


class SkipListNode(object):
    def __init__(self, value=None, height=32):
        self.value = value
        self.next = [None for i in xrange(height+1)]

    @property
    def height(self):
        return len(self.next) - 1


class SkipListSortedSet(object):
    sentinel = SkipListNode()

    def __init__(self):
        self.height = 0

    def find(self, value):
        u = self._find_predecessor_node(value)
        if u.next[0] is None:
            return None
        return u.next[0].value

    def add(self, value):
        u = self.sentinel
        r = self.height
        array = [None for i in xrange(self.height+1)]
        while r >= 0:
            while u.next[r] is not None and value > u.next[r].value:
                u = u.next[r]
            if u.next[r] is not None and value == u.next[r].value:
                return False
            array[r] = u
            r -= 1
        w = SkipListNode(value, self._pick_height())
        while self.height < w.height:
            array[self.height] = self.sentinel
            self.height += 1
        for i in xrange(len(w.next)):
            w.next[i] = array[i].next[i]
            array[i].next[i] = w
        return True

    def _find_predecessor_node(self, value):
        u = self.sentinel
        r = self.height
        while r >= 0:
            while u.next[r] is not None and value > u.next[r].value:
                u = u.next[r]
            r -= 1
        return u

    def _pick_height(self):
        height = 0
        while random.random() <= 0.5:
            height += 1
        return height
