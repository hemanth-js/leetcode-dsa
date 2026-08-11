class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> result = new ArrayList<>();

        int max = candies[0];

        // Find maximum candies
        for (int i = 1; i < candies.length; i++) {
            max = Math.max(max, candies[i]);
        }

        // Check each kid
        for (int i = 0; i < candies.length; i++) {
            result.add(candies[i] + extraCandies >= max);
        }

        return result;
    }
}