/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
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
        String temp;
        File f;
        FileReader fr = null;
        BufferedReader br = null;
        
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
            br.read();
            
            
            
            
            
            br.close();
            fr.close();
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
        
        try{
            
            ArrayList<String> wordlist = new ArrayList<String>();
            temp = "";
            int c = 0;             
            
            while((c = br.read()) != -1){       //reads Char by Char                         
                if ((char)c == ' '){      
                    wordlist.add(temp);
                    temp = "";
                }          
                    temp += (char)c;
                //int ascii = temp;               //getting ascii of single character
                //System.out.println("'" +temp+ "'" +" ascii: " + ascii);  //Display the Character         
            }
            
            
            if(temp != null){
                wordlist.add(temp);
            }
            System.out.println(wordlist);

        }
      
        catch (IOException e){
            System.out.println("Error: IOException");
        
        }
        
        // Save Token List In txt
        
            
    }
 
}
