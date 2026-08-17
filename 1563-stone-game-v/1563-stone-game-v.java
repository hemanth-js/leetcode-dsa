class Solution {
    int[][] dp;
    int[] prefix;

    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        dp = new int[n][n];
        prefix = new int[n + 1];

        // Prefix sum
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        return solve(stoneValue, 0, n - 1);
    }

    private int solve(int[] stones, int i, int j) {

        // Only one stone
        if (i >= j) {
            return 0;
        }

        // Already calculated
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        int ans = 0;

        int leftSum = 0;
        int rightSum = prefix[j + 1] - prefix[i];

        for (int k = i; k < j; k++) {

            leftSum += stones[k];
            rightSum -= stones[k];

            if (leftSum < rightSum) {

                // Right side is removed
                ans = Math.max(
                    ans,
                    leftSum + solve(stones, i, k)
                );

            } 
            else if (leftSum > rightSum) {

                // Left side is removed
                ans = Math.max(
                    ans,
                    rightSum + solve(stones, k + 1, j)
                );

            } 
            else {

                // Equal -> Alice can choose either side
                ans = Math.max(
                    ans,
                    Math.max(
                        leftSum + solve(stones, i, k),
                        rightSum + solve(stones, k + 1, j)
                    )
                );
            }

            /*
             * Pruning:
             * Once leftSum becomes greater than rightSum,
             * rightSum will only become smaller as k increases.
             */
            if (leftSum > rightSum && ans >= 2 * rightSum) {
                break;
            }
        }

        return dp[i][j] = ans;
    }
}