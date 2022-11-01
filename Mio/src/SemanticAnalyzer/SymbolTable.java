/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class SymbolTable {
    
    //Tables--------------------------------------------------------------------
    
    HashMap<String, MainTable> mainTable = new HashMap<>();
    
    //Stack---------------------------------------------------------------------
    
    ScopeStack<FunctionTable> stack = new ScopeStack<>();
    
    //Insert Functions----------------------------------------------------------
    
    public boolean insertMT() { 
        return false;
    }
    public boolean insertDT() { 
        return false;
    }
    public boolean insertFT() { 
        return false;
    }
    
    //LookUp Functions----------------------------------------------------------
    
    public MainTable/*Row*/ lookUpMT(String NAME) {
        return null;
    }
    public ClassDataTable/*Row*/ lookUpDT(String NAME, String REF) {
        return null;
    }
    public ClassDataTable/*Row*/ lookUpDT(String NAME, ArrayList<String> PARAMETER) {
        return null;
    }
    public String/*RetType*/ lookUpFT(String NAME, ScopeStack<FunctionTable> Stack) {
        return null;
    }
    
}
