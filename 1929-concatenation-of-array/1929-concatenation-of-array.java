class Solution {
    public int[] getConcatenation(int[] nums) {
     int a = 2*nums.length;
     int[] ans = new int[a];
     for(int i=0;i<a;i++){
        ans[i]=nums[i%nums.length];
     }   
     return ans; 
    }
}