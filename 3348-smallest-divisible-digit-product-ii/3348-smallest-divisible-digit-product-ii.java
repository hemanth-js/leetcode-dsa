import java.util.*;

class Solution {

    // factor[d] = {number of 2s, 3s, 5s, 7s in digit d}
    int[][] factor = {
        {0, 0, 0, 0}, // 0
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {

        // --------------------------------------------------
        // 1. Factorize t
        // --------------------------------------------------

        int[] primes = {2, 3, 5, 7};
        int[] need = new int[4];

        for (int i = 0; i < 4; i++) {

            while (t % primes[i] == 0) {
                need[i]++;
                t /= primes[i];
            }
        }

        // If t has any prime factor other than 2,3,5,7
        if (t != 1) {
            return "-1";
        }

        int n = num.length();

        // --------------------------------------------------
        // 2. Prefix factor counts
        // --------------------------------------------------

        int[][] prefix = new int[n + 1][4];

        // hasZero[i] = whether num[0...i-1] contains zero
        boolean[] hasZero = new boolean[n + 1];

        for (int i = 0; i < n; i++) {

            int digit = num.charAt(i) - '0';

            hasZero[i + 1] =
                hasZero[i] || digit == 0;

            for (int j = 0; j < 4; j++) {

                prefix[i + 1][j] =
                    prefix[i][j] + factor[digit][j];
            }
        }

        // --------------------------------------------------
        // 3. Check if num itself is already valid
        // --------------------------------------------------

        if (!hasZero[n] && satisfies(prefix[n], need)) {
            return num;
        }

        // --------------------------------------------------
        // 4. Try to find answer with same length
        // --------------------------------------------------

        for (int pos = n - 1; pos >= 0; pos--) {

            // If prefix contains zero, it cannot be used
            if (hasZero[pos]) {
                continue;
            }

            int originalDigit = num.charAt(pos) - '0';

            // Try the smallest possible greater digit
            for (int digit = originalDigit + 1;
                 digit <= 9;
                 digit++) {

                int[] remaining = new int[4];

                for (int j = 0; j < 4; j++) {

                    remaining[j] = Math.max(
                        0,
                        need[j]
                        - prefix[pos][j]
                        - factor[digit][j]
                    );
                }

                int slots = n - pos - 1;

                // Can remaining slots provide the factors?
                if (minDigits(remaining) <= slots) {

                    String suffix =
                        buildSmallest(remaining, slots);

                    StringBuilder ans =
                        new StringBuilder();

                    // Original prefix
                    ans.append(num, 0, pos);

                    // Changed digit
                    ans.append(digit);

                    // Smallest possible suffix
                    ans.append(suffix);

                    return ans.toString();
                }
            }
        }

        // --------------------------------------------------
        // 5. Same length impossible.
        // Need a longer number.
        // --------------------------------------------------

        int minLen = minDigits(need);

        if (minLen == Integer.MAX_VALUE) {
            return "-1";
        }

        int length = Math.max(n + 1, minLen);

        return buildSmallest(need, length);
    }

    // ------------------------------------------------------
    // Check whether have contains all required factors
    // ------------------------------------------------------

    private boolean satisfies(int[] have, int[] need) {

        for (int i = 0; i < 4; i++) {

            if (have[i] < need[i]) {
                return false;
            }
        }

        return true;
    }

    // ------------------------------------------------------
    // Minimum number of digits required to provide factors
    //
    // req = 2^a * 3^b * 5^c * 7^d
    //
    // 5 can ONLY come from digit 5
    // 7 can ONLY come from digit 7
    //
    // For 2 and 3:
    //
    // 2 -> digit 2
    // 2^2 -> digit 4
    // 2^3 -> digit 8
    // 3 -> digit 3
    // 3^2 -> digit 9
    // 2*3 -> digit 6
    // ------------------------------------------------------

    private int minDigits(int[] req) {

        int a = req[0]; // number of 2s
        int b = req[1]; // number of 3s
        int c = req[2]; // number of 5s
        int d = req[3]; // number of 7s

        int maxSix = Math.min(a, b);

        int answer = Integer.MAX_VALUE;

        /*
         * k = number of digit 6s.
         *
         * We only need to check k = 0..5.
         *
         * Because:
         *
         * f(k + 6) = f(k) + 1
         *
         * so going another 6 steps can never improve
         * the answer.
         */

        int limit = Math.min(5, maxSix);

        for (int k = 0; k <= limit; k++) {

            int remaining2 = a - k;
            int remaining3 = b - k;

            // 8 gives three 2s
            int digitsFor2 =
                (remaining2 + 2) / 3;

            // 9 gives two 3s
            int digitsFor3 =
                (remaining3 + 1) / 2;

            int current =
                k
                + digitsFor2
                + digitsFor3
                + c
                + d;

            answer =
                Math.min(answer, current);
        }

        return answer;
    }

    // ------------------------------------------------------
    // Construct lexicographically smallest number
    // of exactly 'slots' digits satisfying req.
    // ------------------------------------------------------

    private String buildSmallest(int[] req, int slots) {

        StringBuilder ans =
            new StringBuilder(slots);

        for (int pos = 0; pos < slots; pos++) {

            int left = slots - pos - 1;

            /*
             * Try digits from smallest to largest.
             *
             * 1 is allowed because it doesn't change
             * the digit product.
             */

            for (int digit = 1; digit <= 9; digit++) {

                int[] next = new int[4];

                for (int j = 0; j < 4; j++) {

                    next[j] = Math.max(
                        0,
                        req[j] - factor[digit][j]
                    );
                }

                /*
                 * If the remaining positions can still
                 * satisfy the remaining factors, choose
                 * this digit.
                 */

                if (minDigits(next) <= left) {

                    ans.append((char) ('0' + digit));

                    req = next;

                    break;
                }
            }
        }

        return ans.toString();
    }
}