/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;


/**
 *  variable naming scheme: camel case.
 * @author omera
 */
public class ValidWords {

    //{{value, classpart}}
    static String[][] keyword = {
        
        {"if", "if"}, 
        {"else", "else"},
        {"loop", "loop"},
        
        {"till", "unidentified"},
        {"thru", "unidentified"},
        
        {"in", "in"},
        {"shift", "shift"}, // shift
        {"state", "state"}, // case
        {"default", "default"},
        {"cont", "cont"},   // continue
        {"stop", "stop"},   // break
        {"ret", "ret"},     // return
        
        {"int", "dataType"},     
        {"point", "dataType"}, // float 
        {"char", "dataType"},
        {"str", "dataType"},
        {"bool", "dataType"},
        {"val", "dataType"},
 
        
        {"Class", "Class"},
        {"Abstract", "Abstract"},
        {"Const", "const"},
        {"Self", "Self"},
        {"Parent", "Parent"},
        {"new", "new"},
        {"test", "test"},   // catch
        {"except", "except"},   
        {"finally", "finally"},
        {"raise", "raise"}, 
        {"raises", "raises"}
        
          
    };

    static String[][] operator = {
        
        {"<=", "Relation Operator"},
        {">=", "Relation Operator"},
        {"!=", "Relation Operator"},
        {"==", "Relation Operator"},
        
        {"+=", "compund assignment"},
        {"*=", "compund assignment"},
        {"-=", "compund assignment"},
        {"%=", "compund assignment"},
        {"^=", "compund assignment"},
        
        {"!", "not"}, 
        {"&&", "and"},
        {"||", "or"},

        {"++", "inc-dec"},
        {"--", "inc-dec"},
          
        {"+", "pm"},
        {"-", "pm"},
        
        {"*", "mdm"},
        {"/", "mdm"},
        {"%", "mdm"},
        
        {"^", "power"},
        
        {"=", "simple assignment"}
           
    };

    static String[] punctuator = {
        
        ";",
        ",", 
        ":", 
        "{", 
        "(", 
        "[",
        "}",
        ")",
        "]"
            
    };

    
    
    static String isKeyword(String word) {
        // code to be executed
    }
    static String isOperator(String word) {
        // code to be executed
    }      
    static boolean isPunctuator(String word) {
        // code to be executed
    }    


    static boolean isId(String word) {
        // code to be executed
        return true;
    }    
    static boolean isCharConst(String word) {
        // code to be executed
        return true;
    }
    static boolean isStrConst(String word) {
        // code to be executed
        return true;
    }
    static boolean isIntConst(String word) {
        // code to be executed
        return true;
    }
    static boolean isFltConst(String word) {
        // code to be executed
        return true;
    }
    
    
}
