import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class CC_Parser {

    static ArrayList<Lexem> lexems = new ArrayList();
    static ArrayList<UnidentifiedLexem> unid_lexems = new ArrayList();
    static SLRParser parser = new SLRParser();
    static int nextValue = 7;



    public static void main(String[] args) {
        while (true){
            displayOptions();
            if(!lexems.isEmpty()){
                printLexems();
                lexems.clear();
            }
            if(!unid_lexems.isEmpty()){
                printUnIdLexems();
                unid_lexems.clear();
            }
        }
    }

    public static void displayOptions(){
        Scanner scanner = new Scanner(System.in);
        char inputChar;
        System.out.println("\n\n\n************************************************************************************\n\n");
        System.out.println("=========================== Compiler Construction Project ==========================");
        System.out.println("\n1. Perform Lexical Analysis Only ----------------------------------------- Press '1'");
        System.out.println("2. Perform Lexical and Syntax Analysis both ------------------------------ Press '2'"); 
        System.out.println("3. Exit Code ------------------------------------------------------------- Press '3'"); 
        System.out.println("\n\n************************************************************************************");   
        System.out.print("Enter Your Choice: ");
        inputChar = scanner.next().charAt(0);
        selectOption(inputChar);
    }

    // Read command and perform relevent actions..
    public static void selectOption(char inputChar){
        int inputCharAscii = (int) inputChar; 
        switch(inputCharAscii){
            case 49 :
                System.out.println("\nReading file to perform Lexical Analysis...");
                openFile();
                break;
            case 50:
                System.out.println("\nTaking user input to perform Lexical and Syntax Analysis...");
                ReadLineForSyntaxAnalysis();
                break;
            case 51:
                System.out.println("\nExiting Code..");
                System.exit(0);
                break;
            default:
                System.out.println("\nInvalid Option");
                displayOptions();
        }
    }

    // Open File
    public static void openFile(){
        
        File file = new File("input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                LexemsAndTokens(line, lineNo++);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error While Reaading File..");
            System.out.println(e.getMessage());
        }
    }

    public static void LexemsAndTokens(String line, int LineNo){
        String[] words = line.split(" ");
        boolean commentStarted = false;

        for(int i=0; i< words.length; i++) { 

            // Handeling Comments
            if(words[i].equals("//") && !commentStarted) {
                break;
            }
            if(words[i].equals("/*") || words[i].contains("/*")) {
                commentStarted = true;
                continue;
            }
            if(words[i].equals("*/") || words[i].contains("*/")) {
                commentStarted = false;
                continue;
            }
            
            if(commentStarted) {
                continue;
            }

            // Handeling Whitspaces, Tabs, newLines
            if(isWhiteSpace(words[i])){
                continue;
            }

            // Creating Lexeme
            else if (isKeyword(words[i])){
                lexems.add(new Lexem(words[i], words[i], getKeywordType(words[i])));
            } 
            else if (isIdentifier(words[i])) {
                // nextValue = nextValue + 1;
                lexems.add(new Lexem(words[i], "id", Integer.toString(nextValue++)));
            }
            else if(isString(words[i])) {
                lexems.add(new Lexem(words[i], "sl", Integer.toString(nextValue++)));
            } 
            else if(isInteger(words[i])) {
                lexems.add(new Lexem(words[i], "in", Integer.toString(nextValue++)));
            } 
            else if(isRelational(words[i])) {
                lexems.add(new Lexem(words[i], "ro", getRelationType(words[i])));
            } 
            else if(isArithmetic(words[i])) {
                lexems.add(new Lexem(words[i], "ao", getArithmeticType(words[i])));
            } 
            else if(isOtherOperator(words[i])) {
                lexems.add(new Lexem(words[i], "oo", getOtherOperatorType(words[i])));
            }
            else{
                if (words[i] != ""){
                    unid_lexems.add(new UnidentifiedLexem(Integer.toString(LineNo), words[i], "Un-Identified Token"));
                }
            }
        }
    }

    public static boolean isWhiteSpace (String word) {
        return Pattern.matches("", word); 
    }
    
    public static boolean isKeyword (String word) {
        return
            word.equals("int") ||
            word.equals("char") ||
            word.equals("string") ||
            word.equals("if") ||
            word.equals("else") ||
            word.equals("do") ||
            word.equals("while");
    }
    
    public static String getKeywordType (String word) {
        if (word.equals("int")) return "0";
        if (word.equals("char")) return "1";
        if (word.equals("string")) return "2";
        if (word.equals("if")) return "3";
        if (word.equals("else")) return "4";
        if (word.equals("do")) return "5";
        if (word.equals("while")) return "6";
        return null;
    }
    
    public static boolean isIdentifier (String word) {
        return Pattern.matches("^[a-zA-Z$][a-zA-Z$0-9]*$", word); 
    }
    
    public static boolean isString (String word) {
        return Pattern.matches("\"[A-Z a-z 0-9]*\"", word); 
    }
    
    public static boolean isInteger (String word) {
        return Pattern.matches("\\d+", word); 
    }

    public static boolean isRelational (String word) {
        return 
            word.equals("<") || 
            word.equals("<=") ||
            word.equals("==") ||
            word.equals("<>") ||
            word.equals(">=") ||
            word.equals(">");
        }
    
    public static String getRelationType (String word) {
        if (word.equals("<")) return "lt";
        if (word.equals("<=")) return "le";
        if (word.equals("==")) return "eq";
        if (word.equals("<>")) return "ne";
        if (word.equals(">=")) return "ge";
        if (word.equals(">")) return "gt";
        return null;
    }
    
    public static boolean isArithmetic (String word) {
        return word.equals("+") || 
        word.equals("-") || 
                word.equals("*")  || 
                word.equals("/");
            }
    
    public static String getArithmeticType (String word) {
        if (word.equals("+")) return "ad";
        if (word.equals("-")) return "sb";
        if (word.equals("*")) return "ml";
        if (word.equals("*")) return "dv";
        return null;
    }

    public static boolean isOtherOperator (String word) {
        return word.equals("=") || 
                word.equals("(") || 
                word.equals(")") || 
                word.equals("{")  || 
                word.equals("}") || 
                word.equals(";");
    }
    
    public static String getOtherOperatorType (String word) {
        if (word.equals("=")) return "as";
        if (word.equals("(")) return "op";
        if (word.equals(")")) return "cp";
        if (word.equals("{")) return "ob";
        if (word.equals("}")) return "cb";
        if (word.equals(";")) return "tr";
        return null;
    }
    
    public static void printLexems() {
        System.out.println("\n================================== LEXEMS & TOKENS =================================");
        System.out.println("Name" + "                          " + "Token Name" + "                    " + "Value");
        System.out.println("====================================================================================");
        lexems.forEach((lexem) -> System.out.println(lexem.toString()));
    }

    public static void printUnIdLexems() {
        System.out.println("\n================================ UNIDENTIFIED LEXEMS ===============================");
        unid_lexems.forEach((unid_lexem) -> System.out.println(unid_lexem.toString()));
    }

    public static void ReadLineForSyntaxAnalysis(){
        Scanner scanner = new Scanner(System.in);
        String inputString;
        System.out.print("\n[NOTE]: ");
        System.out.print("* Seperate Each Token With A Space.\n");
        System.out.print("\t* Tokens Without Space Will Return An Error.\n");
        System.out.println("===================================================================================");
        System.out.print("Write your expression here: ");
        inputString = scanner.nextLine();
        LexemeWithParsing(inputString);
    }

    public static void LexemeWithParsing(String line){
        String[] words = line.split(" ");
        String tokenLine = "";
        boolean commentStarted = false;

        for(int i=0; i< words.length; i++) { 

            // Handeling Comments
            if(words[i].equals("//") && !commentStarted) {
                break;
            }
            if(words[i].equals("/*") || words[i].contains("/*")) {
                commentStarted = true;
                continue;
            }
            if(words[i].equals("*/") || words[i].contains("*/")) {
                commentStarted = false;
                continue;
            }
            
            if(commentStarted) {
                continue;
            }

            // Handeling Whitspaces, Tabs, newLines
            if(isWhiteSpace(words[i])){
                continue;
            }

            // Creating Lexeme
            if (isKeyword(words[i])){
                lexems.add(new Lexem(words[i], words[i], getKeywordType(words[i])));
            } 
            else if (isIdentifier(words[i])) {
                // nextValue = nextValue + 1;
                lexems.add(new Lexem(words[i], "id", Integer.toString(nextValue++)));
                tokenLine = tokenLine + " " + "id";
            }
            else if(isString(words[i])) {
                lexems.add(new Lexem(words[i], "sl", Integer.toString(nextValue++)));
            } 
            else if(isInteger(words[i])) {
                lexems.add(new Lexem(words[i], "in", Integer.toString(nextValue++)));
                tokenLine = tokenLine + " " + "id";
            } 
            else if(isRelational(words[i])) {
                lexems.add(new Lexem(words[i], "ro", getRelationType(words[i])));
            } 
            else if(isArithmetic(words[i])) {
                lexems.add(new Lexem(words[i], "ao", getArithmeticType(words[i])));
                if(words[i].equals("+") || words[i].equals("*")){
                    tokenLine = tokenLine + " " + words[i];
                }
            } 
            else if(isOtherOperator(words[i])) {
                lexems.add(new Lexem(words[i], "oo", getOtherOperatorType(words[i])));
                if(words[i].equals("=")){
                    tokenLine = "";
                }
                else if(words[i].equals("(") || words[i].equals(")")){
                    tokenLine = tokenLine + " " + words[i];
                }
            }
            else{
                if (words[i] != ""){
                    unid_lexems.add(new UnidentifiedLexem(Integer.toString(1), words[i], "Un-Identified Token"));
                }
            }
        }
        generateParse(tokenLine);
    }

    public static void generateParse(String line){
        String[] words = line.split(" ");
        String token = "";
        for(int i = 0; i < words.length; i ++){
            if (!words[i].equals(";")){
                token = token + " " + words[i];
            }
        }
        token = token + " $";
        System.out.println("\nInput Token: " + token);
        parser.Parse(token);
    }

}