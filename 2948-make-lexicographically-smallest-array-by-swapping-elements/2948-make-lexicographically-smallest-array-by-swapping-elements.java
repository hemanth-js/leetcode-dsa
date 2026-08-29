import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // [value, original index]
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        // Sort by value
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        int[] ans = new int[n];

        int start = 0;

        while (start < n) {

            int end = start;

            // Find the group
            while (end + 1 < n &&
                   arr[end + 1][0] - arr[end][0] <= limit) {
                end++;
            }

            // Collect original indices
            int[] indices = new int[end - start + 1];

            for (int i = start; i <= end; i++) {
                indices[i - start] = arr[i][1];
            }

            // Sort original indices
            Arrays.sort(indices);

            // Put smallest values into smallest indices
            for (int i = 0; i < indices.length; i++) {
                ans[indices[i]] = arr[start + i][0];
            }

            start = end + 1;
        }

        return ans;
    }
}