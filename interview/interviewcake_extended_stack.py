import unittest


class EmptyError(Exception):
    pass


class Stack(object):
    def __init__(self):
        self.data = []
        
    def push(self, value):
        self.data.append(value)

    def peek(self):
        return self.data[-1]

    def __len__(self):
        return len(self.data)
        
    def pop(self):
        if len(self.data) == 0:
            raise EmptyError
        return_value = self.data[-1]
        self.data = self.data[:-1]
        return return_value


class StackTest(unittest.TestCase):
    def test_basic(self):
        stack = Stack()
        stack.push(4)
        stack.push(5)
        self.assertEqual(stack.pop(), 5)
        self.assertEqual(stack.pop(), 4)

    def test_pop_empty_stack(self):
        stack = Stack()
        self.assertRaises(EmptyError, stack.pop)


class ExtendedStackTest(unittest.TestCase):
    def test_get_largest_basic(self):
        stack = ExtendedStack()
        for i in xrange(5):
            stack.push(i)
        self.assertEqual(stack.getLargest(), 4)
        self.assertEqual(stack.pop(), 4)

    def test_get_largest_duplicate(self):
        stack = ExtendedStack()
        stack.push(1)
        stack.push(4)
        stack.push(4)
        self.assertEqual(stack.getLargest(), 4)
        stack.pop()
        self.assertEqual(stack.getLargest(), 4)
        stack.pop()
        self.assertEqual(stack.getLargest(), 1)


class ExtendedStack(Stack):
    def __init__(self):
        super(ExtendedStack, self).__init__()
        self.max_stack = Stack()

    def push(self, value):
        super(ExtendedStack, self).push(value)
        if len(self.max_stack) == 0 or value >= self.max_stack.peek():
            self.max_stack.push(value)

    def pop(self):
        return_value = super(ExtendedStack, self).pop()
        if len(self.max_stack) != 0 and self.max_stack.peek() == return_value:
            self.max_stack.pop()
        return return_value

    def getLargest(self):
        temp = Stack()
        maximum_value = None
        while True:
            try:
                value = self.pop()
                if maximum_value is None:
                    maximum_value = value
                else:
                    maximum_value = max(maximum_value, value)
            except EmptyError:
                break
            temp.push(value)
        self.data = temp.data[::-1]
        return maximum_value


if __name__ == '__main__':
    unittest.main()
