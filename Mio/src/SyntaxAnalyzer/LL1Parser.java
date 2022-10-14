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
    
    private int index = 0;
    List <TokenClass> tokenList;
    //Selection Set
    private HashMap<String, String[][]> sSet;

    /**
     * Constructor Initializes Selection Set HashMap
     */
    public LL1Parser() {
        initializeSelectionSet();
    }

    
    /**
     * Validate function takes TokenList and parse through CFG
     * @param wordList TokenList
     * @return 
     */
    public boolean validate(List<TokenClass> wordList) {
        this.tokenList = wordList;
        boolean result = false;
        
        if (searchFirstSet("START")){
            if ( START() ) 
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
    
    /**
     * Get Token from TokenList at position of global index current value
     * @return 
     */
    private String getTokenCP() {
        return this.tokenList.get(index).classP;
    }
    
    /**
     * Search First Set words from Selection Set
     * @param word
     * @param selectionSet
     * @return 
     */
    private boolean searchFirstSet(String selectionSet) {
        String word = getTokenCP();
        for (String cp : sSet.get(selectionSet)[0]) {
            if ( word.equals(cp)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Search Follow Set words from Selection Set when Non Terminal has null
     * @param selectionSet
     * @return 
     */
    private boolean searchFollowSet(String selectionSet) {
        String word = getTokenCP();
        for (String cp : sSet.get(selectionSet)[1]) {
            if ( word.equals(cp)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * If token match with the grammar word then index is incremented and
     * function return true.
     * @param nonTerminal
     * @return 
     */
    private boolean match(String nonTerminal) {
        if (getTokenCP().equals(nonTerminal)) {
            index++;
            return true;
        }
        return false;
    }
    
    //selectionSet
    private void initializeSelectionSet(){
        //Start Structure
        sSet = new HashMap<>();
        
        sSet.put("START", new String[][] {{"package"},{"~"}});
        sSet.put("ST1", new String[][] {{"import", "Begin", "def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        sSet.put("ST_BODY", new String[][] {{"Begin", "def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        sSet.put("ST_BODY2", new String[][] {{"def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        
        //Body
        sSet.put("BODY", new String[][] {{";", "if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "Ret", "Cont", "raise", "{"}});
        
        //Single and Multi Statements
        sSet.put("SST", new String[][] {{"if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "Cont", "raise"}});
        sSet.put("MST", new String[][] {{"if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "Cont", "raise"},{"state", "default", "}"}});

        //Begin the Main Function
        sSet.put("MAIN", new String[][] {{"begin"}});
        
        //Package and Import
        sSet.put("PACKAGE", new String[][] {{"package"}});
        sSet.put("IMPORTS", new String[][] {{"import"}});
        sSet.put("IMP_DOT", new String[][] {{"dot", ";"}});
        sSet.put("ID_STAR", new String[][] {{"id", "power", ";"}});
    }
                
    // CFG
    
    // DUMMY NON TERMINAL FUNCTION    
    // CFG
    
    // DUMMY NON TERMINAL FUNCTION        
    // CFG
    
    // DUMMY NON TERMINAL FUNCTION    
    // CFG
    
    // DUMMY NON TERMINAL FUNCTION
    private boolean DUMMY() {
        //IF PRODUCTION RULES START FROM NON TERMINAL THEN SEARCHFIRSTSET FUNCTION IS USED
        //IF THERE IS NULL THEN IN THE ELSE BLOCK SEARCHFOLLOWSET IS USED
        if (searchFirstSet("NONTERMINAL")) 
        {
            //Recursive descent code
        } else {
            if (searchFollowSet("NONTERMINAL"))
                return true;
        }
        
        return false;
    }
    
    //Start Structure-----------------------------------------------------------?
    
    private boolean START() {
        if (searchFirstSet("PACKAGE")) {
            if (PACKAGE()) 
                if (ST1()) 
                    return true;
                
        } 
        else {
            if (searchFollowSet("START")) 
                return true;
        }

        return false;
    }
    private boolean ST1() {
        if (searchFirstSet("IMPORTS")) {
            if (IMPORTS())
                if (ST1())
                    return true;
        } 
                
                

        else {
            if (searchFollowSet("ST1"))
                return true;
        }

        return false;
    }
    private boolean ST_BODY(){return false;}
    private boolean ST_BODY2(){return false;}
    
    //Body----------------------------------------------------------------------?
    
    //Single and Multi Statements-----------------------------------------------?
    
    //Begin the Main Function---------------------------------------------------?
    
    //Package and Import--------------------------------------------------------?
    private boolean PACKAGE() {
        if (match("package")) {
            if (match("id"))
                if (IMP_DOT()) 
                    return true; 
        }
        
        return false;
    }
    private boolean IMPORTS() {
        if (match("import")) {
            if (match("id"))
                if (IMP_DOT())
                    return true;
        }
        
        return false;
    }
    private boolean IMP_DOT() {
        if (match("dot")) {
            if (ID_STAR())
                return true;
            
        } 
        else if (match(";"))
            return true;
        

        return false;
    }
    private boolean ID_STAR() {

        if (match("id")){
            if (IMP_DOT()) 
                return true;
            
        } 
        else if (match("power")) {
            if (match(";"))
                return true;
        } 
        else if (match(";")){
            return true;
        }
        
        
        return false;
    }

    //Reusable CFG--------------------------------------------------------------?
    
    //Access Modifier-----------------------------------------------------------?
    
    //Function Statement--------------------------------------------------------?
    
    //Class Statement-----------------------------------------------------------?
    
    //Class Body----------------------------------------------------------------?
    
    //Dot Separated Identifers, Function calls, array subscripts----------------?
    
    //Declaration and Initialization--------------------------------------------?
    
    //Assignment----------------------------------------------------------------?
    
    //Object Declaration--------------------------------------------------------?
    
    //Array Declaration---------------------------------------------------------?
    
    //Global Variable Declaration-----------------------------------------------?
    
    //Attribute Declaration in class--------------------------------------------?
    
    //Expression----------------------------------------------------------------?
    
    //Operands------------------------------------------------------------------?
    
    //Increment Decrement-------------------------------------------------------?
    
    //Constant------------------------------------------------------------------?
    
    //Conditional Statements----------------------------------------------------?
    
    //Loop Statements-----------------------------------------------------------?
    
    //Jump Statements-----------------------------------------------------------?
    
    //Exception Handler---------------------------------------------------------?
}
