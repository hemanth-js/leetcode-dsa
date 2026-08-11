class Solution {
    public int largestAltitude(int[] gain) {
        int max=0;
        int alltitude=0;
        for(int i=0;i<gain.length;i++){
            alltitude+=gain[i];
            if(max<alltitude){
                max=alltitude;
            }
        }
        return max;
    }
}