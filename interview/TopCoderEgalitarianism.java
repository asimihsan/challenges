class Egalitarianism {
    public int maxDifference(String[] isFriend, int d) {
        // 1. Initialization
        final int n = isFriend[0].length();
        int[][] dist = new int[n][n];
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                dist[u][v] = Integer.MAX_VALUE;
            }
            dist[u][u] = 0;
            String row = isFriend[u];
            for (int v = 0; v < n; v++) {
                char flag = row.charAt(v);
                if (u == v)
                    continue;
                if (flag == 'Y')
                    dist[u][v] = d;
            }
        }

        for (int t = 0; t < n; t++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (dist[u][t] == Integer.MAX_VALUE ||
                        dist[t][v] == Integer.MAX_VALUE)
                        continue;
                    int newLen = dist[u][t] + dist[t][v];
                    if (newLen < dist[u][v])
                        dist[u][v] = newLen;
                }
            }
        }

        int maxShortestPath = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == Integer.MAX_VALUE)
                    return -1;
                maxShortestPath = Math.max(maxShortestPath, dist[i][j]);
            }
        }
        return maxShortestPath;
    }
}

class TopCoderEgalitarianism {
    public static void main(String[] args) {
        Egalitarianism e = new Egalitarianism();

        String[] a1 = {"NYN", "YNY", "NYN"};
        System.out.println(e.maxDifference(a1, 10));  // 20

        String[] a2 = {"NN", "NN"};
        System.out.println(e.maxDifference(a2, 1));  // -1

        String[] a3 = {"NNYNNN",
                       "NNYNNN",
                       "YYNYNN",
                       "NNYNYY",
                       "NNNYNN",
                       "NNNYNN"};
        System.out.println(e.maxDifference(a3, 1000));  // 3000
    }
}
