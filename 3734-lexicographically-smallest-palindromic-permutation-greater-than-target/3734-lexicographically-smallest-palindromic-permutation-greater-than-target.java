import java.util.*;

public class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int half = n / 2;
        int[] cnt = new int[26];
        
        // Count frequencies of each character
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        
        // Verify if a palindrome configuration is possible
        int oddCount = 0;
        int midChar = -1;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 != 0) {
                oddCount++;
                midChar = i;
            }
        }
        
        // If more than one character has an odd count, a palindrome cannot be formed
        if (oddCount > 1) {
            return "";
        }
        
        char[] res = new char[n];
        if (dfs(0, half, cnt, midChar, target, res, false)) {
            return new String(res);
        }
            
        return "";
    }
    
    private boolean dfs(int idx, int half, int[] cnt, int midChar, String target, char[] res, boolean isGreater) {
        // Base case: First half of the palindrome has been successfully constructed
        if (idx == half) {
            if (res.length % 2 != 0) {
                res[half] = (char) (midChar + 'a');
            }
            
            // If it hasn't been strictly confirmed greater yet, check the middle/right half
            if (!isGreater) {
                for (int i = half; i < res.length; i++) {
                    if (res[i] > target.charAt(i)) {
                        return true;
                    } else if (res[i] < target.charAt(i)) {
                        return false;
                    }
                }
                return false; // Equal to target, not strictly greater
            }
            return true;
        }
        
        // Determine the starting character to check
        int start = isGreater ? 0 : (target.charAt(idx) - 'a');
        
        for (int c = start; c < 26; c++) {
            if (cnt[c] >= 2) {
                cnt[c] -= 2;
                res[idx] = (char) (c + 'a');
                res[res.length - 1 - idx] = (char) (c + 'a');
                
                boolean nextGreater = isGreater || (c > (target.charAt(idx) - 'a'));
                
                // Optimization: If already strictly greater, fill the rest greedily with smallest possible chars
                if (nextGreater) {
                    if (fillGreedily(idx + 1, half, cnt, midChar, res)) {
                        cnt[c] += 2;
                        return true;
                    }
                } else {
                    if (dfs(idx + 1, half, cnt, midChar, target, res, nextGreater)) {
                        cnt[c] += 2;
                        return true;
                    }
                }
                cnt[c] += 2; // Backtrack
            }
        }
        return false;
    }
    
    private boolean fillGreedily(int idx, int half, int[] cnt, int midChar, char[] res) {
        int c = 0;
        for (int i = idx; i < half; i++) {
            while (c < 26 && cnt[c] < 2) {
                c++;
            }
            if (c == 26) return false;
            res[i] = (char) (c + 'a');
            res[res.length - 1 - i] = (char) (c + 'a');
            cnt[c] -= 2;
        }
        
        if (res.length % 2 != 0) {
            res[half] = (char) (midChar + 'a');
        }
        
        // Restore character counts for backtracking step
        for (int i = idx; i < half; i++) {
            cnt[res[i] - 'a'] += 2;
        }
        return true;
    }
}