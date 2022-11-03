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
            TYPE;

    public ParentTableAttr(String NAME, String TYPE) {
        this.NAME = NAME;
        this.TYPE = TYPE;
    }
          
    /**
     * 
     * @return Generate Key for HashMap
     */
    public String keyGenerate() {
        return NAME;
    }
}


