"""
General Depth First Search (DFS), using:

-   Algorithms in a Nutshell
-   http://www.cs.toronto.edu/~heap/270F02/node36.html
"""


class Vertex(object):
    def __init__(self, value):
        self.value = value

    def __eq__(self, other):
        if not isinstance(other, Vertex):
            return False
        return self.value == other.value

    def __hash__(self):
        return hash(self.value)

    def __str__(self):
        return '{value: %s}' % self.value

    def __repr__(self):
        return str(self)


class Graph(object):

    def __init__(self):
        self.nodes = set()
        self.adj = {}

    def nodes_iter(self):
        return iter(self.nodes)

    def add_vertex(self, value):
        node = Vertex(value)
        self.nodes.add(node)
        self.adj[node] = set()

    def add_undirected_edge(self, value1, value2):
        node1 = Vertex(value1)
        node2 = Vertex(value2)
        self.adj[node1].add(node2)
        self.adj[node2].add(node1)

    def get_neighbours(self, node):
        return self.adj[node]

    def get_node(self, value):
        return Vertex(value)


def dfs(G, value):
    node = G.get_node(value)
    visited = dict((node, False) for node in G.nodes_iter())
    stack = [node]
    while len(stack) != 0:
        node = stack.pop()
        if visited[node] is False:
            print 'visited: %s' % node.value
            visited[node] = True
            stack.extend(G.get_neighbours(node))


def bfs(G, value):
    node = G.get_node(value)
    visited = dict((node, False) for node in G.nodes_iter())
    queue = [node]
    while len(queue) != 0:
        node = queue.pop(0)
        if visited[node] is False:
            print 'visited: %s' % node.value
            visited[node] = True
            queue.extend(G.get_neighbours(node))

g = Graph()
g.add_vertex('A')
g.add_vertex('B')
g.add_vertex('C')
g.add_vertex('D')
g.add_vertex('E')
g.add_vertex('F')
g.add_vertex('G')
g.add_vertex('H')
g.add_vertex('I')
g.add_undirected_edge('A', 'B')
g.add_undirected_edge('B', 'C')
g.add_undirected_edge('C', 'D')
g.add_undirected_edge('A', 'E')
g.add_undirected_edge('E', 'B')
g.add_undirected_edge('A', 'F')
g.add_undirected_edge('F', 'G')
g.add_undirected_edge('G', 'H')
g.add_undirected_edge('H', 'I')

dfs(g, 'A')
print '-' * 79
bfs(g, 'A')
