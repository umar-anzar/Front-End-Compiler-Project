/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SyntaxAnalyzer;

import LexicalAnalyzer.TokenClass;

/**
 *
 * @author omera
 */
public class CFG {

    
    public static void main(String[] args) {
        TokenClass.loadToken(); // load tokens from file
        
        var parser = new LL1Parser();
        System.out.println("----------------------------------------------");
        System.out.println("SYNTAX: "+parser.validate(TokenClass.tokenList));
        System.out.println("----------------------------------------------");
        parser.ST.printST();
        System.out.println("----------------------------------------------");
        parser.ST.printError();
    }
    
    
    
    
}
