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

    public FunctionTableRow(String NAME, String TYPE, String TYPE_MODIFIER, String DIMENSION, int SCOPE) {
        super(NAME, TYPE, TYPE_MODIFIER, DIMENSION);
        this.SCOPE = SCOPE;
    }

    @Override
    public String keyGenerate() {
        return super.keyGenerate() + "," + SCOPE;
    }

}