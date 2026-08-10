class Solution {
    public int maximumWealth(int[][] accounts) {
        int rich=0;
        for(int i=0;i<accounts.length;i++){
            int wealth=0;
            for(int j=0;j<accounts[i].length;j++){
                wealth+=accounts[i][j];
                if(wealth>rich){
                    rich=wealth;
                }
            }

        }
        return rich;
    }
}
