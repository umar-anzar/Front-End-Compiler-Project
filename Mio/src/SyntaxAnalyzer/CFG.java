/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SyntaxAnalyzer;

import LexicalAnalyzer.TokenClass;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class CFG {
    
    static HashMap<String, String[][]> cfg;
    static HashMap<String, String[]> selectionSet;
    
    public static void main(String[] args) {
        TokenClass.loadToken(); // load tokens from file
        
        CFG.getCFG();
        CFG.getSelectionSet();
        
        var parser = new LL1GeneralParser("<X>", cfg, selectionSet);
        System.out.println(parser.validate(TokenClass.tokenList));
    }
    
    
    static HashMap<String, String[]> getSelectionSet() {
        selectionSet = new HashMap<>();
        selectionSet.put("<X>", new String[]  {"{","~"}  );
        selectionSet.put("<S>", new String[]  {"{"} );
        selectionSet.put("<A>", new String[]  {"dt"}  );
        selectionSet.put("<D>", new String[]  {"=", "+=", "*=","{","}"}  );
        selectionSet.put("<B>", new String[]  {"=", "+=", "*="}  );
        selectionSet.put("<C>", new String[]  {"intConst", "floatConst"}  );
//        {
//            "<X>" : ['{','$'],
//
//            "<S>" : ['{'],
//
//            "<A>" : ['dt'],
//
//            "<D>" : ["=", "+=", "*=",'{',"}"],
//
//            "<B>" : ["=", "+=", "*="],
//
//            "<C>" : ["intConst", "floatConst"]
//}
        return selectionSet;
    }
    
    static HashMap<String, String[][]> getCFG() {
        
        cfg = new HashMap<>();
        cfg.put("<X>", new String[][] { {"<S>",";"} } );
        cfg.put("<S>" , new String[][] { {"{","<A>","}"} } );
        cfg.put("<A>", new String[][] { {"dt","id","<D>"} } );
        cfg.put("<D>" , new String[][] { {"<B>","<C>"}, {"<S>"},{"null"} } );
        cfg.put("<B>", new String[][] { {"="},{"+="},{"*="} } );
        cfg.put("<C>", new String[][] { {"intConst"}, {"floatConst"} } );
        
        
        
        
        return cfg;
    }
    
}
