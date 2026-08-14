class Solution {
    public int maximumLengthSubstring(String s) {
        HashMap<Character, Integer> freq = new HashMap<>();

        int left = 0;
        int ans = 0;

        for (int i= 0; i< s.length(); i++) {

            char ch = s.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);

            while (freq.get(ch) > 2) {
                char remove = s.charAt(left);

                freq.put(remove, freq.get(remove) - 1);

                left++;
            }

            ans = Math.max(ans, i- left + 1);
        }

        return ans;
    }
}