import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Node {
    int row;
    int col;

    Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("{row=%s, col=%s}", row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node n = (Node)o;
        return (row == n.row) && (col == n.col);
    }

    public int distanceTo(Node other) {
        return Math.abs(row - other.row) + Math.abs(col - other.col);
    }
}

class HackerRankBotClean {
    static String getAction(Node source, Node destination) {
        //System.out.println(String.format("source=%s, destination=%s", source, destination));
        if (source.equals(destination))
            return "CLEAN";
        if (Math.abs(source.row - destination.row) < Math.abs(source.col - destination.col)) {
            if (source.col > destination.col) {
                return "LEFT";
            } else {
                return "RIGHT";
            }
        } else {
            if (source.row > destination.row) {
                return "UP";
            } else {
                return "DOWN";
            }
        }
    }

    static void next_move(int posr, int posc, String[] board) {
        Node source = new Node(posr, posc);
        Node closest = null;
        int minimumDistance = Integer.MAX_VALUE;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length(); col++) {
                if (board[row].charAt(col) == 'd') {
                    Node dirty = new Node(row, col);
                    int distance = source.distanceTo(dirty);
                    if (distance < minimumDistance) {
                        closest = dirty;
                        minimumDistance = distance;
                    }
                }
            }
        }
        String action = getAction(source, closest);
        System.out.println(action);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] pos = new int[2];
        String[] board = new String[5];
        for (int i = 0; i < 2; i++)
            pos[i] = in.nextInt();
        for (int i = 0; i < 5; i++)
            board[i] = in.next();
        next_move(pos[0], pos[1], board);
    }
}