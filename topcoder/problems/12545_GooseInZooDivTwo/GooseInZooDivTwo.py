#!/usr/bin/env python

"""
SRM 578 - Goose In Zoo Division Two (problem 12545)

Suppose we have the following grid, with dist=1:

vvv
...
vvv

If any cell in the top has a goose, then the rest must have geese. Conversely,
if any cell in the top does not have a goose, the rest must not have geese.
The top row has only two degrees of freedom, despite having three cells.
Moreover, the top row's values does not effect the bottom row's values because
they are too far apart.

Rather than enumerating all possible values of cells, we want to enumerate
over each of the two possible values of each "supercell". Each supercell may
either have all geese or no geese. Indeed, given k supercells, there are
2^k possible combinations of all supercells. Since one combination is all
supercells having no geese we disregard this. Hence, if there is a way of
determining how many supercells (k) there are, the result is 2^k-1.

What is a supercell? All cells that are within a certain distance of one
another. If we think of this as a graph, where nodes only have an edge
between them if they are within the required distance, then a supercell
is also a connected component. In graph theory a connected component is
a subgraph wherein all pairs of nodes have a path connecting them, and
all nodes in the subgraph do not have edges to nodes outside the
subgraph.

Hence after drawing edges between all nodes separated by less than dist
return 2^k-1, where k is the number of connected components.

To execute tests please use PyTopCoder
"""

# For a fully connected graph, i.e. all nodes are possible and every
# node is connected to all other nodes, there are 50 * 50 = 2500
# edges per node, and DFS will need a stack limit of around 2500
# (plus calls that start, and the Python interpreter itself) to
# DFS this graph. The default of 1000 doesn't cut it.
import sys
sys.setrecursionlimit(50 * 50 + 500)

import collections

Node = collections.namedtuple("Node", ["x", "y", "index"])


def calculate_distance(first, second):
    return abs(first.x - second.x) + abs(first.y - second.y)


class Graph(object):
    def __init__(self, nodes, adjacency_lists):
        self.nodes = nodes
        self.adjacency_lists = adjacency_lists

    def get_adjacent_nodes(self, node):
        return self.adjacency_lists[node.index]


class ConnectedComponents(object):
    def __init__(self, graph):
        self.marked = [False for node in graph.nodes]
        self.id = [-1 for node in graph.nodes]
        self.count = 0

        for node in graph.nodes:
            if not self.marked[node.index]:
                self.dfs(graph, node)
                self.count += 1

    def dfs(self, graph, node):
        self.marked[node.index] = True
        self.id[node.index] = self.count
        for neighbour in graph.get_adjacent_nodes(node):
            if not self.marked[neighbour.index]:
                self.dfs(graph, neighbour)

    def is_connected(self, v, w):
        """Are nodes v and w connected?"""
        return self.id[v] == self.id[w]

    def get_count(self):
        """Get number of connected components."""
        return self.count

    def get_id(self, v):
        """Get the identification number of the connected component within
        which node v is contained."""
        return self.id[v]


def get_graph(field, dist):
    nodes = []
    index = 0
    for y, row_elements in enumerate(field):
        for x, column_elements in enumerate(row_elements):
            if field[y][x] != "v":
                continue
            nodes.append(Node(x, y, index))
            index += 1

    adjacency_lists = []
    for first_index, first_node in enumerate(nodes):
        first_node_adjacency_list = []
        for second_index, second_node in enumerate(nodes):
            if first_index == second_index:
                continue
            if calculate_distance(first_node, second_node) <= dist:
                first_node_adjacency_list.append(second_node)
        adjacency_lists.append(first_node_adjacency_list)

    return Graph(nodes, adjacency_lists)


def count(field, dist):
    graph = get_graph(field, dist)
    connected_components = ConnectedComponents(graph)
    k = connected_components.get_count()
    return pow(2, k, 1000000007) - 1

if __name__ == "__main__":
    assert(count(['vvv'], 1)) == 1
    assert(count(['vvv'], 0)) == 7
