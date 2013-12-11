class Node(object):
    def __init__(self, value):
        self.value = value
        self.next = None

    def __str__(self):
        return '{value: %s}' % self.value

    def __repr__(self):
        return str(self)


class SinglyLinkedList(object):
    def __init__(self):
        self.head = None
    
    def append(self, value):
        node = Node(value)
        if self.head is None:
            self.head = node
            return
        current = self.head
        while current.next is not None:
            current = current.next
        current.next = node

    def reverse(self):
        if self.head is None or self.head.next is None:
            return
        previous = None
        next = None
        while self.head is not None:
            next = self.head.next
            self.head.next = previous
            previous = self.head
            self.head = next
        self.head = previous

    def __iter__(self):
        if self.head is None:
            return
        current = self.head
        while current is not None:
            yield current.value
            current = current.next
        

sll = SinglyLinkedList()
sll.append(1)
sll.append(2)
sll.append(3)
print [e for e in sll]
sll.reverse()
print [e for e in sll]

