import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // Store: [left, right, weight, originalIndex]
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by ending position
        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1])
                return Integer.compare(a[1], b[1]);
            return Integer.compare(a[0], b[0]);
        });

        // End positions for binary search
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            ends[i] = arr[i][1];
        }

        /*
         * dp[i][k] = maximum weight using first i intervals
         *            and choosing at most k intervals.
         */
        long[][] dp = new long[n + 1][5];

        /*
         * best[i][k] = indices chosen for dp[i][k]
         */
        int[][][] best = new int[n + 1][5][];

        for (int i = 1; i <= n; i++) {

            int left = arr[i - 1][0];
            int weight = arr[i - 1][2];
            int originalIndex = arr[i - 1][3];

            // Find last interval whose end < current start
            int previous = findPrevious(ends, i - 1, left);

            for (int k = 1; k <= 4; k++) {

                // Option 1: don't take current interval
                dp[i][k] = dp[i - 1][k];
                best[i][k] = best[i - 1][k];

                // Option 2: take current interval
                long takeScore = dp[previous + 1][k - 1] + weight;

                int[] previousIndices = best[previous + 1][k - 1];

                int[] takeIndices = addIndex(previousIndices, originalIndex);

                if (takeScore > dp[i][k] ||
                    (takeScore == dp[i][k] &&
                     isLexicographicallySmaller(takeIndices, best[i][k]))) {

                    dp[i][k] = takeScore;
                    best[i][k] = takeIndices;
                }
            }
        }

        return best[n][4];
    }

    // Find the last interval with end < start
    private int findPrevious(int[] ends, int high, int start) {

        int low = 0;
        int ans = -1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (ends[mid] < start) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    // Add an index and keep the result sorted
    private int[] addIndex(int[] old, int index) {

        int size = old == null ? 0 : old.length;

        int[] result = new int[size + 1];

        if (old != null) {
            System.arraycopy(old, 0, result, 0, size);
        }

        result[size] = index;

        Arrays.sort(result);

        return result;
    }

    // Compare two index arrays lexicographically
    private boolean isLexicographicallySmaller(int[] a, int[] b) {

        if (b == null)
            return true;

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] != b[i])
                return a[i] < b[i];
        }

        return a.length < b.length;
    }
}