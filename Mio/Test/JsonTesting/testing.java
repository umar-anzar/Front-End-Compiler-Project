package JsonTesting;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testing {

    
    public static void main(String[] args){
        System.out.println(("\"\"").length());
//        while(true){
//        Scanner scan = new Scanner(System.in);
//        System.out.print("enter a test case: ");        
//        String test = scan.nextLine();
//        
//        // regex
//        if (language("^([a-zA-Z_$][a-zA-Z\\d_$]*)$", test)){  //"[A-Za-z][A-Za-z0-9_]*"
//               System.out.println("Identifier constant");
//        }
//        else{
//            System.out.println("Not identifier constant");
//        }
//        
//        }


    }
        public static boolean language(String RE, String test){
        Pattern p = Pattern.compile(RE);
        Matcher m = p.matcher(test);
        return m.matches();
     
    }  

}


