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
    //CTRL + SHIFT + - TO MINIMIZE FUNCTIONS AND COMMENTS
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
     * @return true if syntax/ TokenList match with Grammar
     */
    public boolean validate(List<TokenClass> wordList) {
        this.tokenList = wordList;
        boolean result = false;
        
        if (searchSelectionSet("START")){
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
    private boolean searchSelectionSet(String selectionSet) {
        String word = getTokenCP();
        for (String cp : sSet.get(selectionSet)[0]) {
            if ( word.equals(cp)) {
                return true;
            }
        }
        for (String cp : sSet.get(selectionSet)[1]) {
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
     * @return Boolean value, true if match
     */
    private boolean match(String nonTerminal) {
        if (getTokenCP().equals(nonTerminal)) {
            index++;
            return true;
        }
        return false;
    }
    /**
     * If token value part match with the grammar word then index is incremented and
     * function return true.
     * @param nonTerminal
     * @return Boolean value, true if match
     */
    private boolean matchVp(String nonTerminal) {
        String token = this.tokenList.get(index).valueP;
        if (token != null) {
            if (token.equals(nonTerminal)) {
                index++;
                return true;
            }
        }
        return false;
    }
    
    //selectionSet
    private void initializeSelectionSet(){
        
        sSet = new HashMap<>();
        
        //$Start Structure
        sSet.put("START", new String[][] {{"package"},{"~"}});
        sSet.put("ST1", new String[][] {{"import", "Begin", "def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        sSet.put("ST_BODY", new String[][] {{"Begin", "def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        sSet.put("ST_BODY2", new String[][] {{"def", "Class", "Abstract", "const", "dt", "id"},{"~"}});
        
        //$Body
        sSet.put("BODY", new String[][] {{";", "if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "Ret", "Cont", "raise", "{"},{}});
        
        //$Single and Multi Statements
        sSet.put("SST", new String[][] {{"if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "Cont", "raise"},{}});
        sSet.put("MST", new String[][] {{"if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "Cont", "raise"},{"state", "default", "}"},{}});

        //$Begin the Main Function
        sSet.put("MAIN", new String[][] {{"begin"},{}});
        
        //$Package and Import
        sSet.put("PACKAGE", new String[][] {{"package"},{}});
        sSet.put("IMPORTS", new String[][] {{"import"},{}});
        sSet.put("IMP_DOT", new String[][] {{"dot", ";"},{}});
        sSet.put("ID_STAR", new String[][] {{"id", "power", ";"},{}});
        
        //$Reusable CFG
        sSet.put("TYPE", new String[][] {{"id", "dt", "str"}, {} });
        sSet.put("DT_STR", new String[][] {{"dt", "str"}, {} });
        sSet.put("ARR_TYPE", new String[][] {{"["}, {} });
        sSet.put("ARR_TYPE_LIST", new String[][] { {"["},{"id", "protected", "private"} });
        sSet.put("ACCESS_METH", new String[][] {{"Parent", "Self"}, {}});
        
        
        //$Access Modifier
        sSet.put("ACCESSMOD", new String[][] { {"protected", "private"}, {"id"} });
        
        //$Function Statement
        sSet.put("FN_DEC", new String[][] { {"def"}, {}  });
        sSet.put("FN_ST", new String[][] { {"("} });
        sSet.put("PAR", new String[][] { {"id", "dt", "str", ")"}, {}  });
        sSet.put("PAR_LIST", new String[][] { {",", ")"}, {}  });
        sSet.put("DT_ID", new String[][] { {"id", "dt", "str"}, {}  });
        
        sSet.put("RET_TYPE", new String[][] { {"dt", "str", "id"}, {}  });
        sSet.put("RT_OBJ", new String[][] { {"[", "Id"}, {"("} });
        
        sSet.put("FN_CLASS_DEC", new String[][] { {"def"}, {}  });
        sSet.put("IS_ABSTRACT", new String[][] { {"Abstract", "const", "Static"}, {}  });
        sSet.put("RET_TO_THROW", new String[][] { {"dt", "str", "id", "private", "protected"}, {}  });
        sSet.put("WITH_STATIC", new String[][] { {"Static", "dt", "str", "id", "private", "protected"}, {}  });
        
        sSet.put("RET_TYPE_C", new String[][] { {"dt", "str", "id", "private", "protected"}, {}  });
        sSet.put("RET_OBJ_C", new String[][] { {"[", "private", "protected", "Id"}, {"("} });
        sSet.put("ACCESSMOD_C", new String[][] { {"private", "protected"}, {} });
        
        //$Class Statement
        sSet.put("GLOBAL_CLASS", new String[][] { {"Class", "Abstract", "const"}, {} });
        sSet.put("CLASS_GLOBAL", new String[][] { {"Class", "id", "dt", "str"}, {} });
        sSet.put("CLASS_DEC", new String[][] { {"Class"}, {} });
        sSet.put("NO_PRIVATE", new String[][] { {"protected"}, {"id"} });
        sSet.put("CLASS_PAR", new String[][] { {"<"}, {"("} });
        sSet.put("INHERIT", new String[][] { {"id", ")"}, {} });
        sSet.put("MULTI_INHERIT", new String[][] { {",", ")"}, {} });
        
        //?Class Body
        //?Dot Separated Id, FC, AR subscripts
        //?Declaration and Initialization
        //?Assignment
        //?Object Declaration
        //?Array Declaration
        
        //$Global Variable Declaration
        sSet.put("GLOBAL_DEC", new String[][] { {"dt", "id"}, {} });
        sSet.put("IS_OBJ_G", new String[][] { {"dt", "id"}, {} });
        sSet.put("VAR_OBJ_G", new String[][] { {"id", "dt", "str"}, {} });
        sSet.put("VAR_ARR_G", new String[][] { {"[", "id", "=", ";"}, {} });
        sSet.put("IS_INIT_G", new String[][] { {"=", ";"}, {} });
        sSet.put("LIST_G", new String[][] { {",", ";"}, {} });

        //?Attribute Declaration in class
        //?Expression
        //?Operands
        //?Increment Decrement
        //?Constant
        //?Conditional Statements
        //?Loop Statements
        //?Jump Statements
        //?Exception Handler

    }

        

                
    // CFG______________________________________________________________________
    
    // DUMMY NON TERMINAL FUNCTION
    private boolean DUMMY() {
        //IF PRODUCTION RULES START FROM NON TERMINAL ONLY THEN SEARCHFIRSTSET FUNCTION IS USED
        //IF THERE IS NULL THEN IN THE ELSE BLOCK SEARCHFOLLOWSET IS USED
        if (searchSelectionSet("NONTERMINAL")) 
        {
            //Recursive descent code
        } else {
            if (searchFollowSet("NONTERMINAL")) {
                return true;
            }
        }
        
        return false;
    }
    
    //Start Structure-----------------------------------------------------------&
    
    private boolean START() {
        if (searchSelectionSet("PACKAGE")) {
            if (PACKAGE()) { 
                if (ST1()) { 
                    return true;  
                }
            }
        } 
        else {
            if (searchFollowSet("START"))  {
                return true;
            }
        }

        return false;
    }
    private boolean ST1() {
        if (searchSelectionSet("IMPORTS")) {
            if (IMPORTS()) {
                if (ST1()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ST_BODY")) {
            if (ST_BODY()) {
                return true;
            }
        }
        
        return false;
    }
    private boolean ST_BODY() {
        if (searchSelectionSet("MAIN")) {
            if (MAIN()) {
                if (ST_BODY2()) {
                    return true; 
                }
            }
        }
        else if (searchSelectionSet("FN_DEC")) {
            if (FN_DEC()) {
                if (ST_BODY()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("GLOBAL_CLASS")) {
            if (GLOBAL_CLASS()) {
                if (ST_BODY()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("GLOBAL_DEC")) {
            if (GLOBAL_DEC()) {
                if (ST_BODY()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("ST_BODY")) {
                return true;
            }
        }
        return false;
    }
    private boolean ST_BODY2() {
        if (searchSelectionSet("FN_DEC")) {
            if (FN_DEC()) {
                if (ST_BODY2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("GLOBAL_CLASS")) {
            if (FN_DEC()) {
                if (ST_BODY2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("GLOBAL_DEC")) {
            if (GLOBAL_DEC()) {
                if (ST_BODY2()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("ST_BODY2")) {
                return true;
            }
        }
        return false;
    }
    
    //Body----------------------------------------------------------------------$
    private boolean BODY() { 
        if (match(";")) {
            return true;
        }
        else if (searchSelectionSet("SST")) {
            if (SST()) {
                return true;
            }
        }
        else if (match("{")) {
            if (searchSelectionSet("MST")) {
                if (MST()) {
                    if (match("}")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //Single and Multi Statements-----------------------------------------------?
    private boolean SST() {return false;}
    private boolean MST() {return false;}
    
    //Begin the Main Function---------------------------------------------------$
    private boolean MAIN() {
        if (match("begin")) {
            if (match("{")) {
                if (MST()) {
                    if (match("}")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //Package and Import--------------------------------------------------------$
    private boolean PACKAGE() {
        if (match("package")) {
            if (match("id")) {
                if (IMP_DOT()) { 
                    return true; 
                }
                
            }
        }
        
        return false;
    }
    private boolean IMPORTS() {
        if (match("import")) {
            if (match("id")) {
                if (IMP_DOT()) { 
                    return true; 
                }
            }
        }
        
        return false;
    }
    private boolean IMP_DOT() {
        if (match("dot")) {
            if (ID_STAR()) {
                return true;
            }
        } 
        else if (match(";")) {
            return true;
        }
        

        return false;
    }
    private boolean ID_STAR() {

        if (match("id")){
            if (IMP_DOT()) { 
                return true; 
            }
        } 
        else if (match("power")) {
            if (match(";")) {
                return true;
            }
        } 
        else if (match(";")){
            return true;
        }
          
        return false;
    }

    //Reusable CFG--------------------------------------------------------------$
    private boolean TYPE() {
        if (match("id")) {
            return true;
        }
        else if (match("dt")) {
            return true;
        }
        else if (match("str")) {
            return true;
        }
        return false;
    }
    private boolean DT_STR() {
        if (match("dt")) {
            return true;
        }
        else if (match("str")) {
            return true;
        }
        return false;
    }
    private boolean ARR_TYPE() {
        if (match("[")) {
            if (match("]")) {
                if (ARR_TYPE_LIST()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean ARR_TYPE_LIST() {
        if (match("[")) {
            if (match("]")) {
                if (ARR_TYPE_LIST()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("ARR_TYPE_LIST")) {
                return true;
            }
        }
        return false;
    }
    private boolean ACCESS_METH() {
        if (match("Parent")) {
            if (match("dot")) {
                return true;
            }
        }
        else if (match("Self")) {
            if (match("dot")) {
                return true;
            }
        }
        return false;
    }
    
    //Access Modifier-----------------------------------------------------------$
    private boolean ACCESSMOD() {
        if (match("protected")) {
            return true;
        }
        else if (match("private")) {
            return true;
        }
        else {
            if (searchFollowSet("ACCESSMOD")) {
                return true;
            }
        }
        return false;
    }
    
    //Function Statement--------------------------------------------------------?
    private boolean FN_DEC() {
        if (match("def")) {
            if (RET_TYPE()) {
                if (FN_ST()) {
//                    if (THROWS()) {
                        if (match("{")) {
//                            if (MST()) {
                                if (match("}")) {
                                    return true;
                                }
//                            }
                        }
//                    }
                }
            }
        }
        return false;
    }
    private boolean FN_ST() {
        if (match("(")) {
            if (PAR()) {
                return true;
            }
        }
        return false;
    }
    private boolean PAR() {
        if (searchSelectionSet("DT_ID")) {
            if (DT_ID()) {
                if (match("id")) {
                    if (PAR_LIST()) {
                        return true;
                    }
                }
            }
        }
        else if (match(")")) {
            return true;
        }
        return false;
    }
    private boolean PAR_LIST() {
        if (match(",")) {
            if (DT_ID()) {
                if (match("id")) {
                    if (PAR_LIST()) {
                        return true;
                    }
                }
            }
        }
        else if (match(")")) {
            return true;
        }
        return false;
    }
    private boolean DT_ID() {
        if (searchSelectionSet("TYPE")) {
            if (TYPE()) {
                if (ARR_TYPE_LIST()) {
                    return true;
                } 
            }
        }
        return false;
    }
    
    private boolean RET_TYPE() {
        if (searchSelectionSet("DT_STR")) {
            if (DT_STR()) {
                if (ARR_TYPE_LIST()) {
                    if (match("id")) {
                        return true;
                    }
                }
            }
        }
        else if (match("id")) {
            if (RT_OBJ()) {
                return true;
            }
        }
        return false;
    }
    private boolean RT_OBJ() {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE()) {
                if (match("id")) {
                    return true;
                }
            }
        }
        else if (match("id")) {
            return true;
        }
        else {
            if (searchFollowSet("RT_OBJ")) {
                return true;
            }
        }
        return false;
    }
    
    private boolean FN_CLASS_DEC() {return false;}
    private boolean IS_ABSTRACT() {
        if (match("Abstract")) {
            if (WITH_STATIC()) {
                if (match(";")) {
                    return true;
                }
            }
        }
        else if (match("const")) {
            if (WITH_STATIC()) {
                if (match("{")) {
                    if (MST()) {
                        if (match("}")) {
                            return true;
                        }
                    }
                }
            }
        }
        else if (searchSelectionSet("WITH_STATIC")) {
            if (WITH_STATIC()) {
                if (match("{")) {
                    if (MST()) {
                        if (match("}")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean RET_TO_THROW() {return false;}
    private boolean WITH_STATIC() {return false;}
    
    private boolean RET_TYPE_C(){return false;}
    private boolean RET_OBJ_C(){return false;}
    private boolean ACCESSMOD_C(){return false;}
    
    //Class Statement-----------------------------------------------------------?
    private boolean GLOBAL_CLASS() {
        if (searchSelectionSet("CLASS_DEC")) {
            if (CLASS_DEC()) {
                return true;
            }
        }
        else if (match("Abstract")) {
            if (CLASS_DEC()) {
                return true;
            }
        }
//        else if (match("const")) {
//            if (CLASS_GLOBAL()) {
//                return true;
//            }
//        }
        return false;
    }
    private boolean CLASS_GLOBAL() {
        if (searchSelectionSet("CLASS_DEC")) {
            if (CLASS_DEC()) {
                return true;
            }
        }
        else if (searchSelectionSet("VAR_OBJ_G")) {
            if (VAR_OBJ_G()) {
                return true;
            }
        }
        return false;
    }
    private boolean CLASS_DEC() {
        if (match("Class")) {
            if (NO_PRIVATE()) {
                if (match("id")) {
                    if (CLASS_PAR()) {
                        if (match("(")) {
                            if (INHERIT()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean NO_PRIVATE() {
        if (match("protected")) {
            return true;
        }
        else {
            if (searchFollowSet("NO_PRIVATE")) {
                return true;
            }
        }
        return false;
    }
    private boolean CLASS_PAR() {
        if (matchVp("<")) {
            if (match("id")) {
                if (matchVp(">")) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("CLASS_PAR")) {
                return true;
            }
        }
        return false;
    }
    private boolean INHERIT(){
        if (match("id")) {
            if (MULTI_INHERIT()) {
                return true;
            }
        }
        else if (match(")")) {
            if (match("{")) {
                if (CLASS_BODY()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean MULTI_INHERIT(){
        if (match(",")) {
            if (match("id")) {
                if (MULTI_INHERIT()) {
                    return true;
                }
            }
        }
        else if (match(")")) {
            if (match("{")) {
                if (CLASS_BODY()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Class Body----------------------------------------------------------------?
    private boolean CLASS_BODY(){
//        if (searchSelectionSet("ATTR_FUNC")) {
//            if (ATTR_FUNC()) {
//                if (CLASS_BODY()) {
//                    return true;
//                }
//            }
//        }
//        else 
            if (match("}")) {
                return true;
        }
        return false;
    }
    private boolean ATTR_FUNC(){
        if (searchSelectionSet("FN_CLASS_DEC")) {
            if (FN_CLASS_DEC()) {
                return true;
            }
        }
        else if (searchSelectionSet("ATTR_CLASS_DEC")) {
            if (ATTR_CLASS_DEC()) {
                return true;
            }
        }
        return false;
    }
    
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
    private boolean THROWS(){
        System.out.println("asdasd");
        return false;
    }
    private boolean FINALLY(){return false;}
    
}
