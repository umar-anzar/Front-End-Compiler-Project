/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LexicalAnalyzer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Umar, Izhan, Muqsit
 */
public class TokenClass {
    
    // Class Attribute
    static List <TokenClass> tokenList;
    
    // Object Attributes
    String classPart;
    String valuePart;
    String error;
    int line;
    
    //Constructor--------------------------------------------------------------
        public TokenClass(String classPart, String valuePart, int line) {

            this.classPart = classPart;
            this.line = line;
            // Class part is not equal to value then add value else Dont
            if ( !(classPart.equals(valuePart)) ) {
                this.valuePart = valuePart;
            }
        }
    
    
    // Static SAVE METHOD-------------------------------------------------------
        // Create TokenList
        static void createTokenList() {
            TokenClass.tokenList = new ArrayList<>();
        }

        // Add Token
        static void addToken(TokenClass t) {
            TokenClass.tokenList.add(t);
        }

        // Save Token in Json File
        static void saveToken() {
            GsonBuilder builder = new GsonBuilder(); 
            //builder.setPrettyPrinting(); 
            Gson gson = builder.create(); 
            try {
                //Create Token Json File
                File myObj = new File("src\\LexicalAnalyzer\\Tokens.json");
                if (myObj.createNewFile()) {
                  System.out.println("File created: " + myObj.getName());
                } else {
                  System.out.println("File already exists.");
                }

                // Write Token Json File
                FileWriter writer = new FileWriter("src\\LexicalAnalyzer\\Tokens.json");
                    //Every Token In New Line
                    String jsonString  = "[";
                    if (!TokenClass.tokenList.isEmpty()){
                        jsonString += gson.toJson(TokenClass.tokenList.get(0));
                    }
                    for (int i = 1; i < TokenClass.tokenList.size(); i++) {
                        jsonString += ",\n";
                        jsonString += gson.toJson(TokenClass.tokenList.get(i));

                    }
                    jsonString += "]";
                    
                writer.write(jsonString);
                writer.close();
                System.out.println("Successfully save Token to the file.");

              } catch (IOException e) {
                System.out.println("An error occurred.");
              }
        }
        
        // Load Token from Json File
        static void loadToken(){
            try {
            Reader reader = Files.newBufferedReader(Paths.get("src\\LexicalAnalyzer\\Tokens.json"));
             // convert JSON array to list of users
            TokenClass.tokenList = new Gson().fromJson(reader, new TypeToken<List<TokenClass>>() {}.getType());
                System.out.println("Successfully load Token from the file.");
            } catch (IOException e) {
                TokenClass.tokenList = new ArrayList<>();
            }
        }
            

    // Object Method------------------------------------------------------------
        // Set The Error Message
        public void setError(String error) {
            this.error = error;
        }
        
    // Override function
        // When Print Show attributes
        @Override
        public String toString() {
            String returnString = this.classPart+"-"+this.valuePart+"-"+this.line;
            if (this.error != null){
                returnString += "-"+this.error;
            }
            return returnString; 
        }
        
    
}
