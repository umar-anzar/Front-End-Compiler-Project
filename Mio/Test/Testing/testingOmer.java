/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class testingOmer {

    public testingOmer() {
        
    }

    public static void main(String[] args) {
        C a = new C();
        System.out.println(a.x());
        
    }
}
class A {
    static int fun(){return 1;}
    
}
class B extends A{
    public static int y=7;
    static int x () {
        int z = B.y;
        return z;
    }
}
class C extends B{
    
}