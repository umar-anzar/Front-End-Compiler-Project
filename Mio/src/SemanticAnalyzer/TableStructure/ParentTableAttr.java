/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

/**
 *
 * @author omera
 */
public abstract class ParentTableAttr {
    
    public String 
            NAME,
            TYPE,
            SIZE,
            TYPE_MODIFIER,
            DIMENSION;
    
    public ParentTableAttr(String NAME, String TYPE, String TYPE_MODIFIER, String DIMENSION) {
        this.NAME = NAME;
        this.TYPE = TYPE;
        if (! this.TYPE.isEmpty() ) {
            this.TYPE = "void"; //empty type means its a function with void return type
        }
        this.TYPE_MODIFIER = TYPE_MODIFIER;
        this.DIMENSION = DIMENSION;
    }
    
          
    /**
     * 
     * @return Generate Key for HashMap
     */
    public String keyGenerate() {
        return NAME;
    }
}


