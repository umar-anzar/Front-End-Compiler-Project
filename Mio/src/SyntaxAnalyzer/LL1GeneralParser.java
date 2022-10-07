/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SyntaxAnalyzer;

import LexicalAnalyzer.TokenClass;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Umar, Izhan, Muqsit
 */
public class LL1GeneralParser {
    
    String start;
    HashMap<String, String[][]> cfg;
    HashMap<String, String[]> ss;
    boolean theEnd = false;

    int index = 0;
    List <TokenClass> wordList;

    /**
     * 
     * @param start start terminal of grammar
     * @param contextFreeGrammar    context free grammar in the form of hash map
     * @param selectionSet  Selection Set of the grammar
     */
    public LL1GeneralParser(String start, 
                            HashMap<String, String[][]> contextFreeGrammar, 
                            HashMap<String, String[]> selectionSet) {
        
        this.start = start;
        this.cfg = contextFreeGrammar;
        this.ss = selectionSet;
    }
    
    /**
     * Reset index and word list so that new word can be parse
     * @return  void
     */
    private void reset(){
        this.index = 0;
        this.wordList = null;
    }
    
    public boolean validate(List <TokenClass> tokens) {
        this.wordList = tokens;
        return false;
    }
    
    private boolean parse(String start){
        return false;
    }
}
