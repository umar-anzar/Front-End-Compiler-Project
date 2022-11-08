package Testing;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class testing {  
    int b =3,x;
    static int [][][] po = new int[1][][];
    int [][] o = new int [1][];
    void p(){

    }
    
    public static void main(String[] args){
        LinkedList<Integer> y = new LinkedList<>();
        
        y.add(1);
        y.add(2);
        Iterator<Integer> iter = y.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
            
        }
        y.add(2);
        iter = y.listIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
            
        }
        
        
        
    }
}  

