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

    
    
    static void isKeyword() {
        // code to be executed
    }
    static void isOperator() {
        // code to be executed
    }      
    static void isPunctuator() {
        // code to be executed
    }    


    static void isId() {
        // code to be executed
    }    
    static void isCharConst() {
        // code to be executed
    }
    static void isStrConst() {
        // code to be executed
    }
    static void isIntConst() {
        // code to be executed
    }
    static void isFltConst() {
        // code to be executed
    }
    
    
}
