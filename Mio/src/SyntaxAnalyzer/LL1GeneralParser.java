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
    
    String startNonTerminal;
    HashMap<String, String[][]> cfg;
    HashMap<String, String[]> ss;
    boolean theEnd = false;

    int index = 0;
    List <TokenClass> wordList;

    /**
     * 
     * @param startNonTerminal startNonTerminal terminal of grammar
     * @param contextFreeGrammar    context free grammar in the form of hash map
     * @param selectionSet  Selection Set of the grammar
     */
    public LL1GeneralParser(String startNonTerminal, 
                            HashMap<String, String[][]> contextFreeGrammar, 
                            HashMap<String, String[]> selectionSet) {
        
        this.startNonTerminal = startNonTerminal;
        this.cfg = contextFreeGrammar;
        this.ss = selectionSet;
    }
    
    /**
     * Reset index and terminal list so that new terminal can be parse
     * @return  void
     */
    private void reset(){
        this.index = 0;
        this.wordList = null;
        this.theEnd = false;
    }
    
    /**
     * 
     * @param terminal
     * @param index
     * @param token
     * @return 
     */
    private boolean match(String terminal, int index, String token) {
        return String.valueOf(terminal.charAt(0)).equals(token);
    }
    
    /**
     * Use for matching tokens from CFG to check whether it is non terminal or null
     * @param terminal
     * @param token
     * @return 
     */
    private boolean match(String terminal, String token) {
        return terminal.equals(token);
    }
    
    /**
     * Use for matching tokens from CFG with syntax words
     * @param terminal
     * @param word
     * @return 
     */
    private boolean match(String terminal, List <TokenClass> word) {
        String token = word.get(this.index).classP;
        return terminal.equals(token);
    }
    
    /**
     * 
     * @param word
     * @param selectionSet
     * @return 
     */
    private boolean search(List <TokenClass> word, String [] selectionSet) {

        String token = word.get(this.index).classP;
        
        for (String sSet : selectionSet) {
            if ( token.equals(sSet)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return True if syntax match with the grammar otherwise it return false
     * @param tokens    ArrayList of Tokens or Word that are parse
     * @return result   true or false if tokens list matches with the grammar accordingly
     */
    public boolean validate(List <TokenClass> tokens) {
        this.wordList = tokens;
        boolean result = false;
        
        if ( this.parse(this.startNonTerminal) ) {
            if ( this.wordList.size() - 1 == this.index ){
                result = true;
            }
        }
        
        // if result is false then print error before returning
        if ( !result ) {
            TokenClass token = this.wordList.get(this.index);
            String errorTk = this.wordList.get(this.index).valueP;
            if ( errorTk == null )
                errorTk = this.wordList.get(this.index).classP;
            System.err.println("Syntax Error: "+ errorTk + " on line number: "+ token.line);
        }
        
        this.reset(); //Reset parameters to parse more
        
        return result;
    }
    
    /**
     * 
     * @param nonTerminal Non terminal to choose the production rule
     * @return true or false according to the syntax
     */
    private boolean parse(String nonTerminal){
        
        boolean accept = false;
        String [][] rule = this.cfg.get(nonTerminal); //Select rule
        String [] selectionSet = this.ss.get(nonTerminal);
        
        // if current word not present in selection set of the non terminal then return false
        if ( ! this.search(wordList, selectionSet) )
            return false;

        
        for (String [] OR: rule) {
            
            for (int k = 0; k < OR.length; k++) {
                String terminal = OR[k];
                
                accept = false;

                // if terminal is actually non-terminal
                if (this.match(terminal, 0, "<")) 
                {
                    accept = this.parse(terminal); // Recursion starts
                    
                    // Non Terminal failed to parse hence *break* to find other rule of same non terminal
                    if ( !(accept) ) {
                        
                        // First set of rule is true for word then second or further word false so no backtrack, Failed to parse
                        if ( k > 0 )
                            this.theEnd = true;
                        
                        break;
                    }
                }
                else if ( this.match(terminal, this.wordList) ) 
                {
                    ++this.index;
                    accept = true;
                }
                else 
                {
                    
                    // Word can be neglected if the terminal is actually null else break to find other rule of same Non terminal
                    if ( this.match(terminal, "null") )
                        // return True instead break because it is end condition hence just returns True
                        return true;

                    // First set of rule is true for word then second or further word false so no backtrack, Failed to parse
                    if ( k > 0 ) 
                        this.theEnd = true;

                    break;
                }
                
            }
            // This condition is on mean word wont parse so straightly Return False
            if (this.theEnd)
                return false;

            /* this return True in order to skip searching in Next OR (production rule of same Non Terminal)
            and it is also the here to give True if word parse correctly */
            if (accept)
                return true;
        }
        
        return false;
    }
}
