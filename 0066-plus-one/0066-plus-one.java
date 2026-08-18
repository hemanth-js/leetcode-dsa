class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length-1;
        digits[n]++;
        for(int i=n;i>0;i--){
            if(digits[i]==10){
                digits[i]=0;
                digits[i-1]++;
            }
        }
        if(digits[0]==10){
            int[] ans = new int[n+2];
            ans[0]=1;
            return ans;
        }
        return digits;
    }
}