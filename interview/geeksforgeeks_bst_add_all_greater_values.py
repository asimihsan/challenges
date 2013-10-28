"""
Given a Binary Search Tree (BST), modify it so that all greater values in the
given BST are added to every node. For example, consider the following BST.

Given a BST, we can:

-   Do in-order traversal (left, centre, right) to output a sorted
    array of values in O(n).
-   Create a 'summation' array in O(n) time with O(n) additional
    space. Set each value the sum of all greater values.
-   Assuming no duplicate values (or, at least, duplicate values are
    stored as a list in a single BST node), in O(log n) find node
    in BST and O(log n) find index of node in in-order traversal
    array, and use that index to find summation value.
    
Hence overall O(n) time and O(n) space. Might be a better way than
O(n) space, but let's code this for now. This involves two traversals of the
BST. Original problem page has it done in one traversal (traverse in
reverse in-order order, keeping track of the sum, and setting the current
sum to the current node):

http://www.geeksforgeeks.org/add-greater-values-every-node-given-bst/
"""

class Node(object):
    def __init__(self, value):
        self.left = None
        self.right = None
        self.value = value
        
    def __str__(self):
        return '{value: %s, left: %s, right: %s}' % (self.value, self.left, self.right)
        
    def __repr__(self):
        return str(self)
        

def inorder(node):
    if node.left is not None:
        for elem in inorder(node.left):
            yield elem
    yield node
    if node.right is not None:
        for elem in inorder(node.right):
            yield elem


BST = Node(50)
BST.left = Node(30)
BST.left.left = Node(20)
BST.left.right = Node(40)
BST.right = Node(70)
BST.right.right = Node(80)
BST.right.left = Node(60)

# 1.
inorder_array = [node for node in inorder(BST)]
print inorder_array

# 2.
summation_array = [0 for i in xrange(len(inorder_array))]
summation_array[-1] = inorder_array[-1].value
for i in xrange(len(summation_array) - 2, -1, -1):
    summation_array[i] = inorder_array[i].value + summation_array[i+1]
print summation_array

for i, node in enumerate(inorder_array):
    node.value = summation_array[i]

print BST
