/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//https://www.tutorialspoint.com/gson/gson_quick_guide.htm
package JsonTesting;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder; 
/**
 *
 * @author omer
 */
public class NewClass2 {
    public static void main(String[] args) {
        ABC Hello = new ABC(4, (float)2.3, new double[]{3,2,5});
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 
        System.out.println(gson.toJson(Hello));
        ABC NewHello = gson.fromJson(gson.toJson(Hello), ABC.class);
        System.out.println(NewHello.a);
    }
}
class ABC{
    int a;
    float b;
    private double[] x;

    public ABC(int a, float b, double[] x) {
        this.a = a;
        this.b = b;
        this.x = x;
    }
    
}