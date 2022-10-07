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
    
    String startTerminal;
    HashMap<String, String[][]> cfg;
    HashMap<String, String[]> ss;
    boolean theEnd = false;

    int index = 0;
    List <TokenClass> wordList;

    /**
     * 
     * @param start startTerminal token of grammar
     * @param contextFreeGrammar    context free grammar in the form of hash map
     * @param selectionSet  Selection Set of the grammar
     */
    public LL1GeneralParser(String start, 
                            HashMap<String, String[][]> contextFreeGrammar, 
                            HashMap<String, String[]> selectionSet) {
        
        this.startTerminal = start;
        this.cfg = contextFreeGrammar;
        this.ss = selectionSet;
    }
    
    /**
     * Reset index and token list so that new token can be parse
     * @return  void
     */
    private void reset(){
        this.index = 0;
        this.wordList = null;
        this.theEnd = false;
    }
    
    private boolean match(String terminal, int index, String token) {
        return String.valueOf(terminal.charAt(0)).equals(token);
    }
    
    private boolean match(String terminal, String token) {
        return terminal.equals(token);
    }
    
    private boolean match(String terminal, List <TokenClass> word) {
        String token = word.get(this.index).classP;
        return terminal.equals(token);
    }
    
    /**
     * 
     * @param tokens    ArrayList of Tokens or Word that are parse
     * @return result   true or false if tokens list matches with the grammar accordingly
     */
    public boolean validate(List <TokenClass> tokens) {
        this.wordList = tokens;
        boolean result = false;
        
        if ( this.parse(this.startTerminal) ) {
            if ( this.wordList.size() - 1 == this.index ){
                result = true;
            }
        }
        
        this.reset(); //Reset parameters to parse more
        
        return result;
    }
    
    private boolean parse(String nonTerminal){
        
        boolean accept = false;
        String [][] rule = this.cfg.get(nonTerminal); //Select rule
        
        for (String [] OR: rule) {
            
            for (int k = 0; k < OR.length; k++) {
                String token = OR[k];
                
                accept = false;
                
                // if token is non-terminal
                if (this.match(token, 0, "<")) 
                {
                    
                    accept = this.parse(token); // Recursion starts
                    
                    // Non Terminal failed to parse hence *break* to find other rule of same non terminal
                    if ( !(accept) ) {
                        if ( k > 0 ) {this.theEnd = true;}
                        break;
                    }
                }
                else if ( this.match(token, this.wordList) ) 
                {
                    this.index++;
                    accept = true;
                }
                else 
                {
                    if ( this.match(nonTerminal, "null") ) {
                        
                        return false;
                    }
                    
                    // First set of rule is true for word then second or further word false so no backtrack, Failed to parse
                    if ( k > 0 ) {this.theEnd = true;}
                    break;
                }
                
            }
        }
        return false;
    }
}
