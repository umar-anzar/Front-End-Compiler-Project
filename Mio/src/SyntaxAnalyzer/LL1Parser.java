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
                
    // CFG______________________________________________________________________
    
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
        else if (searchFirstSet("ST_BODY")) {
            if (ST_BODY()) {
                return true;
            }
        }
        
        return false;
    }
    private boolean ST_BODY(){return false;}
    private boolean ST_BODY2(){return false;}
    
    //Body----------------------------------------------------------------------?
    private boolean BODY(){return false;}
    
    //Single and Multi Statements-----------------------------------------------?
    private boolean SST(){return false;}
    private boolean MST(){return false;}
    
    //Begin the Main Function---------------------------------------------------?
    private boolean MAIN(){return false;}
    
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
    private boolean TYPE(){return false;}
    private boolean DT_STR(){return false;}
    private boolean ARR_TYPE(){return false;}
    private boolean ARR_TYPE_LIST(){return false;}
    private boolean ACCESS_METH(){return false;}
    
    //Access Modifier-----------------------------------------------------------?
    private boolean ACCESSMOD(){return false;}
    
    //Function Statement--------------------------------------------------------?
    private boolean FN_DEC(){return false;}
    private boolean FN_ST(){return false;}
    private boolean PAR(){return false;}
    private boolean PAR_LIST(){return false;}
    private boolean DT_ID(){return false;}
    
    private boolean RET_TYPE(){return false;}
    private boolean RT_OBJ(){return false;}
    
    private boolean FN_CLASS_DEC(){return false;}
    private boolean IS_ABSTRACT(){return false;}
    private boolean RET_TO_THROW(){return false;}
    private boolean WITH_STATIC(){return false;}
    
    private boolean RET_TYPE_C(){return false;}
    private boolean RET_OBJ_C(){return false;}
    private boolean ACCESSMOD_C(){return false;}
    
    //Class Statement-----------------------------------------------------------?
    private boolean GLOBAL_CLASS(){return false;}
    private boolean CLASS_GLOBAL(){return false;}
    private boolean CLASS_DEC(){return false;}
    private boolean NO_PRIVATE(){return false;}
    private boolean CLASS_PAR(){return false;}
    private boolean INHERIT(){return false;}
    private boolean MULTI_INHERIT(){return false;}
    
    //Class Body----------------------------------------------------------------?
    private boolean CLASS_BODY(){return false;}
    private boolean ATTR_FUNC(){return false;}
    
    //Dot Separated Identifers, Function calls, array subscripts----------------?
    private boolean POS(){return false;}
    private boolean SUBSCRIPT(){return false;}
    private boolean SUBSCRIPT_LIST(){return false;}
    private boolean FN_BRACKETS(){return false;}
    private boolean ARG(){return false;}
    private boolean ARG_LIST(){return false;}
    private boolean EXPR_OBJ(){return false;}
    private boolean DOT_ID(){return false;}
    
    private boolean POS2(){return false;}
    private boolean INC_DEC_DOT(){return false;}
    private boolean DOT_ID2(){return false;}
    
    //Declaration and Initialization--------------------------------------------?
    private boolean DEC(){return false;}
    private boolean ASSIGN_OBJ(){return false;}
    private boolean ARR_SUBSCRIPT(){return false;}
    private boolean VAR_ARR(){return false;}
    private boolean IS_INIT(){return false;}
    private boolean INIT(){return false;}
    private boolean IS_ACMETH(){return false;}
    private boolean OPER_TO_EXPR(){return false;}
    private boolean ASSIGN_EXPR(){return false;}
    private boolean DOT_EXPR(){return false;}
    private boolean DOT_EXPR2(){return false;}
    private boolean ID_TO_EXPR(){return false;}
    private boolean LIST(){return false;}
    private boolean ASSIGN_OP(){return false;}
    
    //Assignment----------------------------------------------------------------?
    private boolean ASSIGN(){return false;}
    private boolean DOT_ID3(){return false;}
    private boolean DOT_ID4(){return false;}
    private boolean TWO_ASSIGN(){return false;}
    
    //Object Declaration--------------------------------------------------------?
    private boolean NEW_OBJ(){return false;}
    private boolean CONSTR_ARR(){return false;}
    
    //Array Declaration---------------------------------------------------------?
    private boolean ARR_DEC(){return false;}
    private boolean ARR_CLASS_DEC(){return false;}
    private boolean ARR_INIT(){return false;}
    private boolean IS_ARR_INIT(){return false;}
    private boolean CHOICE(){return false;}
    private boolean NEW_ARR_CONST(){return false;}
    private boolean REF_NEWARR(){return false;}
    private boolean POSARR(){return false;}
    private boolean DOT_ARR(){return false;}
    private boolean DOT_ARR_TRMIN(){return false;}
    private boolean MORE_REF_STR(){return false;}
    
    private boolean DIM_PASS(){return false;}
    
    private boolean MUL_ARR_DEC(){return false;}
    private boolean LEN_OF_ARR(){return false;}
    private boolean EMP_ARR_DEC(){return false;}
    private boolean MUL_ARR_DEC2(){return false;}
    private boolean LEN_OF_ARR2(){return false;}
    private boolean EMP_ARR_DEC2(){return false;}
    
    private boolean ARR_CONST(){return false;}
    private boolean ARR_ELEMT(){return false;}
    private boolean EXPR_LIST(){return false;}
    
    //Global Variable Declaration-----------------------------------------------?
    private boolean GLOBAL_DEC(){return false;}
    private boolean IS_OBJ_G(){return false;}
    private boolean VAR_OBJ_G(){return false;}
    private boolean VAR_ARR_G(){return false;}
    private boolean IS_INIT_G(){return false;}
    private boolean LIST_G(){return false;}
    
    //Attribute Declaration in class--------------------------------------------?
    private boolean ATTR_CLASS_DEC(){return false;}
    private boolean IS_FINAL(){return false;}
    private boolean VAR_OBJ_C(){return false;}
    private boolean VAR_ARR_C(){return false;}
    private boolean IS_INIT_C(){return false;}
    private boolean LIST_C(){return false;}
    
    //Expression----------------------------------------------------------------?
    private boolean EXPR(){return false;}
    private boolean EXPR1(){return false;}
    private boolean F(){return false;}
    private boolean F1(){return false;}
    private boolean G(){return false;}
    private boolean G1(){return false;}
    private boolean H(){return false;}
    private boolean H1(){return false;}
    private boolean I(){return false;}
    private boolean I1(){return false;}
    private boolean J(){return false;}
    private boolean J1(){return false;}
    private boolean K(){return false;}
    private boolean IS_FLAG(){return false;}
    
    //Operands------------------------------------------------------------------?
    private boolean OPERANDS(){return false;}
    private boolean UNARY(){return false;}
    private boolean FLAG(){return false;}
    
    //Increment Decrement-------------------------------------------------------?
    private boolean INC_DEC(){return false;}
    
    //Constant------------------------------------------------------------------?
    private boolean CONST(){return false;}
    
    //Conditional Statements----------------------------------------------------?
    private boolean IF_ELSE(){return false;}
    private boolean OELSE(){return false;}
    
    private boolean SWITCH(){return false;}
    private boolean STATE(){return false;}
    private boolean DEFAULT(){return false;}
    private boolean SWITCH_BODY(){return false;}
    
    //Loop Statements-----------------------------------------------------------?
    private boolean LOOP(){return false;}
    private boolean LT(){return false;}
    
    private boolean WHILE_ST(){return false;}
    private boolean DO_WHILE(){return false;}
    
    private boolean FOR_ST(){return false;}
    private boolean FOR_ARG(){return false;}
    
    private boolean POS3(){return false;}
    private boolean DOT_ID5(){return false;}
    
    //Jump Statements-----------------------------------------------------------?
    private boolean BREAK(){return false;}
    private boolean CONTINUE(){return false;}
    private boolean L(){return false;}
    
    private boolean RET_ST(){return false;}
    
    private boolean THROW(){return false;}
    //Exception Handler---------------------------------------------------------?
    private boolean TRY_CATCH(){return false;}
    private boolean EXCEPT_FINALLY(){return false;}
    private boolean ERROR_TYPE(){return false;}
    private boolean ERR_DOT(){return false;}
    private boolean THROWS(){return false;}
    private boolean FINALLY(){return false;}
    
}
