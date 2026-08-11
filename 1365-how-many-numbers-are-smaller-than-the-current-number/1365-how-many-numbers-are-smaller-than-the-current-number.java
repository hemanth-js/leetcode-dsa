class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] ans=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            int count=0;
            int max=nums[i];
            for(int j=0;j<nums.length;j++){
                if(max>nums[j]){
                    count++;
                    }
            }
            ans[i]=count;
        }
        return ans;
    }
}