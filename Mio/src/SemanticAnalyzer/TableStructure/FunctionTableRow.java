/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

/**
 *
 * @author omera
 */
public class FunctionTableRow extends ParentTableAttr{
    
    public int 
            SCOPE;

    /**
     * For Function Table goes in stack
     * @param NAME
     * @param TYPE
     * @param scope 
     */
    public FunctionTableRow(String NAME, String TYPE, int scope) {
        super(NAME, TYPE);
        this.SCOPE = scope;
    }

    @Override
    public String keyGenerate() {
        return super.keyGenerate() + "," + SCOPE;
    }

}