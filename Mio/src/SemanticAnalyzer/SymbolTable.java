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
    
    ScopeStack stack = new ScopeStack();
    
    //Global Current Class and Function Data Table------------------------------
    
    public HashMap<String, ClassDataTable> cdt;
    public HashMap<String, FunctionDataTable> fdt;
    
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
    
    /**
     * Class and Global Var Dec
     * @param NAME
     * @return 
     */
    public MainTable/*Row*/ lookUpMT(String NAME) {
        return mainTable.get(NAME);
    }
    /**
     * Function Dec
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public MainTable/*Row*/ lookUpMT_FN(String NAME, String PARAMETER_LIST) {
        return mainTable.get(NAME);
    }
    /**
     * Var Dec in Class
     * @param NAME
     * @param cdt
     * @return 
     */
    public ClassDataTable/*Row*/ lookUpDT(String NAME, HashMap<String, ClassDataTable> cdt) {
        return cdt.get(NAME);
    }
    /**
     * Function Dec in Class
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public ClassDataTable/*Row*/ lookUpDT_FN(String NAME, String PARAMETER_LIST) {
        return null;
    }
    /**
     * Var Dec in Function
     * Search in scope stack to find
     * @param NAME
     * @param Stack
     * @return 
     */
    public String/*RetType*/ lookUpFDT(String NAME) {
        stack.resetIter(); //Bring point on top of the stack
        
        // Search In Stack
        while (stack.iter.hasNext()) {
            int scope = stack.iter.next();
            FunctionDataTable row = fdt.get(NAME+Integer.toString(scope));
            if (row != null) {
                return row.TYPE;
            }
        }
        
        
        
        return null;
    }
    
    
    public static void main(String[] args) {
        SymbolTable x = new SymbolTable();
        System.out.println(x.lookUpMT("package"));
    }
}
