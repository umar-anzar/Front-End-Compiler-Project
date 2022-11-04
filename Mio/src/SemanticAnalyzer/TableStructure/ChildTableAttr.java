/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

/**
 *
 * @author omera
 */
public abstract class ChildTableAttr extends ParentTableAttr {
    public String 
            ABSTRACT="",
            PARAM_LIST="",
            TYPE_EXP="",
            SIZE="",
            ACCESS_MODIFIER="";

    public ChildTableAttr(String NAME, String TYPE) {
        super(NAME, TYPE);
    }

    @Override
    public String keyGenerate() {
        return super.keyGenerate() + "," + PARAM_LIST;
    }

}
