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
 * @author  Umar, Izhan, Muqsit
 */
public class LL1Parser {
    
    int index = 0;
    List <TokenClass> tokenList;


    /**
     * 
     * @param wordList
     * @return 
     */
    public boolean validate(List<TokenClass> wordList) {
        this.tokenList = wordList;
        boolean result = false;
        
        if ( START() ) {
            result = ( this.index == this.tokenList.size()-1);
            
        }
        
        if ( !result ) {
            TokenClass token = this.tokenList.get(this.index);
            if (token.error == null) {
                String errorTk = this.tokenList.get(this.index).valueP;
                if ( errorTk == null )
                errorTk = this.tokenList.get(this.index).classP;
            
                System.err.println("Syntax Error, token:"+ errorTk + " on line number: "+ token.line);
            } else {
                System.err.println(token.error +": "+token.valueP+" on line no# "+ token.line);
            }
            
        }
        
        reset();
        return  result;
    }
    
    /**
     * Reset index and terminal list so that new terminal can be parse
     * @return  void
     */
    private void reset() {
        index = 0;
        tokenList = null;
    }
    
    private String getTokenCP(int index) {
        return this.tokenList.get(index).classP;
    }
    
    /**
     * 
     * @param word
     * @param selectionSet
     * @return 
     */
    private boolean search(String word, String [] selectionSet) {
        
        for (String sSet : selectionSet) {
            if ( word.equals(sSet)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean match(String nonTerminal) {
        if (getTokenCP(index).equals(nonTerminal)) {
            index++;
            return true;
        }
        return false;
    }
    
    //Selection Set
    HashMap<String, String[]> selectionSet;
    
    private void initializeSelectionSet(){
        selectionSet.put("START", new String[] {"a"});
        selectionSet.put("START", new String[] { "import", "Begin", "def", "Class", "Abstract", "const", "dt", "id" });
    }
    
    // CFG
    
    // DUMMY NON TERMINAL FUNCTION
    private boolean DUMMY() {
        String[] selectionSet = new String[] {};
        String[] selectionNullSet = new String[] {};
        if (search(getTokenCP(index), selectionSet)) 
        {
            
        }
        
        return false;
    }
    
    //Start Structure-----------------------------------------------------------
    
    private boolean START() {
        
        String[] selectionSet = new String[] {"package"};
        String[] selectionNullSet = new String[] {"~"};
        if (search(getTokenCP(index), selectionSet)) 
        {
            
            if (PACKAGE()) {
                if (ST1()) {
                    return true;
                }
            } 
            
        } 
        else if (search(getTokenCP(index), selectionNullSet)) {
                return true;
        }

        return false;
    }
    private boolean ST1() {
    String[] selectionSet = new String[] {"import", "Begin", " def", " Class", " Abstract", "const", "dt", "id"};
        String[] selectionNullSet = new String[] {"~"};
        if (search(getTokenCP(index), selectionSet)) 
        {
                if (IMPORTS()) {
                    if (ST1()) {
                        return true;
                    }
                }
                
        }
//        else if (search(getTokenCP(index), selectionNullSet)) {
//                return true;
//        }

        return false;
    }
    
    //Package and Import--------------------------------------------------------
    private boolean PACKAGE() {
        String[] selectionSet = new String[] {"package"};
        String[] selectionNullSet = new String[] {};
        if (search(getTokenCP(index), selectionSet)) 
        {
            if (match("package")) {
                if (match("id")) {
                    if (IMP_DOT()) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    
    private boolean IMPORTS() {
        String[] selectionSet = new String[] {"import"};
        String[] selectionNullSet = new String[] {};
        if (search(getTokenCP(index), selectionSet)) 
        {
            if (match("import")) {
                if (match("id")) {
                    if (IMP_DOT()) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean IMP_DOT() {
        String[] selectionSet = new String[] {"dot",";"};
        String[] selectionNullSet = new String[] {};
        if (search(getTokenCP(index), selectionSet)) 
        {
            if (match("dot")) {
                if (ID_STAR()) {
                    return true;
                }
            } else if (match(";")) {
                return true;
            }
        }
        
        return false;
    }

    private boolean ID_STAR() {
        String[] selectionSet = new String[] {"id","power",";"};
        String[] selectionNullSet = new String[] {};
        if (search(getTokenCP(index), selectionSet)) 
        {
            if (match("id")) {
                if (IMP_DOT()) {
                    return true;
                }
            } else if (match("power")) {
                if (match(";")) {
                    return true;
                }
            } else if (match(";")) {
                return true;
            }
        }
        
        return false;
    }

}
