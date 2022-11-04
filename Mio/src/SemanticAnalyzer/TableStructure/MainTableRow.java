/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

import SemanticAnalyzer.ClassTable;

/**
 *
 * @author omera
 */
public class MainTableRow extends ChildTableAttr{
    
    public String 
            EXTEND;      
    public ClassTable 
            DT;

    public MainTableRow(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION, String PARAM_LIST, String TYPE_EXP, 
            String ACCESS_MODIFIER, String EXTEND) 
    {
        super(NAME, TYPE, TYPE_MODIFIER, DIMENSION,PARAM_LIST, 
                TYPE_EXP, ACCESS_MODIFIER);
        this.EXTEND = EXTEND;
        
        //If class is NOT ABSTRACT then initialize DT
        if (! "Abstract".equals(TYPE_MODIFIER) ) {
            DT = new ClassTable();
        }
    }

}