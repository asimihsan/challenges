class BinarySearchTree(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        
    def __str__(self):
        return '{value: %s, left: %s, right: %s}' % \
               (self.value, self.left, self.right)

    def __repr__(self):
        return str(self)
        
    def inorder_iter(self):
        if self.left is not None:
            for node in self.left.inorder_iter():
                yield node
        yield self
        if self.right is not None:
            for node in self.right.inorder_iter():
                yield node


class DoublyLinkedListNode(object):
    def __init__(self, value):
        self.value = value
        self.next = None
        self.previous = None
        
    def __str__(self):
        return '{value: %s, next: %s}' % \
               (self.value, self.next)

    def __repr__(self):
        return str(self)


class DoublyLinkedList(object):
    def __init__(self):
        self.head = None
        self.tail = None
        
    def put(self, value):
        node = DoublyLinkedListNode(value)
        if self.head is None:
            self.head = node
            self.tail = node
        else:
            self.tail.next = node
            node.previous = self.tail
            self.tail = node

    def forward_iter(self):
        if self.head is None:
            return
        current = self.head
        while current is not None:
            yield current
            current = current.next


a = BinarySearchTree(8)
b = BinarySearchTree(3)
c = BinarySearchTree(10)
d = BinarySearchTree(1)
e = BinarySearchTree(6)
f = BinarySearchTree(14)
g = BinarySearchTree(4)
h = BinarySearchTree(7)
i = BinarySearchTree(13)

a.left = b
a.right = c
b.left = d
b.right = e
e.left = g
e.right = h
c.right = f
f.left = i

linked_list = DoublyLinkedList()
for node in a.inorder_iter():
    linked_list.put(node.value)
print [node.value for node in linked_list.forward_iter()]
