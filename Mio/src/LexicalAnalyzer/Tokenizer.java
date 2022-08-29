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
        
        //For Reader
        int chr=0;
        int line=0;
        
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
            String [] punctuator = ValidWords.punctuator;
            while((chr = br.read()) != -1){
                
                temp += (char)chr;
                
                for (int i = 0; i < punctuator.length; i++) {
                    if (punctuator[i].charAt(0) == chr){
                        //temp[0:-1]
                        System.out.println(temp.substring(0, temp.length() - 1));
                        temp = "";
                        temp += (char)chr;
                        System.out.println(temp);
                        
                    }
                }
                
                
                
                
                
            } 
            System.out.println(temp);
            
            
            
            
            

            
            
            
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
        
        
        // Save Token List In txt
        
            
    }
 
}
