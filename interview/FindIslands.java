import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

class Coordinate {
    public int x, y;
    Coordinate(int x, int y) { this.x = x; this.y = y; }

    @Override public String toString() {
        return String.format("{x=%s, y=%s}", x, y);
    }

    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate coord = (Coordinate)o;
        return (this.x == coord.x && this.y == coord.y);
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * x + result;
        result = 31 * y + result;
        return result;
    }
}

class FindIslands {
    public static Collection<Coordinate> getNeighbors(Coordinate coord, int[][] landscape) {
        int width = landscape.length;
        int height = landscape[0].length;
        Collection<Coordinate> neighbors = new ArrayList<Coordinate>();
        for (int i = coord.x - 1; i <= coord.x + 1; i++) {
            for (int j = coord.y - 1; j <= coord.y + 1; j++) {
                if (i == coord.x && j == coord.y)
                    continue;
                if (i < 0 || i > (width - 1))
                    continue;
                if (j < 0 || j > (height - 1))
                    continue;
                if (landscape[i][j] == 1)
                    neighbors.add(new Coordinate(i, j));
            }
        }
        return neighbors;
    }

    public static int getNumberOfIslands(int[][] landscape) {
        int width = landscape.length;
        int height = landscape[0].length;
        Stack<Coordinate> nodes = new Stack<Coordinate>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (landscape[i][j] == 1)
                    nodes.push(new Coordinate(i, j));
            }
        }

        Set<Coordinate> visited = new LinkedHashSet<Coordinate>();
        Stack<Coordinate> currentNodes = new Stack<Coordinate>();
        currentNodes.push(null);
        currentNodes.push(nodes.pop());
        int numberOfIslands = 0;
        while(nodes.size() != 0) {
            Coordinate currentNode = currentNodes.pop();
            if (currentNode == null) {
                numberOfIslands += 1;
                while (nodes.size() != 0) {
                    currentNode = nodes.pop();
                    if (!(visited.contains(currentNode)))
                        break;
                }
                if (nodes.size() == 0)
                    continue;
                currentNodes.push(null);
                currentNodes.push(currentNode);
                continue;
            }
            if (!(visited.contains(currentNode))) {
                visited.add(currentNode);
                currentNodes.addAll(getNeighbors(currentNode, landscape));
            }
        }
        return numberOfIslands;
    }

    public static void main(String[] args) {
        int[][] landscape = {{1, 1, 0, 0, 0},
                             {0, 1, 0, 0, 1},
                             {1, 0, 0, 1, 1},
                             {0, 0, 0, 0, 0},
                             {1, 0, 1, 0, 1}};
        System.out.println(getNumberOfIslands(landscape));
    }
}