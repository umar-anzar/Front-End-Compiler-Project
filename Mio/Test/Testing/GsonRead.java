/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/**
 *
 * @author omer
 */
public class GsonRead {
    public static void main(String[] args) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("Test\\JsonTesting\\json.txt"));
             // convert JSON array to list of users
            List<ABC> users = new Gson().fromJson(reader, new TypeToken<List<ABC>>() {}.getType());
            System.out.println(users);
        } catch (IOException e) {
            System.out.println("error");
        }
        
    }
}
