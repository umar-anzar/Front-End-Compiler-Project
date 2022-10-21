/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import LexicalAnalyzer.Tokenizer;
import SyntaxAnalyzer.CFG;

/**
 *
 * @author omera
 */
public class Main {
    public static void main(String[] args) {
        Tokenizer.main(args); // Create Token and save it into Tokens.txt
        CFG.main(args); // Parse the tokens Stream
    }
}
