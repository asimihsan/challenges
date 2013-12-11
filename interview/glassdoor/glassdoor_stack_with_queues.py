"""

queue push
self.stack1 = [1]
self.stack1 = [1, 2]
self.stack1 = [1, 2, 3]

queue pop...pop everything from stack1 to stack2
self.stack1 = []
self.stack2 = [3, 2, 1]
then pop from stack2, correct

self.stack1 = []
self.stack2 = [3, 2]

push 4? push 5?
queue: [2, 3, 4], next is 2

self.stack1 = [4, 5]
self.stack2 = [3, 2]

pops _always_ from stack2.
if stack2 empty pop stack1 onto stack2 then attempt to continue

"""

class Stack(object):
    def __init__(self):
        self.data = []
        
    def pop(self):
        return self.data.pop()
        
    def push(self, value):
        self.data.append(value)

    def peek(self):
        return self.data[-1]
        
    def __len__(self):
        return len(self.data)


class QueueEmptyError(Exception):
    pass


class Queue(object):
    def __init__(self):
        self.stack1 = Stack()
        self.stack2 = Stack()
        
    def _rebalance(self):
        if len(self.stack2) == 0:
            while len(self.stack1) != 0:
                self.stack2.push(self.stack1.pop())
    
    def pop(self):
        if len(self.stack1) == 0 and len(self.stack2) == 0:
            raise QueueEmptyError
        self._rebalance()
        return self.stack2.pop()
        
    def push(self, value):
        self.stack1.push(value)
        
    def peek(self):
        if len(self.stack1) == 0 and len(self.stack2) == 0:
            raise QueueEmptyError
        self._rebalance()
        return self.stack2.peek()

q = Queue()
q.push(1)
q.push(2)
q.push(3)
print q.pop()
print q.pop()
print q.peek()

        