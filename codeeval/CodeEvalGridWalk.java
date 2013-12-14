/**
 * There is a monkey which can walk around on a planar grid. The monkey can move
 * one space at a time left, right, up or down. That is, from (x, y) the monkey can
 * go to (x+1, y), (x-1, y), (x, y+1), and (x, y-1). Points where the sum of the
 * digits of the absolute value of the x coordinate plus the sum of the digits of
 * the absolute value of the y coordinate are lesser than or equal to 19 are
 * accessible to the monkey. For example, the point (59, 79) is inaccessible
 * because 5 + 9 + 7 + 9 = 30, which is greater than 19. Another example: the point
 * (-5, -7) is accessible because abs(-5) + abs(-7) = 5 + 7 = 12, which is less
 * than 19. How many points can the monkey access if it starts at (0, 0), including
 * (0, 0) itself?
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class Point {
    int x;
    int y;

    Point(int x, int y) { this.x = x; this.y = y; };

    @Override
    public String toString() {
        return String.format("{x=%s, y=%s}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point)o;
        return ((x == point.x) && (y == point.y));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    public boolean isAccessible(int limit) {
        return getSum() <= limit;
    }

    private int getSum() {
        int result = 0;
        String x = new Integer(Math.abs(this.x)).toString();
        for (int i = 0; i < x.length(); i++)
            result += new Integer(x.substring(i, i+1));
        String y = new Integer(Math.abs(this.y)).toString();
        for (int i = 0; i < y.length(); i++)
            result += new Integer(y.substring(i, i+1));
        return result;
    }
}

class GridWalk {
    private static List<Point> getNeighbors(Point point, int limit) {
        List<Point> neighbors = new ArrayList<>(4);
        Point candidate = new Point(point.x - 1, point.y);
        if (candidate.isAccessible(limit)) neighbors.add(candidate);
        candidate = new Point(point.x, point.y - 1);
        if (candidate.isAccessible(limit)) neighbors.add(candidate);
        candidate = new Point(point.x + 1, point.y);
        if (candidate.isAccessible(limit)) neighbors.add(candidate);
        candidate = new Point(point.x, point.y + 1);
        if (candidate.isAccessible(limit)) neighbors.add(candidate);
        return neighbors;
    }

    public static int solve(int limit) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(new Point(0, 0));
        Set<Point> visited = new HashSet<>();
        while (queue.size() != 0) {
            Point current = queue.remove();
            if (visited.contains(current)) continue;
            visited.add(current);
            queue.addAll(getNeighbors(current, limit));
        }
        return visited.size();
    }
}

class CodeEvalGridWalk {
    public static void main(String[] args) {
        System.out.println(GridWalk.solve(19));
    }
}
