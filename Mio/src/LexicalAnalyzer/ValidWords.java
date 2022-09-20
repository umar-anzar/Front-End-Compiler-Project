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

    final static String ID = "id";
    final static String INTCONST = "intConst";
    final static String FLOATCONST = "floatConst";
    final static String CHARCONST = "charConst";
    final static String BOOLEANCONST = "boolConst";
    final static String STRCONST = "strConst";
    final static String DOT = "dot";
    
    
    final static String DT = "dt";
    final static String MDM = "mdm";
    final static String RELATIONALOPERATOR = "rop";
    final static String COMPOUNDASSIGNMENT = "cma";
    
    //{{value, classpart}}
    static String[][] keyword = {
            
        {"convt", "typeCast"},
                
        {"if", "if"}, 
        {"else", "else"},
        {"loop", "loop"},
        
        {"till", "till"},
        {"thru", "thru"},
        
        {"in", "in"},
        {"shift", "shift"}, // shift
        {"state", "state"}, // case
        {"default", "default"},
        {"cont", "cont"},   // continue
        {"stop", "stop"},   // break
        {"ret", "ret"},     // return
        
        {"int", DT},     
        {"point", DT}, // float 
        {"char", DT},
        {"str", "str"},
        {"bool", DT},
        {"val", DT},
 
        
        {"Class", "Class"},
        {"Abstract", "Abstract"},
        {"$", "private"},
        {"$$", "protected"},
        {"Const", "const"},
        {"Self", "Self"},
        {"Parent", "Parent"},
        {"new", "new"},
        {"test", "test"},   // catch
        {"except", "except"},   
        {"finally", "finally"},
        {"raise", "raise"}, 
        {"raises", "raises"},
        {"true",BOOLEANCONST},
        {"false",BOOLEANCONST}
        
          
    };

    static String[][] operator = {
        
        {"<=", RELATIONALOPERATOR},
        {">=", RELATIONALOPERATOR},
        {"!=", RELATIONALOPERATOR},
        {"==", RELATIONALOPERATOR},
        {"<",  RELATIONALOPERATOR},
        {">",  RELATIONALOPERATOR},
        
        {"+=", COMPOUNDASSIGNMENT},
        {"*=", COMPOUNDASSIGNMENT},
        {"-=", COMPOUNDASSIGNMENT},
        {"%=", COMPOUNDASSIGNMENT},
        {"^=", COMPOUNDASSIGNMENT},
        {"/=", COMPOUNDASSIGNMENT},
        
        {"!", "not"}, 
        {"&&", "and"},
        {"||", "or"},

        {"++", "inc_dec"},
        {"--", "inc_dec"},
          
        {"+", "pm"},
        {"-", "pm"},
        
        {"*", MDM},
        {"/", MDM},
        {"%", MDM},
        
        {"^", "power"},
        
        {"=", "assignOp"}
           
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
