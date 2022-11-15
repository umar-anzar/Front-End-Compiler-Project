/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SyntaxAnalyzer;

import LexicalAnalyzer.TokenClass;
import SemanticAnalyzer.RetOutInfo;
import SemanticAnalyzer.SymbolTable;
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
    //SymbolTable
    public SymbolTable ST = new SymbolTable();

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
                System.err.println(token.error +": "+token.classP+"\t"+token.valueP+" on line no# "+ token.line);
            }
        }
        
        emptyAndReset();
        return  result;
    }
    
    /**
     * Reset index and terminal list so that new terminal can be parse
     * @return  void
     */
    private void emptyAndReset() {
        index = 0;
        tokenList = null;
    }
    
    /**
     * Get Token class part from TokenList at position of global index current value
     * @return 
     */
    private String getTokenCP() {
        return this.tokenList.get(index).classP;
    }
    
    /**
     * Get Token value from TokenList at position of global index current value
     * @return 
     */
    public String getTokenVP() {
        if (this.tokenList.get(index).valueP != null) {
            return this.tokenList.get(index).valueP;
        }
        return this.tokenList.get(index).classP;
    }
    
    public int getTokenLine() {
        return this.tokenList.get(index).line;
    }
    
    /**
     * Search First Set words from Selection Set
     * @param word
     * @param selectionSet
     * @return 
     */
    private boolean searchSelectionSet(String selectionSet) {
        String word = getTokenCP();
        try {
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
        } catch (NullPointerException e) {
            System.out.println(word);
            System.out.println(selectionSet);
            String[] error = sSet.get(selectionSet)[0];
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
        try {
            for (String cp : sSet.get(selectionSet)[1]) {
                if ( word.equals(cp)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println(word);
            System.out.println(selectionSet);
            String[] error = sSet.get(selectionSet)[1];
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
        sSet.put("ST1", new String[][] {{"import", "begin", "def", "Class", "Abstract", "const", "id", "dt","str"},{"~"}});
        sSet.put("ST_BODY", new String[][] {{"begin", "def", "Class", "Abstract", "const", "id", "dt","str"},{"~"}});
        sSet.put("ST_BODY2", new String[][] {{"def", "Class", "Abstract", "const", "id", "dt","str"},{"~"}});
        
        //$Body
        sSet.put("BODY", new String[][] {{";", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "cont", "raise", "{"},{}});
        
        //$Single and Multi Statements
        sSet.put("SST", new String[][] {{";", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "cont", "raise"},{}});
        sSet.put("MST", new String[][] {{";", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "cont", "raise"},{"state", "default", "}"},{}});

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
        sSet.put("ACM", new String[][] {{"Parent", "Self"}, {}});
        
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
        sSet.put("IS_ABSTRACT", new String[][] { {"Abstract", "Static", "const", "dt", "str", "id", "private", "protected"}, {}  });
        sSet.put("RET_TO_THROW", new String[][] { {"dt", "str", "id", "private", "protected"}, {}  });
        sSet.put("WITH_FINAL", new String[][] { {"const", "dt", "str", "id", "private", "protected"}, {}  });
        
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
        sSet.put("CLASS_BODY", new String[][] { {"def", "Static", "const", "id", "dt", "str", "}"}, {} });
        sSet.put("ATTR_FUNC", new String[][] { {"def", "Static", "const", "id", "dt", "str"}, {} });
        
        //$Dot Separated Id, FC, AR subscripts
        sSet.put("POS", new String[][] { {"dot", "[", "("}, {"power", "mdm", "pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("SUBSCRIPT", new String[][] { {"["}, {} });
        sSet.put("SUBSCRIPT_LIST", new String[][] { {"["}, {"dot", "[", "(", "inc_dec", "power", "mdm", "pm", "rop", "and", "or", "=", "Cma", "]", ")", ",", "}", ":", ";"} });
        sSet.put("FN_BRACKETS", new String[][] { {"("}, {} });
        sSet.put("ARG", new String[][] { {"pm", "Parent", "Self", "id", "(", "typeCast", "not", "intConst", "floatConst", "charConst", "boolConst", "strConst", "new", "NaN", ")"}, {} });     
        sSet.put("ARG_LIST", new String[][] { {",", ")"}, {} });
        sSet.put("EXPR_OBJ", new String[][] { {"pm", "Parent", "Self", "id", "(", "typeCast", "not", "intConst", "floatConst", "charConst", "boolConst", "strConst", "new", "NaN"}, {} });
        sSet.put("DOT_ID_SUBSCRIPT", new String[][] { {"dot", "["}, {} });
        
        
        sSet.put("POS2", new String[][] { {"inc_dec", "dot", "[", "("}, {"power", "mdm", "pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("INC_DEC_DOT", new String[][] { {"inc_dec", "dot", "["}, {"power", "mdm", "pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("DOT_ID_SUBSCRIPT2", new String[][] { {"dot", "["}, {"power", "mdm", "pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        
        
        //$Declaration and Initialization
        sSet.put("DEC", new String[][] { {"const", "dt", "str", "id", "Parent", "Self"}, {} });
        sSet.put("ID_FN", new String[][] { {"[", "id", "dot", "(", "inc_dec", "=", "cma"}, {} });
        sSet.put("ASSIGN_OBJ", new String[][] { {"id", "dot", "(", "inc_dec", "=", "cma"}, {} });
        sSet.put("ARR_SUBSCRIPT", new String[][] { {"]", "pm", "Parent", "Self", "id", "inc_dec", "(", "typeCast", "not", "intConst", "floatConst", "charConst", "boolConst", "strConst"}, {} });
        sSet.put("VAR_ARR", new String[][] { {"[", "id"}, {} });
        sSet.put("IS_INIT", new String[][] { {",", ";", "="}, {} });
        sSet.put("INIT", new String[][] { {"Parent", "Self", "id", "new", "NaN", "inc_dec", "(", "typeCast", "not", "intConst", "floatConst", "charConst", "boolConst", "strConst", "pm"}, {} });
        sSet.put("IS_ACMETH", new String[][] { {"Parent", "Self"}, {"id"} });
        
        sSet.put("OPER_TO_EXPR", new String[][] { {"inc_dec", "(", "typeCast", "not", "intConst", "floatConst", "charConst", "boolConst", "strConst"}, {} });
        
        sSet.put("ASSIGN_EXPR", new String[][] { {"dot", "[", "power", "mdm", "pm", "rop", "and", "or", "(", "=", "cma", "inc_dec"}, {",", ";"} });
        sSet.put("DOT_EXPR", new String[][] { {"dot", "[", "power", "mdm", "pm", "rop", "and", "or"}, {",", ";"} });
        sSet.put("ID_TO_EXPR", new String[][] { {"power", "mdm", "pm", "rop", "and", "or"}, {",", ";"} });
        sSet.put("LIST", new String[][] { {",", ";"}, {} });
        sSet.put("ASSIGN_OP", new String[][] { {"=", "cma"}, {} });

        //$Assignment
        sSet.put("ASSIGN", new String[][] { {"dot", "(", "inc_dec", "=", "cma"}, {} });
        sSet.put("ASSIGN2", new String[][] { {"dot", "[", "(", "inc_dec", "=", "cma"}, {} });
        sSet.put("FN_TWO_ASSIGN", new String[][] { {"(", "inc_dec", "=", "cma"}, {} });
        sSet.put("DOT_ID3", new String[][] { {"dot", "["}, {} });
        sSet.put("DOT_ID4", new String[][] { {"dot", "[", ";"}, {} });
        sSet.put("TWO_ASSIGN", new String[][] { {"inc_dec", "=", "cma"}, {} });
        
        //$Object Declaration
        sSet.put("NEW_OBJ", new String[][] { {"new", "NaN"}, {} });
        sSet.put("CONSTR_ARR", new String[][] { {"id", "dt", "str"}, {} });
        sSet.put("FN_ARR", new String[][] { {"(", "["}, {} });
        
        //$Array Declaration
        sSet.put("DIM_PASS", new String[][] { {"pm", "Parent", "Self", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst", "]"}, {} });
        
        sSet.put("MUL_ARR_DEC", new String[][] { {"["}, {",", ")", ";"} });
        sSet.put("LEN_OF_ARR", new String[][] { {"pm", "Parent", "Self", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst", "]"}, {} });
        sSet.put("EMP_ARR_DEC", new String[][] { {"[", "{"}, {} });
        sSet.put("EMP_ARR_DEC2", new String[][] { {"["}, {",", ")", ";"} });
        
        sSet.put("ARR_CONST", new String[][] { {"{"}, {} });
        sSet.put("ARR_ELEMT", new String[][] { {"pm", "Parent", "Self", "id", "inc_dec(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst", "{", "}"}, {} });       
        sSet.put("EXPR_LIST", new String[][] { {",", "}"}, {} });
        
        //$Global Variable Declaration
        sSet.put("GLOBAL_DEC", new String[][] { {"dt", "id", "str"}, {} });
        sSet.put("VAR_ARR_G", new String[][] { {"[", "id"}, {} });
        sSet.put("VAR_G", new String[][] { {"id"}, {} });
        sSet.put("IS_INIT_G", new String[][] { {"=", ",", ";"}, {} });
        sSet.put("LIST_G", new String[][] { {",", ";"}, {} });

        //$Attribute Declaration in class
        sSet.put("ATTR_CLASS_DEC", new String[][] { {"Static", "const", "id", "dt", "str"}, {} });
        sSet.put("IS_FINAL", new String[][] { {"const", "id", "dt", "str"}, {} });
        sSet.put("TYPE_VAR_ARR", new String[][] { {"id", "dt", "str"}, {} });
        sSet.put("VAR_ARR_C", new String[][] { {"[", "protected", "private", "id"}, {} });
        sSet.put("VAR_C", new String[][] { {"protected", "private", "id"}, {"def", "Static", "const", "dt", "id", "str", "}"} });
        sSet.put("IS_INIT_C", new String[][] { {"=", ",", ";"}, {} });
        sSet.put("LIST_C", new String[][] { {",", ";"}, {} });
        
        //$Expression
        sSet.put("EXPR", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("EXPR1", new String[][] { {"or"}, {"]", ")", ",", "}", ":", ";"} });
        sSet.put("F", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("F1", new String[][] { {"and"}, {"or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("G", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("G1", new String[][] { {"rop"}, {"and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("H", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("H1", new String[][] { {"pm"}, {"rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("I", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("I1", new String[][] { {"mdm"}, {"pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("J", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("J1", new String[][] { {"power"}, {"mdm", "pm", "rop", "and", "or", "]", ")", ",", "}", ":", ";"} });
        sSet.put("K", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });
        sSet.put("IS_FLAG", new String[][] { {"pm", "Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", 
            "charConst", "boolConst", "strConst"}, {} });

        //$Operands
        sSet.put("OPERANDS", new String[][] { {"Parent", "Self", "inc_dec", "id", "(", "typeCast", "not", "intConst", "floatConst", "charConst", 
            "boolConst", "strConst", "pm"}, {} });
        sSet.put("UNARY", new String[][] { {"typeCast", "not"}, {} });
        sSet.put("FLAG", new String[][] { {"pm"}, {} });
        
        //$Increment Decrement
        sSet.put("INC_DEC", new String[][] { {"inc_dec"}, {} });
        
        //$Constant
        sSet.put("CONST", new String[][] { {"intConst", "floatConst", "charConst", "boolConst", "strConst"}, {} });
        
        //$Conditional Statements
        sSet.put("IF_ELSE", new String[][] { {"if"}, {} });
        sSet.put("OELSE", new String[][] { {"else"}, {";", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", 
            "loop", "do", "stop", "ret", "cont", "raise", "state", "default", "}", "else", "till"} });
        
        sSet.put("SWITCH", new String[][] { {"shift"}, {} });
        sSet.put("STATE", new String[][] { {"state", "default", "}"}, {} });
        sSet.put("DEFAULT", new String[][] { {"default"}, {} });
        sSet.put("SWITCH_BODY", new String[][] { {"{", "; if", "shift", "const", "dt", "id", "Parent", "Self", "test", "loop", 
            "do", "stop", "ret", "cont", "raise", "state", "default", "}"}, {} }); 
        sSet.put("DEFAULT_BODY", new String[][] { {"{", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", "loop", "do", 
            "stop", "ret", "cont", "raise", ";"}, {"}"} });

        //$Loop Statements
        sSet.put("LOOP", new String[][] { {"loop"}, {} });
        sSet.put("LT", new String[][] { {"till", "thru"}, {} });
        
        sSet.put("WHILE_ST", new String[][] { {"till"}, {} });
        sSet.put("DO_WHILE", new String[][] { {"do"}, {} });

        sSet.put("FOR_ST", new String[][] { {"thru"}, {} });
        sSet.put("FOR_ARG", new String[][] { {"id", "("}, {} });
        sSet.put("POS3", new String[][] { {"dot", "[", "("}, {")"} });
        sSet.put("DOT_ID5", new String[][] { {"dot", "[", "("}, {")"} });
        
        //$Jump Statements
        sSet.put("BREAK", new String[][] { {"stop"}, {} });
        sSet.put("CONTINUE", new String[][] { {"cont"}, {} });
        sSet.put("L", new String[][] { {"id", ";"}, {} });
        
        sSet.put("RET_ST", new String[][] { {"ret"}, {} });
        
        sSet.put("THROW", new String[][] { {"raise"}, {} });
        
        //$Exception Handler
        sSet.put("TRY_CATCH", new String[][] { {"test"}, {} });
        sSet.put("EXCEPT_FINALLY", new String[][] { {"except", "Finally"}, {";", "if", "shift", "const", "dt", "str", "id", "Parent", 
            "Self", "test", "loop", "do", "stop", "ret", "cont", "raise", "state", "default", "}", "else", "till"} });
        sSet.put("ERROR_TYPE", new String[][] { {"("}, {} });
        sSet.put("ERR_DOT", new String[][] { {"dot", "id"}, {} });
        sSet.put("THROWS", new String[][] { {"raises"}, {"{", ";"} });
        sSet.put("ID_ERR", new String[][] { {"dot", ","}, {"{", ";"} });
        sSet.put("ERR_DOT_ID", new String[][] { {"dot"}, {"{", ";"} });
        sSet.put("ERR_LIST", new String[][] { {","}, {} }); 
        sSet.put("FINALLY", new String[][] { {"finally"}, {} });

    }

        

                
    // CFG______________________________________________________________________
    
    
    //Start Structure-----------------------------------------------------------$
    
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
            if (GLOBAL_DEC("")) {
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
            if (GLOBAL_CLASS()) {
                if (ST_BODY2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("GLOBAL_DEC")) {
            if (GLOBAL_DEC("")) {
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
        if (searchSelectionSet("SST")) {
            ST.push();
            if (SST()) {
                ST.pop();
                return true;
            }
        }
        else if (match("{")) {
            ST.push();
            index++;
            if (MST()) {
                if (match("}")) {
                    ST.pop();
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    
    //Single and Multi Statements-----------------------------------------------$
    private boolean SST() {
        if (searchSelectionSet("IF_ELSE")) {
            if (IF_ELSE()) {
                return true;
            }
        }
        else if (searchSelectionSet("SWITCH")) {
            if (SWITCH()) {
                return true;
            }
        }
        else if (searchSelectionSet("DEC")) {
            if (DEC()) {
                return true;
            }
        }
        else if (searchSelectionSet("TRY_CATCH")) {
            if (TRY_CATCH()) {
                return true;
            }
        }
        else if (searchSelectionSet("LOOP")) {
            if (LOOP()) {
                return true;
            }
        }
        else if (searchSelectionSet("DO_WHILE")) {
            if (DO_WHILE()) {
                return true;
            }
        }
        else if (searchSelectionSet("BREAK")) {
            if (BREAK()) {
                return true;
            }
        }
        else if (searchSelectionSet("RET_ST")) {
            if (RET_ST()) {
                return true;
            }
        }
        else if (searchSelectionSet("CONTINUE")) {
            if (CONTINUE()) {
                return true;
            }
        }
        else if (searchSelectionSet("THROW")) {
            if (THROW()) {
                return true;
            }
        }
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean MST() {
        if (searchSelectionSet("SST")) {
            if (SST()) {
                if (MST()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("MST")) {
                return true;
            }
        }
        return false;
    }
    
    //Begin the Main Function---------------------------------------------------$
    private boolean MAIN() {
        String N;
        if (match("begin")) {
            N = getTokenVP();
            index++;
            if (match("{")) {
                ST.insertMT(N, "", "", "", "", "", "", getTokenLine());
                ST.push();
                index++;
                if (MST()) {
                    if (match("}")) {
                        ST.pop();
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //Package and Import--------------------------------------------------------$
    private boolean PACKAGE() {
        String T,N;
        
        if (match("package")) {
            T = getTokenVP();
            index++;
            if (match("id")) {
                N = getTokenVP();
                index++;
                RetOutInfo out = new RetOutInfo();
                if (IMP_DOT(out)) { 
                    N += out.NAME;
                    ST.insertMT(N, T, "", "", "", "", "", getTokenLine());
                    return true; 
                }
                
            }
        }
        
        return false;
    }
    private boolean IMPORTS() {
        String N;
        String T;
        if (match("import")) {
            T = getTokenVP();
            index++;
            if (match("id")) {
                N = getTokenVP();
                RetOutInfo out = new RetOutInfo();
                index++;
                if (IMP_DOT(out)) { 
                    N += out.NAME;
                    ST.insertMT(N, T, "", "", "", "", "", getTokenLine());
                    return true; 
                }
            }
        }
        
        return false;
    }
    private boolean IMP_DOT(RetOutInfo out) {
        if (match("dot")) {
            out.NAME = ".";
            index++;
            if (ID_STAR(out)) {
                return true;
            }
        } 
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean ID_STAR(RetOutInfo out) {

        if (match("id")){
            out.NAME += getTokenVP();
            index++;
            if (IMP_DOT(out)) { 
                return true; 
            }
        } 
        else if (match("power")) {
            out.NAME += "^";
            index++;
            if (match(";")) {
                index++;
                return true;
            }
        } 
        else if (match(";")){
            index++;
            return true;
        }
          
        return false;
    }

    //Reusable CFG--------------------------------------------------------------$
    private boolean TYPE(RetOutInfo out) {
        if (match("id")) {
            out.TYPE += getTokenVP();
            index++;
            return true;
        }
        else if (match("dt")) {
            out.TYPE += getTokenVP();
            index++;
            return true;
        }
        else if (match("str")) {
            out.TYPE += getTokenVP();
            index++;
            return true;
        }
        return false;
    }
    private boolean DT_STR(RetOutInfo out) {
        if (match("dt")) {
            out.TYPE = getTokenVP();
            index++;
            return true;
        }
        else if (match("str")) {
            out.TYPE = getTokenVP();
            index++;
            return true;
        }
        return false;
    }
    private boolean ARR_TYPE(RetOutInfo out) {
        if (match("[")) {
            out.TYPE += getTokenVP();
            index++;
            if (match("]")) {
                out.TYPE += getTokenVP();
                index++;
                if (ARR_TYPE_LIST(out)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean ARR_TYPE_LIST(RetOutInfo out) {
        if (match("[")) {
            out.TYPE += getTokenVP();
            index++;
            if (match("]")) {
                out.TYPE += getTokenVP();
                index++;
                if (ARR_TYPE_LIST(out)) {
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
            index++;
            if (match("dot")) {
                index++;
                return true;
            }
        }
        else if (match("Self")) {
            index++;
            if (match("dot")) {
                index++;
                return true;
            }
        }
        return false;
    }
    private boolean ACM() {
        if (match("Parent")) {
            index++;
            return true;
        }
        else if (match("Self")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Access Modifier-----------------------------------------------------------$
    private boolean ACCESSMOD(RetOutInfo out) {
        if (match("protected")) {
            out.ACCESS_MODIFIER = getTokenCP();
            index++;
            return true;
        }
        else if (match("private")) {
            out.ACCESS_MODIFIER = getTokenCP();
            index++;
            return true;
        }
        else {
            if (searchFollowSet("ACCESSMOD")) {
                return true;
            }
        }
        return false;
    }
    
    //Function Statement--------------------------------------------------------$
    private boolean FN_DEC() {
        String N,T,PL;
        RetOutInfo out = new RetOutInfo();
        if (match("def")) {
            index++;
            if (RET_TYPE(out)) {
                T = out.TYPE;
                N = out.NAME;
                out = new RetOutInfo();
                if (FN_ST(out)) {
                    PL = out.TYPE;
                    if (THROWS()) {
                        if (match("{")) {
                            if(!ST.insertMT(N, T, "", PL, "", "", "", getTokenLine()))
                                ST.addError(getTokenLine(), "Redeclaration error",N);
                            index++;
                            if (MST()) {
                                if (match("}")) {
                                    ST.pop();
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean FN_ST(RetOutInfo out) {
        if (match("(")) {
            ST.push();
            index++;
            if (PAR(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean PAR(RetOutInfo out) {
        if (searchSelectionSet("DT_ID")) {
            if (DT_ID(out)) {
                if (match("id")) {
                    out.NAME2 += getTokenVP();
                    index++;
                    if (PAR_LIST(out)) {
                        return true;
                    }
                }
            }
        }
        else if (match(")")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean PAR_LIST(RetOutInfo out) {
        if (match(",")) {
            out.TYPE += getTokenVP();
            out.NAME2 += getTokenVP();
            index++;
            if (DT_ID(out)) {
                if (match("id")) {
                    out.NAME2 += getTokenVP();
                    index++;
                    if (PAR_LIST(out)) {
                        return true;
                    }
                }
            }
        }
        else if (match(")")) {
            String N = out.NAME2,T= out.TYPE;
            ST.insertFT_Param(N, T, getTokenLine());
            index++;
            return true;
        }
        return false;
    }
    private boolean DT_ID(RetOutInfo out) {
        if (searchSelectionSet("TYPE")) {
            if (TYPE(out)) {
                if (ARR_TYPE_LIST(out)) {
                    return true;
                } 
            }
        }
        return false;
    }
    
    private boolean RET_TYPE(RetOutInfo out) {
        if (searchSelectionSet("DT_STR")) {
            if (DT_STR(out)) {
                if (ARR_TYPE_LIST(out)) {
                    if (match("id")) {
                        out.NAME = getTokenVP();
                        index++;
                        return true;
                    }
                }
            }
        }
        else if (match("id")) {
            out.NAME = getTokenVP();
            out.TYPE = getTokenVP();
            index++;
            if (RT_OBJ(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean RT_OBJ(RetOutInfo out) {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    return true;
                }
            }
        }
        else if (match("id")) {
            out.NAME = getTokenVP();
            index++;
            return true;
        }
        else {
            if (searchFollowSet("RT_OBJ")) {
                out.TYPE = "";
                return true;
            }
        }
        return false;
    }
    
    private boolean FN_CLASS_DEC() {
        if (match("def")) {
            index++;
            if (IS_ABSTRACT()) {
                return true;
            }
        }
        return false;
    }
    private boolean IS_ABSTRACT() {
        RetOutInfo out = new RetOutInfo();
        if (match("Abstract")) {
            out.TYPE_MODIFIER = getTokenVP();
            index++;
            if (RET_TO_THROW(out)) {
                if (match(";")) {
                    index++;
                    return true;
                }
            }
        }
        else if (match("Static")) {
            out.STATIC = getTokenVP();
            index++;
            if (WITH_FINAL(out)) {
                String N=out.NAME,T=out.TYPE2,TM=out.TYPE_MODIFIER,
                        PL=out.TYPE,AC=out.ACCESS_MODIFIER,STC=out.STATIC;
                if (match("{")) {
                    if (!ST.insertCT(N, T, TM, PL, AC, STC, getTokenLine())) {
                        ST.addError(getTokenLine(), "Redeclaration error",N);
                    }
                    index++;
                    if (MST()) {
                        if (match("}")) {
                            ST.pop();
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        else if (searchSelectionSet("WITH_FINAL")) {
            if (WITH_FINAL(out)) {
                String N=out.NAME,T=out.TYPE2,TM=out.TYPE_MODIFIER,
                        PL=out.TYPE,AC=out.ACCESS_MODIFIER,STC=out.STATIC;
                if (match("{")) {
                    if (!ST.insertCT(N, T, TM, PL, AC, STC, getTokenLine())) {
                        ST.addError(getTokenLine(), "Redeclaration error",N);
                    }
                    ST.push();
                    index++;
                    if (MST()) {
                        if (match("}")) {
                            ST.pop();
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean RET_TO_THROW(RetOutInfo out) {
        if (searchSelectionSet("RET_TYPE_C")) {
            if (RET_TYPE_C(out)) {
                out.TYPE2 = out.TYPE;
                out.TYPE="";
                if (FN_ST(out)) {
                    if (THROWS()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean WITH_FINAL(RetOutInfo out) {
        if (match("const")) {
            out.TYPE_MODIFIER = getTokenVP();
            index++;
            if (RET_TO_THROW(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("RET_TO_THROW")) {
            if (RET_TO_THROW(out)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean RET_TYPE_C(RetOutInfo out) {
        if (searchSelectionSet("DT_STR")) {
            if (DT_STR(out)) {
                if (ARR_TYPE_LIST(out)) {
                    if (ACCESSMOD(out)) {
                        if (match("id")) {
                            out.NAME = getTokenVP();
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        else if (match("id")) {
            out.TYPE = getTokenVP();
            out.NAME = getTokenVP();
            index++;
            if (RET_OBJ_C(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("ACCESSMOD_C")) {
            if (ACCESSMOD_C(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean RET_OBJ_C(RetOutInfo out) {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE(out)) {
                if (ACCESSMOD(out)) {
                    if (match("id")) {
                        out.NAME = getTokenVP();
                        index++;
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("ACCESSMOD_C")) {
            if (ACCESSMOD_C(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    return true;
                }
            }
        }
        else if (match("id")) {
            out.NAME = getTokenVP();
            index++;
                return true;
        }
        else {
            if (searchFollowSet("RET_OBJ_C")) {
                out.TYPE = "";
                return true;
            }
        }
        return false;
    }
    private boolean ACCESSMOD_C(RetOutInfo out) {
        if (match("private")) {
            out.ACCESS_MODIFIER = getTokenCP();
            index++;
            return true;
        }
        else if (match("protected")) {
            out.ACCESS_MODIFIER = getTokenCP();
            index++;
            return true;
        }
        return false;
    }
    
    //Class Statement-----------------------------------------------------------$
    private boolean GLOBAL_CLASS() {
        String TM="";
        if (searchSelectionSet("CLASS_DEC")) {
            if (CLASS_DEC(TM)) {
                return true;
            }
        }
        else if (match("Abstract")) {
            TM = getTokenVP();
            index++;
            if (CLASS_DEC(TM)) {
                return true;
            }
        }
        else if (match("const")) {
            TM = getTokenVP();
            index++;
            if (CLASS_GLOBAL(TM)) {
                return true;
            }
        }
        return false;
    }
    private boolean CLASS_GLOBAL(String TM) {
        if (searchSelectionSet("CLASS_DEC")) {
            if (CLASS_DEC(TM)) {
                return true;
            }
        }
        else if (searchSelectionSet("GLOBAL_DEC")) {
            if (GLOBAL_DEC(TM)) {
                return true;
            }
        }
        return false;
    }
    private boolean CLASS_DEC(String TM) {
        RetOutInfo out = new RetOutInfo();
        out.TYPE_MODIFIER = TM;
        if (match("Class")) {
            out.TYPE = getTokenVP();
            index++;
//            if (NO_PRIVATE()) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    if (CLASS_PAR(out)) {
                        if (match("(")) {
                            index++;
                            if (INHERIT(out)) {
                                return true;
                            }
                        }
                    }
                }
//            }
        }
        return false;
    }
//    private boolean NO_PRIVATE() {
//        if (match("protected")) {
//            return true;
//        }
//        else {
//            if (searchFollowSet("NO_PRIVATE")) {
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean CLASS_PAR(RetOutInfo out) {
        if (matchVp("<")) {
            index++;
            if (match("id")) {
                out.PARAMETRIC_CLASS = getTokenVP();
                index++;
                if (matchVp(">")) {
                    index++;
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
    private boolean INHERIT(RetOutInfo out) {
        if (match("id")) {
            
            ST.canInhert(getTokenVP(), getTokenLine());
            
            out.EXTEND = getTokenVP();
            index++;
            if (MULTI_INHERIT(out)) {
                return true;
            }
        }
        else if (match(")")) {
            String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER,
                    PC=out.PARAMETRIC_CLASS;
            index++;
            if (match("{")) {
                if(!ST.insertMT(N, T, TM, "", "", PC, "", getTokenLine()))
                        ST.addError(getTokenLine(), "Redeclaration error",N);
                index++;
                if (CLASS_BODY()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean MULTI_INHERIT(RetOutInfo out){
        if (match(",")) {
            out.EXTEND += getTokenVP();
            index++;
            if (match("id")) {
                ST.canInhert(getTokenVP(), getTokenLine());
                out.EXTEND += getTokenVP();
                index++;
                if (MULTI_INHERIT(out)) {
                    return true;
                }
            }
        }
        else if (match(")")) {
            String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER,
                    PC=out.PARAMETRIC_CLASS,EXT=out.EXTEND;
            index++;
            if (match("{")) {
                if(!ST.insertMT(N, T, TM, "", "", PC, EXT, getTokenLine()))
                        ST.addError(getTokenLine(), "Redeclaration error",N);
                index++;
                if (CLASS_BODY()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Class Body----------------------------------------------------------------$
    private boolean CLASS_BODY(){
        if (searchSelectionSet("ATTR_FUNC")) {
            if (ATTR_FUNC()) {
                if (CLASS_BODY()) {
                    return true;
                }
            }
        }
        else 
            if (match("}")) {
                ST.referenceOff();
                index++;
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
    
    //Dot Separated Identifers, Function calls, array subscripts----------------$
    private boolean POS() {
        if (searchSelectionSet("DOT_ID_SUBSCRIPT")) {
            if (DOT_ID_SUBSCRIPT()) {
                return true;
            }
        }
        else if (searchSelectionSet("FN_BRACKETS")) {
            if (FN_BRACKETS(null)) {
                if (DOT_ID_SUBSCRIPT()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("POS")) {
                return true;
            }
        }
        return false;
    }
    private boolean SUBSCRIPT() {
        if (match("[")) {
            index++;
            if (EXPR(null)) {
                if (match("]")) {
                    index++;
                    if (SUBSCRIPT_LIST()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean SUBSCRIPT_LIST() {
        if (searchSelectionSet("SUBSCRIPT")) {
            if (SUBSCRIPT()) {
                return true;
            }
        }
        else {
            if (searchFollowSet("SUBSCRIPT_LIST")) {
                return true;
            }
        }
        return false;
    }
    private boolean FN_BRACKETS(RetOutInfo out) {
        if (match("(")) {
            index++;
            if (ARG()) {
                return true;
            }
        }
        return false;
    }
    private boolean ARG() {
        if (searchSelectionSet("EXPR_OBJ")) {
            if (EXPR_OBJ()) {
                if (ARG_LIST()) {
                    return true;
                }
            }
        }
        else if (match(")")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean ARG_LIST() {
        if (match(",")) {
            index++;
            if (EXPR_OBJ()) {
                if (ARG_LIST()) {
                    return true;
                }
            }
        }
        else if (match(")")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean EXPR_OBJ() {
        if (searchSelectionSet("EXPR")) {
            if (EXPR(null)) {
                return true;
            }
        }
        else if (searchSelectionSet("NEW_OBJ")) {
            if (NEW_OBJ(null)) {
                return true;
            }
        }
        return false;
    }
    private boolean DOT_ID_SUBSCRIPT() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (POS()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("SUBSCRIPT")) {
            if (POS()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean POS2() {
        if (searchSelectionSet("INC_DEC_DOT")) {
            if (INC_DEC_DOT()) {
                return true;
            }
        }
        else if (searchSelectionSet("FN_BRACKETS")) {
            
            if (FN_BRACKETS(null)) {
                if (DOT_ID_SUBSCRIPT2()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean INC_DEC_DOT() {
        if (searchSelectionSet("INC_DEC")) {
            if (INC_DEC()) {
                return true;
            }
        }
        else if (searchSelectionSet("DOT_ID_SUBSCRIPT2")) {
            if (DOT_ID_SUBSCRIPT2()) {
                return true;
            }
        }
        return false;
    }
    private boolean DOT_ID_SUBSCRIPT2() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (POS2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("SUBSCRIPT")) {
            if (SUBSCRIPT()) {
                if (POS2()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("DOT_ID_SUBSCRIPT2")) {
                return true;
            }
        }
        return false;
    }
    
    //Declaration and Initialization--------------------------------------------$
    private boolean DEC() {
        RetOutInfo out = new RetOutInfo();
        if (match("const")) {
            out.TYPE_MODIFIER = getTokenVP();
            index++;
            if (TYPE(out)) {
                if (VAR_ARR(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("DT_STR")) {
            if (DT_STR(out)) {
                if (VAR_ARR(out)) {
                    return true;
                }
            }
        }
        else if (match("id")) {
            out.TYPE = getTokenVP();
            out.NAME = getTokenVP();
            index++;
            if (ASSIGN_OBJ(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("ACM")) {
            if (ACM()) {
                if (ID_FN()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean ID_FN() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (ASSIGN()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("FN_BRACKETS")) {
            if (FN_BRACKETS(null)) {
                if (DOT_ID4()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean ASSIGN_OBJ(RetOutInfo out) {
        if (match("[")) {
            out.TYPE ="";
            index++;
            if (ARR_SUBSCRIPT()) {
                return true;
            }
        }
        else if (match("id")) {
            out.NAME = getTokenVP();
            index++;
            if (IS_INIT(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("ASSIGN")) {
            out.TYPE ="";
            if (ASSIGN()) {
                return true;
            }
        }
        return false;
    }
    private boolean ARR_SUBSCRIPT() {
        if (match("]")) {
            index++;
            if (ARR_TYPE_LIST(null)) {
                if (match("id")) {
                    index++;
                    if (IS_INIT(null)) {
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("EXPR")) {
            if (EXPR(null)) {
                if (match("]")) {
                    index++;
                    if (DOT_ID3()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean VAR_ARR(RetOutInfo out) {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    if (IS_INIT(out)) {
                        return true;
                    }
                }
            }
        }
        else if (match("id")) {
            out.NAME = getTokenVP();
            index++;
            if (IS_INIT(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean IS_INIT(RetOutInfo out) {
        String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER;
        
        if (match("=")) {
            if (!ST.insertFT(N, T, TM, getTokenLine()))
                ST.addError(getTokenLine(), "Redeclaration error",N);
            index++;
            RetOutInfo out1 = new RetOutInfo();
            if (INIT(out1)) {
                //compare
                ST.compareType(T, out1.TYPE, N, getTokenLine());
                if (LIST(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("LIST")) {
            if (!ST.insertFT(N, T, TM, getTokenLine()))
                ST.addError(getTokenLine(), "Redeclaration error",N);
            if (LIST(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean INIT(RetOutInfo out) {
        if (searchSelectionSet("IS_ACMETH")) {
            if (IS_ACMETH()) {
                if (match("id")) {
                    index++;
                    if (ASSIGN_EXPR()) {
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("NEW_OBJ")) {
            if (NEW_OBJ(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("OPER_TO_EXPR")) {
            if (OPER_TO_EXPR(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean IS_ACMETH() {
        if (searchSelectionSet("ACCESS_METH")) {
            if (ACCESS_METH()) {
                return true;
            }
        }
        else {
            if (searchFollowSet("IS_ACMETH")) {
                return true;
            }
        }
        return false;
    }
    
    private boolean OPER_TO_EXPR(RetOutInfo out) {
        if (searchSelectionSet("INC_DEC")) {
            if (INC_DEC()) {
                if (match("id")) {
                    index++;
                    if (POS()) {
                        if (ID_TO_EXPR(null)) {
                            return true;
                        }
                    }
                }
            }
        }
        else if (match("(")) {
            index++;
            if (EXPR(out)) {
                if (match(")")) {
                    index++;
                    if (ID_TO_EXPR(out)) {
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("UNARY")) {
            if (UNARY(out)) {
                RetOutInfo out1 = new RetOutInfo();
                if (OPERANDS(out1)) {
                    if ("!".equals(out.OPERATOR)) {
                        if (!ST.compatibility_op(out1.TYPE, out.OPERATOR)) {
                            ST.addError(getTokenLine(), "Incompatible with the operator ("+out.OPERATOR+")", out.beforeOpName);
                        } else {
                            //if compatible with not operator
                            out.TYPE = out1.TYPE;
                        }
                        
                        out.OPERATOR = "";
                        out.beforeOpName = "";
                    } else {
                        out.TYPE = ST.compatibility_conv(out.TYPE, out1.TYPE, getTokenLine());
                    }
                    if (ID_TO_EXPR(out)) {
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("CONST")) {
            if (CONST(out)) {
                if (ID_TO_EXPR(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("FLAG")) {
            if (FLAG()) {
                if (OPERANDS(null)) {
                    if (ID_TO_EXPR(null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean ASSIGN_EXPR() {
        if (searchSelectionSet("DOT_EXPR")) {
            if (DOT_EXPR()) {
                return true;
            }
        }
        else if (searchSelectionSet("FN_BRACKETS")) {
            if (FN_BRACKETS(null)) {
                if (DOT_EXPR()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ASSIGN_OP")) {
            if (ASSIGN_OP()) {
                if (INIT(null)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("INC_DEC")) {
            if (INC_DEC()) {
                if (ID_TO_EXPR(null)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean DOT_EXPR() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (ASSIGN_EXPR()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("SUBSCRIPT")) {
            if (SUBSCRIPT()) {
                if (ASSIGN_EXPR()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ID_TO_EXPR")) {
            if (ID_TO_EXPR(null)) {
                return true;
            }
        }
        return false;
    }
    private boolean ID_TO_EXPR(RetOutInfo out) {
        if (searchSelectionSet("J1")) {
            if (J1(out)) {
                if (I1(out)) {
                    if (H1(out)) {
                        if (G1(out)) {
                            if (F1(out)) {
                                if (EXPR1(out)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean LIST(RetOutInfo out) {
        if (match(",")) {
            index++;
            if (match("id")) {
                out.NAME = getTokenVP();
                index++;
                if (IS_INIT(out)) {
                    return true;
                }
            }
        }
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean ASSIGN_OP() {
        if (match("=")) {
            index++;
            return true;
        }
        else if (match("cma")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Assignment----------------------------------------------------------------$
    private boolean ASSIGN() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (ASSIGN2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("FN_TWO_ASSIGN")) {
            if (FN_TWO_ASSIGN()) {
                return true; 
            }
        }
        return false;
    }
    private boolean ASSIGN2() {
        if (searchSelectionSet("DOT_ID3")) {
            if (DOT_ID3()) {
                return true;
            }
        }
        else if (searchSelectionSet("FN_TWO_ASSIGN")) {
            if (FN_TWO_ASSIGN()) {
                return true; 
            }
        }
        return false;
    }
    private boolean FN_TWO_ASSIGN() {
        if (searchSelectionSet("FN_BRACKETS")) {
            if (FN_BRACKETS(null)) {
                if (DOT_ID4()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("TWO_ASSIGN")) {
            if (TWO_ASSIGN()) {
                return true; 
            }
        }
        return false;
    }
    private boolean DOT_ID3() {
        if (match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (ASSIGN2()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("SUBSCRIPT")) {
            if (SUBSCRIPT()) {
                if (ASSIGN2()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean DOT_ID4() {
        if (searchSelectionSet("DOT_ID3")) {
            if (DOT_ID3()) {
                return true;
            }
        }
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean TWO_ASSIGN() {
        if (searchSelectionSet("INC_DEC")) {
            if (INC_DEC()) {
                if (match(";")) {
                    index++;
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ASSIGN_OP")) {
            if (ASSIGN_OP()) {
                if (INIT(null)) {
                    if (match(";")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    //Object Declaration--------------------------------------------------------$
    private boolean NEW_OBJ(RetOutInfo out) {
        if (match("new")) {
            out.onConsturctor();
            index++;
            if (CONSTR_ARR(out)) {
                out.offConsturctor();
                return true;
            }
        }
        else if (match("NaN")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean CONSTR_ARR(RetOutInfo out) {
        if (match("id")) {
            String N = getTokenVP();
            if (ST.typeExist(N, getTokenLine())){
                ST.canMakeObj(N, getTokenLine()); }           
            out.NAME = N;
            index++;
            if (FN_ARR(out)) {
                return true; 
            }
        }
        else if (match("dt")) {
            index++;
            if (match("[")) {
                index++;
                if (DIM_PASS()) {
                    return true;
                }
            }
        }
        else if (match("str")) {
            index++;
            if (FN_ARR(null)) {
                return true; 
            }
        }
        return false;
    }
    private boolean FN_ARR(RetOutInfo out) {
        if (searchSelectionSet("FN_BRACKETS")) {
            if (FN_BRACKETS(out)) {
                return true;
            }
        }
        else if (match("[")) {
            index++;
            if (DIM_PASS()) {
                return true;
            }
        }
        return false;
    }
    
    //Array Declaration---------------------------------------------------------$
    
    private boolean DIM_PASS() {
        if (searchSelectionSet("EXPR")) {
            if (EXPR(null)) {
                if (match("]")) {
                    index++;
                    if (MUL_ARR_DEC()) {
                        return true;
                    }
                }
            }
        }
        else if (match("]")) {
            index++;
            if (EMP_ARR_DEC()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean MUL_ARR_DEC() {
        if (match("[")) {
            index++;
            if (LEN_OF_ARR()) {
                return true;
            }
        }
        else {
            if (searchFollowSet("MUL_ARR_DEC")) {
                return true;
            }
        }
        return false;
    }
    private boolean LEN_OF_ARR() {
        if (searchSelectionSet("EXPR")) {
            if (EXPR(null)) {
                if (match("]")) {
                    index++;
                    if (MUL_ARR_DEC()) {
                        return true;
                    }
                }
            }
        }
        else if (match("]")) {
            index++;
            if (EMP_ARR_DEC2()) {
                return true;
            }
        }
        return false;
    }
    private boolean EMP_ARR_DEC() {
        if (match("[")) {
            index++;
            if (match("]")) {
                index++;
                if (EMP_ARR_DEC()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ARR_CONST")) {
            if (ARR_CONST()) {
                return true;
            }
        }
        return false;
    }
    private boolean EMP_ARR_DEC2() {
        if (match("[")) {
            index++;
            if (match("]")) {
                index++;
                if (EMP_ARR_DEC2()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("EMP_ARR_DEC2")) {
                return true;
            }
        }
        return false;
    }
    
    private boolean ARR_CONST() {
        if (match("{")) {
            index++;
            if (ARR_ELEMT()) {
                return true;
            }
        }
        return false;
    }
    private boolean ARR_ELEMT() {
        if (searchSelectionSet("EXPR")) {
            if (EXPR(null)) {
                if (EXPR_LIST()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("ARR_CONST")) {
            if (ARR_CONST()) {
                if (EXPR_LIST()) {
                    return true;
                }
            }
        }
        else if (match("}")) {
            index++;
            return true;
        }
        return false;
    }
    private boolean EXPR_LIST() {
        if (match(",")) {
            index++;
            if (ARR_ELEMT()) {
                return true;
            }
        }
        else if (match("}")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Global Variable Declaration-----------------------------------------------$
    private boolean GLOBAL_DEC(String TM) {
        RetOutInfo out = new RetOutInfo();
        out.TYPE_MODIFIER = TM;
        if (searchSelectionSet("TYPE")) {
            if (TYPE(out)) {
                if (VAR_ARR_G(out)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean VAR_ARR_G(RetOutInfo out) {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE(out)) {
                if (VAR_G(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("VAR_G")) {
            if (VAR_G(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean VAR_G(RetOutInfo out) {
        if (match("id")) {
            out.NAME = getTokenVP();
            index++;
            if (IS_INIT_G(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean IS_INIT_G(RetOutInfo out) {
        String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER;
        if (match("=")) {
            if (!ST.insertMT(N, T, TM, "", "", "", "", getTokenLine())) {
                ST.addError(getTokenLine(), "Redeclaration error",N);
            }
            index++;
            RetOutInfo out1 = new RetOutInfo();
            if (INIT(out1)) {
                //compare
                ST.compareType(T, out1.TYPE, N, getTokenLine());
                if (LIST_G(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("LIST_G")) {
            if (!ST.insertMT(N, T, TM, "", "", "", "", getTokenLine())) {
                ST.addError(getTokenLine(), "Redeclaration error",N);
            }
            if (LIST_G(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean LIST_G(RetOutInfo out){
        String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER;
        if (match(",")) {
            index++;
            if (match("id")) {
                out.NAME = getTokenVP();
                index++;
                if (IS_INIT_G(out)) {
                    return true;
                }
            }
        }
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Attribute Declaration in class--------------------------------------------?
    private boolean ATTR_CLASS_DEC() {
        RetOutInfo out = new RetOutInfo();
        if (match("Static")) {
            out.STATIC = getTokenVP();
            index++;
            if (IS_FINAL(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("IS_FINAL")) {
            if (IS_FINAL(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean IS_FINAL(RetOutInfo out) {
        if (match("const")) {
            out.TYPE_MODIFIER = getTokenVP();
            index++;
            if (TYPE_VAR_ARR(out)) {
                return true;
            }
        }
        else if (searchSelectionSet("TYPE_VAR_ARR")) {
            if (TYPE_VAR_ARR(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean TYPE_VAR_ARR(RetOutInfo out) {
        if (searchSelectionSet("TYPE")) {
            if (TYPE(out)) {
                if (VAR_ARR_C(out)) {
                    return true;
                }
            }
        }
        return false;
    } 
    private boolean VAR_ARR_C(RetOutInfo out) {
        if (searchSelectionSet("ARR_TYPE")) {
            if (ARR_TYPE(out)) {
                if (VAR_C(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("VAR_C")) {
            if (VAR_C(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean VAR_C(RetOutInfo out) {
        if (searchSelectionSet("ACCESSMOD")) {
            if (ACCESSMOD(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    if (IS_INIT_C(out)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean IS_INIT_C(RetOutInfo out) {
        String N=out.NAME,T=out.TYPE,TM=out.TYPE_MODIFIER,STC=out.STATIC,AC=out.ACCESS_MODIFIER;
        if (match("=")) {
            if (!ST.insertCT(N, T, TM, "", AC, STC, getTokenLine())) {
                ST.addError(getTokenLine(), "Redeclaration error",N);
            }
            index++;
            RetOutInfo out1 = new RetOutInfo();
            if (INIT(out1)) {
                //Type that comes out from init must be equatl to T
                ST.compareType(T, out1.TYPE, N, getTokenLine());
                if (LIST_C(out)) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("LIST_C")) {
            if (!ST.insertCT(N, T, TM, "", AC, STC, getTokenLine())) {
                ST.addError(getTokenLine(), "Redeclaration error",N);
            }
            if (LIST_C(out)) {
                return true;
            }
        }
        return false;
    }
    private boolean LIST_C(RetOutInfo out) {
        
        if (match(",")) {
            index++;
            if (ACCESSMOD(out)) {
                if (match("id")) {
                    out.NAME = getTokenVP();
                    index++;
                    if (IS_INIT_C(out)) {
                        return true;
                    }
                }
            }
        }
        else if (match(";")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Expression----------------------------------------------------------------$
    private boolean EXPR(RetOutInfo out) {
        if (searchSelectionSet("F")){
            if (F(out)){
                if (EXPR1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean EXPR1(RetOutInfo out) {
        if (match("or")) {
            String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            RetOutInfo out1 = new RetOutInfo();
            index++;
            if (F(out1)){
                out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
                if (EXPR1(out)){
                    return true;
                }
            }
        }
        else{
            if (searchSelectionSet("EXPR1")){
                return true;
            }
        }
        return false;
    }
    private boolean F(RetOutInfo out) {
        if (searchSelectionSet("G")){
            if (G(out)){
                if (F1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean F1(RetOutInfo out) {
       if (match("and")) {
           String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            RetOutInfo out1 = new RetOutInfo();
           index++;
           if (G(out1)){
               out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
               if (F1(out)){
                   return true;
               }
           }      
       }
       else{
           if (searchFollowSet("F1")){
               return true;
           }
       }
        return false;
    }
    private boolean G(RetOutInfo out) {
        if (searchSelectionSet("H")){
            if (H(out)){
                if (G1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean G1(RetOutInfo out) {
        if (match("rop")) {
            String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            RetOutInfo out1 = new RetOutInfo();
            index++;
            if (H(out1)){
                out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
                if (G1(out)){
                    return true;
                }
            }
        }
        else{
            if (searchFollowSet("G1")){
                return true;
            }
        }
        return false;
    }
    private boolean H(RetOutInfo out) {
        if (searchSelectionSet("I")){
            if (I(out)){
                if (H1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean H1(RetOutInfo out) {
        if (match("pm")) {
            String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            RetOutInfo out1 = new RetOutInfo();
            index++;
            if (I(out1)){
                out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
                if (H1(out)){
                    return true;
                }
            }
        }
        else{
            if (searchFollowSet("H1")){
                return true;
            }
        }
        return false;
    }
    private boolean I(RetOutInfo out) {
        if (searchSelectionSet("J")){
            if (J(out)){
                if (I1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean I1(RetOutInfo out) {
        if (match("mdm")) {
            String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            RetOutInfo out1 = new RetOutInfo();
            index++;
            if (J(out1)){
                out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
                if (I1(out)){
                    return true;
                }
            }
        }
        else{
            if (searchFollowSet("I1")){
                return true;
            }
        }
        return false;
    }
    private boolean J(RetOutInfo out) {
        if (searchSelectionSet("K")){
            if (K(out)){
                if (J1(out)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean J1(RetOutInfo out) {
        if (match("power")) {
            String operator = getTokenVP();
            if (!ST.compatibility_op(out.TYPE, operator))
                ST.addError(getTokenLine(), "Incompatible with the operator ("+operator+")", out.beforeOpName);
            out.beforeOpName="";
            RetOutInfo out1 = new RetOutInfo();
            index++;
            if (K(out1)){
                out.TYPE = ST.compatibility(out.TYPE, out1.TYPE, operator, getTokenLine());
                if (J1(out)){
                    return true;
                }
            }
        }
        else{
            if (searchFollowSet("J1")){
                return true;
            }
        }
        return false;
    }
    private boolean K(RetOutInfo out) {
        if (searchSelectionSet("IS_FLAG")){
            if (IS_FLAG(out)){
                return true;
            }
        }
        return false;
    }
    private boolean IS_FLAG(RetOutInfo out) {
        if (searchSelectionSet("OPERANDS")){
            if (OPERANDS(out)){
                return true;
            }
        }
        return false;
    }
    
    //Operands------------------------------------------------------------------$
    private boolean OPERANDS(RetOutInfo out) {
        if (searchSelectionSet("IS_ACMETH")){
            if (IS_ACMETH()){
                if (match("id")) {
                    index++;
                    if (POS2()){
                        return true;
                    }             
                }
            }
        }
        else if (searchSelectionSet("INC_DEC")){
            if (INC_DEC()){
                if (match("id")) {
                    index++;
                    if (POS()){
                        return true;
                    }
                }
            }
        }
        else if (match("(")) {
            index++;
            if (EXPR(out)){
                if (match(")")) {
                    index++;
                    return true;
                }
            }          
        }
        else if (searchSelectionSet("UNARY")){
            if (UNARY(out)){
                RetOutInfo out1 = new RetOutInfo();
                if (OPERANDS(out1)){
                    if ("!".equals(out.OPERATOR)) {
                        if (!ST.compatibility_op(out1.TYPE, out.OPERATOR)) {
                            ST.addError(getTokenLine(), "Incompatible with the operator ("+out.OPERATOR+")", out.beforeOpName);
                        } else {
                            //if compatible with not operator
                            out.TYPE = out1.TYPE;
                        }
                        
                        out.OPERATOR = "";
                        out.beforeOpName = "";
                    } else {
                        out.TYPE = ST.compatibility_conv(out.TYPE, out1.TYPE, getTokenLine());
                    }
                    return true;
                }
            }
        }
        else if (searchSelectionSet("CONST")){
            if (CONST(out)){
                return true;
            }
        }
        else if (searchSelectionSet("FLAG")){
            if (FLAG()){
                if (OPERANDS(null)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean UNARY(RetOutInfo out) {
        if (match("typeCast")) {
            index++;
            if (match("(")) {
                index++;
                if (TYPE(out)) {
                    if (match(")")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        else if (match("not")) {
            out.OPERATOR = getTokenVP();
            index++;
            return true;
        }
        return false;
    }
    private boolean FLAG() {
        if (match("pm")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Increment Decrement-------------------------------------------------------$
    private boolean INC_DEC() {
        if (match("inc_dec")) {
            index++;
            return true;
        }
        return false;
    }
    
    //Constant------------------------------------------------------------------$
    private boolean CONST(RetOutInfo out) {
        if (match("intConst")) {
            out.beforeOpName = getTokenVP();
            out.TYPE = getTokenCP();
            index++;
            return true;
        }
        else if (match("floatConst")) {
            out.beforeOpName = getTokenVP();
            out.TYPE = getTokenCP();
            index++;
            return true;
        }
        else if (match("charConst")) {
            out.beforeOpName = getTokenVP();
            out.TYPE = getTokenCP();
            index++;
            return true; 
        }
        else if (match("boolConst")) {
            out.beforeOpName = getTokenVP();
            out.TYPE = getTokenCP();
            index++;
            return true;
        }
        else if (match("strConst")) {
            out.beforeOpName = getTokenVP();
            out.TYPE = getTokenCP();
            index++;
            return true;
        }
        return false;
    }
    
    //Conditional Statements----------------------------------------------------$
    private boolean IF_ELSE(){
        if (match("if")){
            index++;
            if (match("(")){
                index++;
                if (EXPR(null)){
                    if (match(")")){
                        index++;
                        if (BODY()){
                            if (OELSE()){
                                return true;                             
                            }
                        }
                    }
                }    
            }
        }     
        return false;
    }
    private boolean OELSE(){
        if (match("else")){
            index++;
            if (BODY()){
                return true;
            }
        }
        else{
            if (searchFollowSet("OELSE")){
                return true;
            }
        }       
        return false;
    }
    
    private boolean SWITCH() {
        if (match("shift")){
            index++;
            if (match("(")){
                index++;
                if (EXPR(null)){
                    if (match(")")){
                        index++;
                        if (match("{")){
                            ST.push();
                            index++;
                            if(STATE()){
                                return true;
                            }      
                        }                
                    }                   
                }
            }
        }  
        return false;
    }
    private boolean STATE() {
        if (match("state")){
            index++;
            if(EXPR(null)){
                if (match(":")){
                    ST.push();
                    index++;
                    if (SWITCH_BODY()){
                        return true;
                    }
                }
            }
        }
        else if (searchSelectionSet("DEFAULT")){
            if (DEFAULT()){
                return true;                
            }
        }
        else if (match("}")){
            ST.pop();
            index++;
            return true;
        }
        return false;
    }
    private boolean DEFAULT() {
        if (match("default")){
            index++;
            if (match(":")){
                ST.push();
                index++;
                if (DEFAULT_BODY()){
                    if (match("}")){
                        ST.pop();
                        index++;
                        return true;
                    }
                }
            }
        }
       
        return false;
    }
    private boolean SWITCH_BODY() {
        if (match("{")){
            index++;
            if (MST()){
                if (match("}")){
                    ST.pop();
                    index++;
                    if (STATE()){
                        return true;                        
                    }
                }  
            }     
        }
        else if (searchSelectionSet("MST")){
            if (MST()){
                ST.pop();
                if (STATE()){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean DEFAULT_BODY() {
        if (match("{")){
            index++;
            if (MST()){
                if (match("}")){
                    ST.pop();
                    index++;
                    return true;  
                }  
            }     
        }
        else if (searchSelectionSet("MST")){
            if (MST()){
                ST.pop();
                return true;
            }
        }
        return false;
    }
    
    //Loop Statements-----------------------------------------------------------$
    private boolean LOOP() {
        if(match("loop")){
            index++;
            if (LT()){
                return true;
            }     
        }
        return false;
    }
    private boolean LT() {
        if (searchSelectionSet("WHILE_ST")){
            if (WHILE_ST()){
                return true;                     
            }
        }
        else if (searchSelectionSet("FOR_ST")){
            if (FOR_ST()){
                return true;
            }        
        }
        return false;
    }
    
    private boolean WHILE_ST() {
        if (match("till")){
            index++;
            if (match("(")){
                index++;
                if (EXPR(null)){
                    if (match(")")){
                        index++;
                        if (BODY()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean DO_WHILE() {
        if (match("do")){
            index++;
            if (BODY()){
                if (match("till")){
                    index++;
                    if (match("(")){
                        index++;
                        if (EXPR(null)){ 
                            if (match(")")){
                                index++;
                                if (match(";")) {
                                    index++;
                                    return true;
                                }
                            }
                        }  
                    }
                }
            }
        }
        return false;
    }
    
    private boolean FOR_ST() {
        String N,T;
        if (match("thru")){
            index++;
            if (match("(")){
                ST.push();
                index++;
                if (match("dt")){
                    T = getTokenVP();
                    index++;
                    if (match("id")){
                        N = getTokenVP();
                        ST.insertFT(N, T, "", getTokenLine());
                        index++;
                        if (match("in")){
                            index++;
                            if (FOR_ARG()){
                                if (match(")")){
                                    index++;
                                    if (BODY()){
                                        ST.pop();
                                        return true;
                                    }
                                }  
                            }
                        }    
                    }                   
                }              
            }
        }
        return false;
    }
    private boolean FOR_ARG() {
        if (match("id")){
            index++;
            if (POS3()){
                return true;
            }            
        }
        else if (match("(")){
            index++;
            if (EXPR(null)){
                if (match(",")){
                    index++;
                    if (EXPR(null)){
                        if (match(",")){
                            index++;
                            if (EXPR(null)){
                                if (match(")")){
                                    index++;
                                    return true;
                                }                                
                            }                            
                        }
                    }
                }                
            }           
        }
        return false;
    }
    
    private boolean POS3() {
        if (searchSelectionSet("FN_BRACKETS")){
            if (FN_BRACKETS(null)){
                if (DOT_ID5()) {
                    return true;
                }
            }
        }
        else if (searchSelectionSet("DOT_ID5")){
            if (DOT_ID5()){
                return true;
            }
        }
        return false;
    }
    private boolean DOT_ID5() {
        if (match("dot")){
            index++;
            if (match("id")){
                index++;
                if(POS3()){
                    return true;
                }               
            }            
        }
        else if (searchSelectionSet("SUBSCRIPT")) {
            if (SUBSCRIPT()) {
                if(POS3()){
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("DOT_ID5")) {
                return true;
            }
        }
        return false;
    }
    
    //Jump Statements-----------------------------------------------------------?
    private boolean BREAK() {
        if (match("stop")){
            index++;
            if (L()){
                return true;
            }
        }
        return false;
    }
    private boolean CONTINUE() {
        if (match("cont")){
            index++;
            if(L()){
                return true;
            } 
        }
        return false;
    }
    private boolean L() {
        if (match("id")){
            index++;
            if (match(":")){
                index++;
                return true;
            }           
        }
        else if (match(";")){
            index++;
            return true;
        }
        return false;
    }
    
    private boolean RET_ST() {
        if (match("ret")){
            index++;
            if(EXPR_OBJ()){
                if (match(";")){
                    index++;
                  return true;
                } 
            }
        }
        return false;
    }
    
    private boolean THROW() {
        if (match("raise")){
            index++;
            if (NEW_OBJ(null)){
                if (match(";")){
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    
    //Exception Handler---------------------------------------------------------$
    private boolean TRY_CATCH() {
        if (match("test")){
            index++;
            if (match("{")){
                ST.push();
                index++;
                if(MST()){
                    if (match("}")){
                        ST.pop();
                        index++;
                        if (match("except")){
                            index++;
                            if (ERROR_TYPE()){
                                if (match("{")){
                                    index++;
                                    if(MST()){
                                        if (match("}")){
                                            ST.pop();
                                            index++;
                                            if (EXCEPT_FINALLY()){
                                                return true;
                                            }    
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }          
        }
        return false;
    }
    private boolean EXCEPT_FINALLY() {
        if (match("except")){
            index++;
            if (ERROR_TYPE()){
                if (match("{")){
                    index++;
                    if (MST()){
                        if (match("}")){
                            ST.pop();
                            index++;
                            if (EXCEPT_FINALLY()){
                                return true;
                            }               
                        } 
                    }
                }
            }
        }
        else if (searchSelectionSet("FINALLY")){
            if (FINALLY()){
                return true;
            }
        }
        else{
            if (searchFollowSet("EXCEPT_FINALLY")){
                return true;
            }
        }
        return false;
    }
    private boolean ERROR_TYPE() {
        if (match("(")){
            ST.push();
            index++;
            if (match("id")){
                index++;
                if (ERR_DOT()){
                    if (match(")")){
                        index++;
                        return true;
                    }
                }          
            }  
        }
        return false;
    }
    private boolean ERR_DOT() {
        if (match("dot")){
            index++;
            if (match("id")){
                index++;
                if (ERR_DOT()) {
                    return true;   
                }
            }
        }
        else if (match("id")){
            index++;
            return true;
        }
        return false;
    }
    private boolean THROWS() {
        if (match("raises")){
            index++;
            if (match("id")){
                index++;
                if (ID_ERR()) {
                    return true;                  
                }
            }
        }
        else{
            if (searchFollowSet("THROWS")){
                return true;
            }
        }
        return false;
    }
    private boolean ID_ERR() {
        if(searchSelectionSet("ERR_DOT_ID")) {
            if (ERR_DOT_ID()) {
                return true;
            }
        }
        else if (searchSelectionSet("ERR_LIST")) {
            if (ERR_LIST()) {
                return true;
            }
        }
        return false;
    }
    private boolean ERR_DOT_ID() {
        if(match("dot")) {
            index++;
            if (match("id")) {
                index++;
                if (ID_ERR()) {
                    return true;
                }
            }
        }
        else {
            if (searchFollowSet("ERR_DOT_ID")) {
                return true;
            }
        }
        return false;
    }
    private boolean ERR_LIST() {
        if(match(",")) {
            index++;
            if (match("id")) {
                index++;
                if (ERR_DOT_ID()) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean FINALLY(){
        if (match("finally")){
            index++;
            if (match("{")){
                ST.push();
                index++;
                if(MST()){
                    if (match("}")){
                        ST.pop();
                        index++;
                        return true;
                    }
                }
            }  
        }
        return false;}
    
} 
