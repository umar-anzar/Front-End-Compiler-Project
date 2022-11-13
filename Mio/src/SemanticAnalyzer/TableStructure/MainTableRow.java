/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

import SemanticAnalyzer.ClassTable;
import java.util.ArrayList;

/**
 *
 * @author omera
 */
public class MainTableRow extends ChildTableAttr{
    
    public String 
            PARAMETRIC_CLASS,
            EXTEND;
    
    public ClassTable 
            DT;

    public MainTableRow(String NAME, String TYPE, String TYPE_MODIFIER, 
            String PARAM_LIST,
            String ACCESS_MODIFIER, String PARAMETRIC_CLASS, String EXTEND) 
    {
        super(NAME, TYPE, TYPE_MODIFIER, PARAM_LIST, 
                ACCESS_MODIFIER);
        this.PARAMETRIC_CLASS = PARAMETRIC_CLASS;
        this.EXTEND = EXTEND;
        
        
        //If class is NOT ABSTRACT then initialize DT
        if (isClass()) {
            DT = new ClassTable();
            DT.setName(NAME);
        }
    }
    
    public boolean isInherited() {
        return ! EXTEND.isEmpty();
    }
    public String[] inheritedClasses() {
        if (isInherited()) {
            return EXTEND.split(",");
        }
        return new String[] {};
    }
    

 
    @Override
    public ArrayList<String> tablevalues() {
        ArrayList<String> record = super.tablevalues();
        record.add(EXTEND);
        
        record.add(record.get(4));
        record.set(4,PARAMETRIC_CLASS);
        return record;
    }
    
    @Override
    public ArrayList<String> tableheading() {
        
        ArrayList<String> header = super.tableheading();
        header.add("EXT");
        
        header.add(header.get(4));
        header.set(4,"PC");

        return header;
    }

}