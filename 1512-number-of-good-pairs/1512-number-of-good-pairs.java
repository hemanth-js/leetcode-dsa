class Solution {
    public int numIdenticalPairs(int[] nums) {
        int pair = 0;
        int[] freq = new int[101];

        for (int num : nums) {
            pair += freq[num];
            freq[num]++;
        }

        return pair;
    }
}