package com.example.cp;

import java.util.HashMap;

public class Tags {
    String tags[]={"2-sat","binary search","bitmasks","brute force","chinese remainder theorem",
            "combinatorics","constructive algorithms","data structures","dfs and similar",
            "divide and conquer","dp","dsu","expression parsing","fft","flows","games",
            "geometry","graph matchings","graphs","greedy","hashing","implementation",
            "interactive","math","matrices","meet-in-the-middle","number theory","probabilities",
            "schedules","shortest paths","sortings","string suffix structures","strings",
            "ternary search","trees","two pointers","*special"
    };
     HashMap<String,Integer> keys=new HashMap<>();
    public Tags(){
        for(int i=0;i<tags.length;i++){
            keys.put(tags[i],i);
        }
    }
    public static int[] convertarray(String tagsi){
        String t[]=tagsi.split("[|]");
        int[] arr=new int[t.length];
        int i=0;
        for (String str : t)
            arr[i++] = Integer.parseInt(str);

        return arr;
    }

}
