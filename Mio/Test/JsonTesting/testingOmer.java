/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JsonTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

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

        


        
        
        //System.out.println(Pattern.compile("^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$").matcher("6.").matches());
    }
}
