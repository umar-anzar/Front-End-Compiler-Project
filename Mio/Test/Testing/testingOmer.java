/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author omera
 */
public final class testingOmer {

    public testingOmer() {
        
    }

    public static void main(String[] args) {
        
        HashMap<String, String[][]> cfg = new HashMap<>();
        cfg.put("<X>", new String[][] { {"<S>",";"} } );
        cfg.put("<S>" , new String[][] { {"{","<A>","}"} } );
        cfg.put("<A>", new String[][] { {"dt","id","<D>"} } );
        cfg.put("<D>" , new String[][] { {"<B>","<C>"}, {"<S>"},{"null"} } );
        cfg.put("<B>", new String[][] { {"="},{"+="},{"*="} } );
        cfg.put("<C>", new String[][] { {"intConst"}, {"floatConst"} } );
        
        String word = "a";
        System.out.println(Arrays.toString(word.split(",")));
        ArrayList<String> x = new ArrayList<>();
        System.out.println(1);
        for (String string : word.split(",")) {
            if (string.isEmpty()) {
                x.add(string);
                System.out.println("X");
            } else {
                 x.add(string);
                System.out.println(string);
            }
        }
        
        System.out.println(x);
        System.out.println(new String []{}[0]);
//        HashSet<String> h = new HashSet<>();
//        h.add("A");h.add("A");h.add("B");h.add("C");
//        System.out.println(h.contains("C"));
        


        
        
        //System.out.println(Pattern.compile("^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$").matcher("6.").matches());
    }
}

class ABC {
    public int a = 0;
}
class DEF extends  ABC{
    public int b = 0;
}
class GHI extends DEF{
    public int c = 0;

    public GHI() {
        System.out.println(a);
    }
     
}