/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  variable naming scheme: camel case.
 * @author  Umar, Izhan, Muqsit
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
        {"raises", "raises"},
        {"true","true"},
        {"false","false"}
        
          
    };

    static String[][] operator = {
        
        {"<=", "Relation Operator"},
        {">=", "Relation Operator"},
        {"!=", "Relation Operator"},
        {"==", "Relation Operator"},
        {"<", "Relation Operator"},
        {">", "Relation Operator"},
        
        {"+=", "compund assignment"},
        {"*=", "compund assignment"},
        {"-=", "compund assignment"},
        {"%=", "compund assignment"},
        {"^=", "compund assignment"},
        {"/=", "compund assignment"},
        
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
        for (int i = 0; i < keyword.length; i++) {
            if (word.equals(keyword[i][0])) {
                return keyword[i][1];
            }
        }
        return "";
    }
    static String isOperator(String word) {
        // code to be executed
        for (int i = 0; i < operator.length; i++) {
            if (word.equals(operator[i][0])) {
                return operator[i][1];
            }
        }
        return "";
    }      
    static boolean isPunctuator(String word) {
        // code to be executed
        for (int i = 0; i < punctuator.length; i++) {
            if (word.equals(punctuator[i])) {
                return true;
            }
        }
        return false;
    }    
    static boolean isId(String word) {
        // code to be executed
        String RE = "[_a-zA-Z][_a-zA-Z0-9]*";
        return match(RE, word);
    }    
    static boolean isCharConst(String word) {
        // code to be executed
        String RE = "[']((\\\\(n|r|t|b|0|\\\\|'|\"))|([\\d\\w !-\\[\\]-~]{1}))[']";
        return match(RE, word);
    }
    static boolean isStrConst(String word) {
        // code to be executed
        String RE = "[\"]((\\\\(n|r|t|b|0|\\\\|'|\"))|([\\d\\w\\s!-\\[\\]-~]))*[\"]";
        return match(RE, word);
    }
    static boolean isIntConst(String word) {
        // code to be executed
        String RE = "[0-9]+";
        return match(RE, word);
    }
    static boolean isFltConst(String word) {
        // code to be executed
        String RE = "^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
        return match(RE, word);
    }
    
    public static boolean match(String RE, String test){
        Pattern p = Pattern.compile(RE);
        Matcher m = p.matcher(test);
        return m.matches();
     }  
    
}
