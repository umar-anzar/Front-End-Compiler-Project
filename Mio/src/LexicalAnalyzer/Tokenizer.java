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


/**
 *
 * @author omera
 */
public class Tokenizer {
    
    
    
    
    public static void main(String[] args) {
        
        // Initialize var
        String temp ="";
        FileReader fr = null;
        BufferedReader br = null;
        
        // For Reader
        int chr=0;
        int line=0;
        
        // Breaker
        String [] b_punctuator = ValidWords.punctuator;
        String [][] b_operator = ValidWords.operator;
        
        
        //Initialize tokenList
        
        
        //ValidWords checker = new ValidWords();
        
        
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
            
            

            while((chr = br.read()) != -1) {
                char character = (char) chr;
                
                //newline
                if (character == '\r') {
                    character = (char)br.read();
                    if (character == '\n') {
                        line += 1;
                        continue;
                    }
                } 
                
                
                
                
                
                
                temp += character; //add character in temp
                
                
                
                // THIS IS FOR SPACE BREAKER
                if (' ' == character || '\t' == character) {
                    if (temp.length() != 1) { 
                        System.out.println(temp.substring(0, temp.length() - 1));
                    }
                    temp = "";
                    continue;
                }
                
                
                // THIS IS FOR b_punctuator BREAKER
                for (int i = 0; i < b_punctuator.length; i++) {
                    if (b_punctuator[i].charAt(0) == character){
                        if (temp.length() != 1) { 
                            System.out.println(temp.substring(0, temp.length() - 1)); //word token
                            temp = "";
                            temp += character;
                        }
                        System.out.println(temp);//punctuator token
                        temp = "";
                    }
                }
                
                
                
                
                
                
            } 
            //System.out.println(temp);
            System.out.println(line);
            
            
            
            

            
            
            
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
        
        
        // Save Token List In txt
        
            
    }
 
}
