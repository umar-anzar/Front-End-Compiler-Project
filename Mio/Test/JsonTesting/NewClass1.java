/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//https://www.w3schools.com/java/java_files.asp LINK OF FILE HANDLING IN JAVA
//https://stackoverflow.com/questions/29908236/reading-java-file-with-escape-characters-for-newline#:~:text=Whenever%20there%20is%20a%20newline,are%20read%20as%20two%20records.

package JsonTesting;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader; // Import the File class
import java.io.BufferedReader; // Java BufferedReader class is used to read the text from a character-based input stream.
import java.io.IOException;
/**
 *
 * @author omer
 */
public class NewClass1 {
    public static void main(String[] args) {
        FileReader myObj;
        BufferedReader br;
        
        try {
            myObj = new FileReader("Test\\JsonTesting\\file.txt"); // Specify the filename
            br = new BufferedReader(myObj);    
             
            System.out.println((char)br.read()=='a');
            System.out.println((char)br.read()=='\r');
            System.out.println((char)br.read()=='\n');
            System.out.println((char)br.read()=='b');
            System.out.println((char)br.read()==(char)-1);//last charater is -1
            System.out.println((char)-1);
            br.close();
            myObj.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            System.out.println("File Dont Exist");
        } catch (IOException e){
            System.out.println("Error");
        }

    }
}
