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


class StackWithMin(Stack):
    def __init__(self):
        super(StackWithMin, self).__init__()
        self.minimums = Stack()
        
    def push(self, value):
        super(StackWithMin, self).push(value)
        if len(self.minimums) == 0 or value < self.minimums.peek():
            self.minimums.push(value)
        
    def pop(self):
        return_value = super(StackWithMin, self).pop()
        if len(self) == 0 or self.peek() > self.minimums.peek():
            self.minimums.pop()
        return return_value
        
    def min(self):
        return self.minimums.peek()

stack = StackWithMin()
stack.push(3)
stack.push(2)
stack.push(1)
stack.push(1)
assert(stack.min() == 1)
assert(stack.pop() == 1)
assert(stack.min() == 1)
assert(stack.pop() == 1)
assert(stack.min() == 2)
assert(stack.pop() == 2)
assert(stack.min() == 3)
assert(stack.pop() == 3)
