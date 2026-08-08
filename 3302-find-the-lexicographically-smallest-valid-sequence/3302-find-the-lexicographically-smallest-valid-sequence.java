class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        char[] a = word1.toCharArray();
        char[] b = word2.toCharArray();

        // dp[i] = maximum number of characters of word2
        // that can be matched exactly using word1[i...]
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && a[i] == b[j]) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        // Greedily choose the smallest possible index
        while (i < n && j < m) {

            // Exact match: take it
            if (a[i] == b[j]) {
                ans[j] = i;
                j++;
            }

            // Mismatch: use our one allowed modification
            else {
                // Can the remaining characters be matched exactly?
                int remaining = m - j - 1;

                if (dp[i + 1] >= remaining) {
                    ans[j] = i;
                    j++;
                    i++;

                    // Now the one mismatch has been used.
                    break;
                }
            }

            i++;
        }

        // Match the remaining characters exactly
        while (j < m && i < n) {
            if (a[i] == b[j]) {
                ans[j] = i;
                j++;
            }
            i++;
        }

        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}