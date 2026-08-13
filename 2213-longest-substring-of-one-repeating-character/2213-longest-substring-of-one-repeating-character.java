class Solution {

    char[] s;

    int[] len;
    int[] pref;
    int[] suff;
    int[] best;

    char[] leftChar;
    char[] rightChar;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        this.s = s.toCharArray();

        int n = s.length();

        len = new int[4 * n];
        pref = new int[4 * n];
        suff = new int[4 * n];
        best = new int[4 * n];

        leftChar = new char[4 * n];
        rightChar = new char[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            // Change the character
            this.s[index] = ch;

            // Update segment tree
            update(1, 0, n - 1, index);

            // Root contains the overall longest length
            ans[i] = best[1];
        }

        return ans;
    }

    // Build the segment tree
    void build(int node, int l, int r) {

        if (l == r) {
            len[node] = 1;
            pref[node] = 1;
            suff[node] = 1;
            best[node] = 1;

            leftChar[node] = s[l];
            rightChar[node] = s[l];

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        merge(node);
    }

    // Update one position
    void update(int node, int l, int r, int index) {

        if (l == r) {
            len[node] = 1;
            pref[node] = 1;
            suff[node] = 1;
            best[node] = 1;

            leftChar[node] = s[index];
            rightChar[node] = s[index];

            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index);
        } else {
            update(node * 2 + 1, mid + 1, r, index);
        }

        merge(node);
    }

    // Merge two children
    void merge(int node) {

        int left = node * 2;
        int right = node * 2 + 1;

        len[node] = len[left] + len[right];

        leftChar[node] = leftChar[left];
        rightChar[node] = rightChar[right];

        // Prefix
        pref[node] = pref[left];

        if (pref[left] == len[left] &&
            rightChar[left] == leftChar[right]) {

            pref[node] = len[left] + pref[right];
        }

        // Suffix
        suff[node] = suff[right];

        if (suff[right] == len[right] &&
            rightChar[left] == leftChar[right]) {

            suff[node] = len[right] + suff[left];
        }

        // Best from either side
        best[node] = Math.max(best[left], best[right]);

        // Best crossing the middle
        if (rightChar[left] == leftChar[right]) {

            best[node] = Math.max(
                best[node],
                suff[left] + pref[right]
            );
        }
    }
}