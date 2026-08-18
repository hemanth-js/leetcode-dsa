class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // Case 1: k = 1
        if (k == 1) {
            int[] freq = new int[51];

            for (int num : nums) {
                freq[num]++;
            }

            int ans = -1;

            for (int num : nums) {
                if (freq[num] == 1) {
                    ans = Math.max(ans, num);
                }
            }

            return ans;
        }

        // Case 2: k = n
        if (k == n) {
            int ans = 0;

            for (int num : nums) {
                ans = Math.max(ans, num);
            }

            return ans;
        }

        // Case 3: 1 < k < n
        int first = nums[0];
        int last = nums[n - 1];

        int firstCount = 0;
        int lastCount = 0;

        for (int num : nums) {
            if (num == first) {
                firstCount++;
            }

            if (num == last) {
                lastCount++;
            }
        }

        int ans = -1;

        if (firstCount == 1) {
            ans = Math.max(ans, first);
        }

        if (lastCount == 1) {
            ans = Math.max(ans, last);
        }

        return ans;
    }
}