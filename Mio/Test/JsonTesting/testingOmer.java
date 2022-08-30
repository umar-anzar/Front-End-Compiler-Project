/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JsonTesting;

import java.util.regex.Pattern;

/**
 *
 * @author omera
 */
public class testingOmer {
    public static void main(String[] args) {
        A x = new A();

        int q = x.a;
        
        String a = "";
        
        
        System.out.println(Pattern.compile("[0-9]*").matcher("23423423").matches());
    }
}
class A{
    int a=5;
    double y=2;
}