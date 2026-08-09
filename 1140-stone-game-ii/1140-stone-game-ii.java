class Solution {
    int[][] dp;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        // Suffix sum
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        return solve(0, 1, piles);
    }

    private int solve(int i, int M, int[] piles) {

        if (i >= n) {
            return 0;
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        // Can take all remaining piles
        if (2 * M >= n - i) {
            return dp[i][M] = suffix[i];
        }

        int best = 0;

        for (int x = 1; x <= 2 * M; x++) {

            // Alice takes x piles
            // Bob then plays optimally and gets the best he can
            int bob = solve(i + x, Math.max(M, x), piles);

            int alice = suffix[i] - bob;

            best = Math.max(best, alice);
        }

        return dp[i][M] = best;
    }
}