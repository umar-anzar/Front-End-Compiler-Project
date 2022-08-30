/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
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
        static String [] b_punctuator = ValidWords.punctuator;
        static String [][] b_operator = ValidWords.operator;
    
    
    
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

    public static boolean stringBreaker() {
        if ('\"' == character) {
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
                    if ('\"' == character) {
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
                    while( (chr = br.read()) != -1) {
                        character = (char) chr;
                        if (newLine(false)) {break;}
                    }
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
        for (int i = 0; i < b_punctuator.length; i++) {
            if (b_punctuator[i].charAt(0) == character){
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
    
    public static void operatorBreaker() {
        for (int i = 0; i < b_operator.length; i++) {
            if (b_operator[i][0].charAt(0) == character){
                if (temp.length() != 1) { 
                    System.out.println(temp.substring(0, temp.length() - 1)); //word token
                    temp = "";
                    temp += character;
                }
                System.out.println(temp+" op line:" + line);//operator token
                temp = "";
                break;
            }
        }
    }
    
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

                temp += character;
                
                // THIS IS FOR NEWLINE
                if (newLine(false)) {continue;}
                //add character in temp after checking new Line
                
                
                // THIS IS FOR STRING BREAKER
                if (stringBreaker()) {continue;}
                // THIS IS FOR COMMENT
                if (commentBreaker()) {continue;}
                // THIS IS FOR SPACE BREAKER
                if (spaceAndTab()){continue;}
                // THIS IS FOR b_punctuator BREAKER
                punctuatorBreaker();
                // THIS IS FOR b_operator BREAKER
                operatorBreaker();

                
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
 
}
