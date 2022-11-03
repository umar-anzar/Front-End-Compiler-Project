/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.ClassDataTableRow;
import SemanticAnalyzer.TableStructure.FunctionDataTableRow;
import SemanticAnalyzer.TableStructure.MainTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class SymbolTable {
    
    //Table--------------------------------------------------------------------
    
    MainTable mt = new MainTable();
    
    //Stack---------------------------------------------------------------------
    
    ScopeStack stack = new ScopeStack();
    
    //Global Current Class and Function Data Table------------------------------
    
    ClassDataTable currentCdt;
    FunctionDataTable fdt;
    
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
    public MainTableRow lookUpMT(String NAME) {
        return mt.get(NAME);
    }
    /**
     * Function Dec
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public MainTableRow lookUpMT_FN(String NAME, String PARAMETER_LIST) {
        return mt.get(NAME);
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
     * Function Use in Class
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public ClassDataTableRow lookUpDT_FN(String NAME, String PARAMETER_LIST) {
        return null;
    }
    /**
     * Var Use in Function
     * Search in scope stack to find
     * @param NAME
     * @return 
     */
    public String/*RetType*/ lookUpFDT(String NAME, String PARAMETER_LIST) {
        stack.resetIter(); //Bring pointer on top of the stack
        
        // Search In Scope Stack
        
        while (stack.iter.hasNext()) {
            int scope = stack.iter.next();
            FunctionDataTableRow row = fdt.get(NAME+Integer.toString(scope));
            if (row != null) {
                return row.TYPE;
            }
        }
        
        // Search in current class if its there in attributes
        if (currentCdt != null) {
            if(currentCdt.get(NAME) != null) {
                return currentCdt.get(NAME).TYPE;
            }
        }
        
        // Search in main table for global dec
        if (lookUpMT(NAME) != null) {
            return lookUpMT(NAME).TYPE;
        }
        
        
        return null;
    }
    
    
    public static void main(String[] args) {
        SymbolTable x = new SymbolTable();
        System.out.println(x.lookUpMT("package"));
        System.out.println("a".isEmpty());
    }
}
