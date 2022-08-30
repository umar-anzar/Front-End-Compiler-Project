/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author omera
 */
public class Tokenizer {
    
    // Initialize var
        static String temp ="";
        static FileReader fr = null;
        static BufferedReader br = null;
        
        // For Reader
        static int chr=0;
        static char character=' ';
        static int line=1;
        
        // Breaker
        static boolean len2Op = false;
        static boolean floatDot = false;
        static String [] bPunctuator = ValidWords.punctuator;
        static String [][] bOperator = ValidWords.operator;
//        static String [][] len2Operator = {
//            {"+","+"},{"+","="},{"-","-"},{"-","="},
//            {"*","="},{"/","="},{"%","="},{"^","="},
//            {"<","="},{">","="},{"=","="},
//            {"|","|"},{"&","&"},{"=","="},
//        
//        };
    
    public static void main(String[] args) {
        
        //Initialize tokenList
        TokenClass.createTokenList();
        
        
        // Initialize File Reader 
        try {
            //Creation of File Reader object
            fr = new FileReader("src\\LexicalAnalyzer\\file.txt");
            //Creation of BufferedReader object ("Raw file Reader")
            br = new BufferedReader(fr);             
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        
        

        // Reading File Char by Char and making Tokens
        try {
            
            
            /* READER LOOP */
            while( (chr = br.read()) != -1) {
                character = (char) chr;
                
                //len2operator check
                if (len2OperatorBreaker()) {continue;}
                
                //Dot breaker 
                if (dotSpecialBreaker()) {continue;}

                //add character in temp
                temp += character;
                
                
                // THIS IS FOR NEWLINE
                if (newLine(false)) {continue;}
                

                // THIS IS FOR STRING BREAKER(finish string from start to end)
                if (stringBreaker('\"')) {continue;}
                // THIS IS FOR CHARACTER BREAKER(finish CHARACTER from start to end)
                if (stringBreaker('\'')) {continue;}
                // THIS IS FOR COMMENT(finish string from start to end)
                if (commentBreaker()) {continue;}
                // THIS IS FOR SPACE BREAKER
                if (spaceAndTab()){continue;}
                // THIS IS FOR Punctuator BREAKER
                punctuatorBreaker();
                // THIS IS FOR Operator BREAKER
                len2Op = operatorBreaker();
                
                
            } 
            
            // AFTER LOOP CHECKING IF TEMP IS FILL WITH SOMETHING
            if (temp.isEmpty()) {
            } else {
                System.out.println(temp+" op line:" + line);//operator token
                temp = "";
            }

            
            
            
            
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
        
        
        // Save Token List In txt
        
            
    }
 
    public static boolean newLine(boolean str){
        if (character == '\r') {
            
            if (!str) {
                if (temp.length() != 1) {
                    System.out.println(temp);//TOKEN
                }
                temp = "";
            }
            
            try {
                character = (char)br.read();
            } catch (IOException ex) {
                System.out.println("error new line function");
            }
            if (character == '\n') {
                line += 1;
                return true;
            }
        }
        return false;
    }

    public static boolean stringBreaker(char c) {
        if (c == character) {
            try {
                if (temp.length() != 1) {
                    System.out.println(temp.substring(0, temp.length() - 1));
                }
                temp = "";
                temp += character;
                while ((chr = br.read()) != -1) {
                    character = (char) chr;
                    if (newLine(true)) {break;}
                    temp += character;
                    if (c == character) {
                        if ('\\' == temp.charAt(temp.length() - 2)) {
                            continue;
                        }
                        break;
                    }

                }
                System.out.println(temp+" String");
                temp = "";
                return true;
            } catch (IOException ex) {
                System.out.println("String breaker error");
            }
        }
        return false;
    }
    
    public static boolean commentBreaker() {
        if (character == '@') {
            if (temp.length() != 1) { 
                System.out.println(temp.substring(0, temp.length() - 1));
            }
            temp = "";
            try {
                if ( (chr = br.read()) == -1){return true;}
                character = (char) chr;
                if (character == '@') {
                    while( (chr = br.read()) != -1) {
                        character = (char) chr;
                        if (character == '@') {
                            if ( (chr = br.read()) == -1){return true;}
                            character = (char) chr;
                            if (character == '@') {break;}
                        }
                    }
                } else {
                    
                    do {
                        character = (char) chr;
                        if (newLine(true)) {break;}
                    } while((chr = br.read()) != -1);
                    
                }
                temp="";
                return true;
            } catch (IOException ex) {
                System.out.println("Error in Comment function");
            }
        }
        return false;
    }
    
    public static boolean spaceAndTab() {
        if (' ' == character || '\t' == character) {
            if (temp.length() != 1) { 
                System.out.println(temp.substring(0, temp.length() - 1));//TOKEN
            }
            temp = "";
            return true;
        }
        return false;
    }
    
    public static void punctuatorBreaker() {
        for (int i = 0; i < bPunctuator.length; i++) {
            if (bPunctuator[i].charAt(0) == character){
                if (temp.length() != 1) { 
                    System.out.println(temp.substring(0, temp.length() - 1)); //word token
                    temp = "";
                    temp += character;
                }
                System.out.println(temp+" punc line:" + line);//punctuator token
                temp = "";
                break;
            }
        }
    }
    
    public static boolean operatorBreaker() {
        for (int i = 0; i < bOperator.length; i++) {
            if (bOperator[i][0].charAt(0) == character){
                if (temp.length() != 1) { 
                    System.out.println(temp.substring(0, temp.length() - 1)); //word token
                    temp = "";
                    temp += character;
                }
                // len 2 operator boolean
                return true;
            }
        }
        return false;
    }
    
    public static boolean len2OperatorBreaker() {
        if (len2Op) {
            boolean found=false;
            for (int i = 0; i < bOperator.length; i++) {
                if (bOperator[i][0].length()>1) {
                    if (character == bOperator[i][0].charAt(1)) {
                        temp += character;
                        found = true;
                        break;
                    }
                }
            }
            System.out.println(temp+" op line:" + line);//operator token
            temp = "";
            len2Op = false;
            if (found){
                found = false;
                return true;
            }
        }
        return false;
    }
    
    public static boolean dotSpecialBreaker(){
        if (floatDot) {
            if (Pattern.compile("[0-9e]").matcher(String.valueOf(character)).matches()) {
                // It's a FLOAT because next char is number :)
                //Read then
            } else {
                // Its not a float, its a indentifer or other breaker
                System.out.println(temp.charAt(0)); //word token DOT
                temp = "";
            }

            floatDot = false;
        }
        if (character == '.') {
            if (temp.isEmpty()) {
                //This might be float so we go further
                floatDot = true;
            } else {
                if (Pattern.compile("[0-9]*").matcher(temp).matches()) {
                    // This is float Hurrah
                    //No need to check further and no breaking
                    floatDot = false;

                } else {
                    //Then this might be identifier or other thing so BREAK IT
                    //NOT A FLOAT SO BREAKING MUST
                    System.out.println(temp);//TOKEN
                    temp = ""+character;
                    System.out.println(temp);//TOKEN
                    temp = "";
                    return true;
                }

            }
            
        }
        return false;
    }
    
}
