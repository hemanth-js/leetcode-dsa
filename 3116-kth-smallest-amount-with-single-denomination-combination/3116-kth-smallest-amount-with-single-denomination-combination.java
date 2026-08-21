class Solution {
    public long findKthSmallest(int[] coins, int k) {

        long left = 1;
        long right = (long) 2e11;

        while (left < right) {

            long mid = left + (right - left) / 2;

            if (count(mid, coins) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public long count(long x, int[] coins) {

        long ans = 0;
        int n = coins.length;

        // Try every subset of coins
        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {

                    bits++;

                    lcm = lcm(lcm, coins[i]);

                    // No multiple of this LCM can be <= x
                    if (lcm > x) {
                        break;
                    }
                }
            }

            if (lcm > x) {
                continue;
            }

            if (bits % 2 == 1) {
                ans += x / lcm;
            } else {
                ans -= x / lcm;
            }
        }

        return ans;
    }

    public long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}