package JsonTesting;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testing {  
    public static void main(String[] args){
        
    int i=1;    
    do
        System.out.println(i);    
    
    while(i<=10);  
    }
}  


        /*        
        while(true){
        Scanner scan = new Scanner(System.in);
        System.out.print("enter a test case: ");        
        String test = scan.nextLine();
        
        // character regex
        if (language("[']((\\\\(n|r|t|b|0|\\\\|'|\"))|([\\d\\w !-\\[\\]-~]{1}))[']", test)){  
               System.out.println("char constant");
        }
        else if (language("[\"]((\\\\(n|r|t|b|0|\\\\|'|\"))|([\\d\\w\\s!-\\[\\]-~]))*[\"]", test)){  
               System.out.println("string constant");
        }        
        else{
            System.out.println("wrong");
        }
        
        }


    }
        public static boolean language(String RE, String test){
        Pattern p = Pattern.compile(RE);
        Matcher m = p.matcher(test);
        ;
        return m.matches();
        */