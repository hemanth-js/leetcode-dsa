import java.util.Arrays;

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        
        // Count total frequencies of characters in s
        int[] totalCount = new int[26];
        for (char c : s.toCharArray()) {
            totalCount[c - 'a']++;
        }
        
        // Target prefix frequency tracker
        int[] prefixCount = new int[26];
        
        // Iterate from right to left to find the longest valid matching prefix
        for (int i = n - 1; i >= 0; i--) {
            // Re-count the exact frequencies needed for target[0 ... i-1]
            Arrays.fill(prefixCount, 0);
            boolean validPrefix = true;
            
            for (int j = 0; j < i; j++) {
                int charIdx = target.charAt(j) - 'a';
                prefixCount[charIdx]++;
                if (prefixCount[charIdx] > totalCount[charIdx]) {
                    validPrefix = false;
                    break;
                }
            }
            
            // If the prefix target[0 ... i-1] cannot even be formed, 
            // no point in looking for a greater character at index i.
            if (!validPrefix) {
                continue;
            }
            
            // Calculate remaining available characters left after forming the prefix
            int[] remainingCount = new int[26];
            for (int k = 0; k < 26; k++) {
                remainingCount[k] = totalCount[k] - prefixCount[k];
            }
            
            // Find the smallest character greater than target.charAt(i)
            int targetCharIdx = target.charAt(i) - 'a';
            int replacementCharIdx = -1;
            for (int k = targetCharIdx + 1; k < 26; k++) {
                if (remainingCount[k] > 0) {
                    replacementCharIdx = k;
                    break;
                }
            }
            
            // If a valid strictly greater character is found, build the result
            if (replacementCharIdx != -1) {
                StringBuilder result = new StringBuilder();
                
                // 1. Append the matching prefix
                result.append(target, 0, i);
                
                // 2. Append the strictly greater character
                result.append((char) ('a' + replacementCharIdx));
                remainingCount[replacementCharIdx]--;
                
                // 3. Append all remaining characters in sorted ascending order
                for (int k = 0; k < 26; k++) {
                    while (remainingCount[k] > 0) {
                        result.append((char) ('a' + k));
                        remainingCount[k]--;
                    }
                }
                return result.toString();
            }
        }
        
        return "";
    }
}