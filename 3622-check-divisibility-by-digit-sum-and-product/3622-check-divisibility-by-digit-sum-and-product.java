class Solution {
    public boolean checkDivisibility(int n) {
        int temp=n;
        int sum=0;
        int prdt=1;
        int total=0;
        while(n>0){
        int rem = n%10;
        sum+=rem;
        prdt*=rem;
        n/=10;
        }
        total=sum+prdt;
        if(temp%total!=0){
            return false;
        }
        return true;
    }
}