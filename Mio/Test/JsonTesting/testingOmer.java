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
    private final int a=3;
    public static void main(String[] args) {
        A x = new A();
        
        int q = x.a;
        
        String z = "Tue";
        switch (z) {
            case "Mon":
                System.out.println("x");
                break;
            case "Tue":
                System.out.println("y");
                break;
            case "Wed":
                System.out.println("z");
                break;
            default:
                break;
        }

        
        
        //System.out.println(Pattern.compile("^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$").matcher("6.").matches());
    }
}
class A{
    int a=5;
    double y=2;
}