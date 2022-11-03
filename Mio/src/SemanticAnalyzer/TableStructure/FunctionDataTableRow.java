/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

/**
 *
 * @author omera
 */
public class FunctionDataTableRow extends ParentTableAttr{
    
    public int 
            scope;

    /**
     * For Function Table goes in stack
     * @param NAME
     * @param TYPE
     * @param scope 
     */
    public FunctionDataTableRow(String NAME, String TYPE, int scope) {
        super(NAME, TYPE);
        this.scope = scope;
    }

    @Override
    public String keyGenerate() {
        return super.keyGenerate() + "," + scope;
    }

}