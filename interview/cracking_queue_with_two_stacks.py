import unittest


class Stack(object):
    def __init__(self):
        self.data = []
    
    def push(self, value):
        self.data.append(value)
        
    def pop(self):
        return self.data.pop()
        
    def peek(self):
        return self.data[-1]
    
    def __len__(self):
        return len(self.data)
        

class Queue(object):
    def __init__(self):
        self.stack1 = Stack()
        self.stack2 = Stack()

    def push(self, value):
        while len(self.stack1) != 0:
            self.stack2.push(self.stack1.pop())
        self.stack1.push(value)
        while len(self.stack2) != 0:
            self.stack1.push(self.stack2.pop())

    def pop(self):
        return self.stack1.pop()


class QueueTest(unittest.TestCase):
    def setUp(self):
        self.queue = Queue()

    def test1(self):
        self.queue.push(1)
        self.queue.push(2)
        self.queue.push(3)
        self.assertEqual(self.queue.pop(), 1)
        self.assertEqual(self.queue.pop(), 2)
        self.assertEqual(self.queue.pop(), 3)


if __name__ == '__main__':
    unittest.main()
