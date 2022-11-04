/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.ClassTableRow;
import SemanticAnalyzer.TableStructure.FunctionTableRow;
import SemanticAnalyzer.TableStructure.MainTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class SymbolTable {
    
    //Table--------------------------------------------------------------------
    
    MainTable mt = new MainTable();
    FunctionTable fdt;
    
    //Stack---------------------------------------------------------------------
    
    ScopeStack stack = new ScopeStack();
    
    //Global Current Class and Function Data Table------------------------------
    
    ClassTable currentCt;
    
    
    //Insert Functions----------------------------------------------------------
    //insertMT(tm,pl,te,am,n,type,dim,ext)
    public boolean insertMT(String TYPE_MODIFIER, String PARAM_LIST, String TYPE_EXP,
            String ACCESSMODIFIER, String NAME, String TYPE, String DIMENSION,
            String EXTEND) { 
        
        MainTableRow row = lookUpMT(NAME, PARAM_LIST);
        
        //Redeclare
        if (row != null) {
            return false;
        }
        
        row = new MainTableRow(NAME, TYPE);
        mt.add(row);
        
        return true;
    }
    public boolean insertCDT() { 
        return false;
    }
    public boolean insertFT() { 
        return false;
    }
    
    //LookUp Functions----------------------------------------------------------
    
    /**
     * 
     * @param NAME
     * @param PARAM_LIST
     * @return 
     */
    public MainTableRow lookUpMT(String NAME, String PARAM_LIST) {
        return mt.get(NAME+","+PARAM_LIST);
    }
    /**
     * Var Dec in Class
     * @param NAME
     * @param cdt
     * @return 
     */
    public ClassTable/*Row*/ lookUpDT(String NAME, HashMap<String, ClassTable> cdt) {
        return cdt.get(NAME);
    }
    /**
     * Function Use in Class
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public ClassTableRow lookUpDT_FN(String NAME, String PARAMETER_LIST) {
        return null;
    }
    /**
     * Var Use in Function
     * Search in scope stack to find
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public String/*RetType*/ lookUpFDT(String NAME, String PARAM_LIST) {
        stack.resetIter(); //Bring pointer on top of the stack
        
        // Search In Scope Stack
        
        while (stack.iter.hasNext()) {
            int scope = stack.iter.next();
            FunctionTableRow row = fdt.get(NAME+Integer.toString(scope));
            if (row != null) {
                return row.TYPE;
            }
        }
        
        // Search in current class if its there in attributes
        if (currentCt != null) {
            if(currentCt.get(NAME) != null) {
                return currentCt.get(NAME).TYPE;
            }
        }
        
        // Search in main table for global dec
        if (lookUpMT(NAME, PARAM_LIST) != null) {
            return lookUpMT(NAME, PARAM_LIST).TYPE;
        }
        
        
        return null;
    }
    
    
    public static void main(String[] args) {
        
    }
}
