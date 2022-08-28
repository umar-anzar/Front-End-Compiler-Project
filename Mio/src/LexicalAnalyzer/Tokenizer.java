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
import java.util.Scanner;   // Import the Scanner class to read text files

/**
 *
 * @author omera
 */
public class Tokenizer {
    public static void main(String[] args) {
        //Initialize tokenList
        //ValidWords checker = new ValidWords();
        
        // Reader
        try{
            File f = new File("src\\LexicalAnalyzer\\file.txt");    //Creation of File Descriptor for input file
            FileReader fr = new FileReader(f);                      //Creation of File Reader object
            BufferedReader br = new BufferedReader(fr);             //Creation of BufferedReader object
            
            int c = 0;             
            while((c = br.read()) != -1){               //Read char by Char       
                char character = (char) c;              //converting integer to char
                int ascii = character;                  //to get ascii of single character
                System.out.println("'" +character+ "'" +" ascii: " + ascii);  //Display the Character
                
            }
        }
        
        catch(FileNotFoundException e){
            System.out.println("Error: File not found!");
        }       
        catch (IOException e){
            System.out.println("Error: IOException");
        
        }

        // Save Token List In txt
            
    }
 
}
