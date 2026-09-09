class Solution {
    public long countCommas(long n) {

        long a = 0;

        if (n < 1000) {
            return a;
        }

        a += n - 999;

        if (n >= 1000000) {
            a += n - 999999;
        }

        if (n >= 1000000000) {
            a += n - 999999999;
        }

        if (n >= 1000000000000L) {
            a += n - 999999999999L;
        }

        if (n >= 1000000000000000L) {
            a += n - 999999999999999L;
        }

        return a;
    }
}