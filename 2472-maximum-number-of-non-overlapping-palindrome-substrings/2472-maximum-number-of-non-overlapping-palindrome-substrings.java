class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        boolean[][] dp = new boolean[n][n];

        // Find all palindromes
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j) &&
                    (len <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        int count = 0;
        int lastEnd = -1;

        // Greedily select the palindrome
        // that ends earliest
        for (int end = 0; end < n; end++) {
            for (int start = lastEnd + 1; start <= end - k + 1; start++) {

                if (dp[start][end]) {
                    count++;
                    lastEnd = end;
                    break;
                }
            }
        }

        return count;
    }
}