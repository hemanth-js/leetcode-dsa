import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        // litterId[i][j] tells which bit belongs to litter at (i,j)
        int[][] litterId = new int[m][n];

        int startX = 0;
        int startY = 0;
        int litterCount = 0;

        // Find starting position and number each litter
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startX = i;
                    startY = j;
                } 
                else if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        // No litter to collect
        if (litterCount == 0) {
            return 0;
        }

        /*
         * mask:
         * 1 = litter still needs to be collected
         * 0 = litter already collected
         *
         * Example: 3 litter
         *
         * 111 -> all 3 remaining
         * 110 -> litter 0 collected
         * 100 -> litter 0 and 1 collected
         * 000 -> all collected
         */
        int fullMask = (1 << litterCount) - 1;

        /*
         * visited[row][col][energy][mask]
         *
         * This tells us whether we have already reached
         * this exact state.
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        /*
         * Each state contains:
         * row
         * column
         * remaining energy
         * remaining litter mask
         */
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[] {
            startX,
            startY,
            energy,
            fullMask
        });

        visited[startX][startY][energy][fullMask] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level
            while (size-- > 0) {

                int[] current = queue.poll();

                int x = current[0];
                int y = current[1];
                int currentEnergy = current[2];
                int mask = current[3];

                // All litter collected
                if (mask == 0) {
                    return moves;
                }

                // No energy means we cannot make another move
                if (currentEnergy == 0) {
                    continue;
                }

                // Try all 4 directions
                for (int d = 0; d < 4; d++) {

                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    // Outside grid
                    if (nx < 0 || nx >= m ||
                        ny < 0 || ny >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nx].charAt(ny) == 'X') {
                        continue;
                    }

                    char nextCell = classroom[nx].charAt(ny);

                    // Every move normally costs 1 energy
                    int nextEnergy = currentEnergy - 1;

                    // R restores energy to maximum
                    if (nextCell == 'R') {
                        nextEnergy = energy;
                    }

                    int nextMask = mask;

                    // If we step on litter, mark it collected
                    if (nextCell == 'L') {

                        int litter = litterId[nx][ny];

                        nextMask = nextMask & ~(1 << litter);
                    }

                    // Have we already visited this exact state?
                    if (!visited[nx][ny][nextEnergy][nextMask]) {

                        visited[nx][ny][nextEnergy][nextMask] = true;

                        queue.offer(new int[] {
                            nx,
                            ny,
                            nextEnergy,
                            nextMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}