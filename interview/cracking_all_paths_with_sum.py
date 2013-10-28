class BinaryTree(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        
    def __str__(self):
        return '{value: %s}' % self.value
        
    def __repr__(self):
        return str(self)


def get_all_subpaths(path):
    """Return all subpaths.
    
    e.g. [1, 2, 3] returns
         [[1], [2], [3], [1, 2], [2, 3], [1, 2, 3]]
    """
    
    return_value = [tuple(path)]
    if len(path) == 1:
        return return_value
    for subpath in [tuple(path[1:]), tuple(path[:-1])]:
        return_value.extend(get_all_subpaths(subpath))
    return list(set(return_value))


def all_paths_with_sum(tree, value, path=None):
    if path is None:
        path = []
    path.append(tree)
    if tree.left is None and tree.right is None:
        for subpath in get_all_subpaths(path):
            if sum(node.value for node in subpath) == value:
                yield subpath
    nodes = [node for node in [tree.left, tree.right]
             if node is not None]
    for node in nodes:
        for subpath in all_paths_with_sum(node, value, path[:]):
            yield subpath

a = BinaryTree(5)
b = BinaryTree(4)
c = BinaryTree(3)
d = BinaryTree(2)
e = BinaryTree(1)
f = BinaryTree(6)

a.left = b
a.right = c
b.left = d
c.right = e
c.left = f

print [path for path in all_paths_with_sum(a, 9)]
