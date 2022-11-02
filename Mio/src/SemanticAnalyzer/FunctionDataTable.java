/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

/**
 *
 * @author omera
 */
public class FunctionDataTable {
    
    public String 
            NAME,
            TYPE;
    
    public int 
            scope;

    /**
     * For Function Table goes in stack
     * @param NAME
     * @param TYPE
     * @param scope 
     */
    public FunctionDataTable(String NAME, String TYPE, int scope) {
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.scope = scope;
    }

}
