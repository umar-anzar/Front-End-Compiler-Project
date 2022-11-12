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
            String PARAM_LIST, String TYPE_EXP, 
            String ACCESS_MODIFIER, String PARAMETRIC_CLASS, String EXTEND) 
    {
        super(NAME, TYPE, TYPE_MODIFIER, PARAM_LIST, 
                TYPE_EXP, ACCESS_MODIFIER);
        this.PARAMETRIC_CLASS = PARAMETRIC_CLASS;
        this.EXTEND = EXTEND;
        
        
        //If class is NOT ABSTRACT then initialize DT
        if (!isAbstract() && isClass()) {
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
        record.add(PARAMETRIC_CLASS);
        record.add(EXTEND);
        return record;
    }
    
    @Override
    public ArrayList<String> tableheading() {
        
        ArrayList<String> header = super.tableheading();
        header.add("PARAMETRIC_CLASS");
        header.add("EXTEND");
    
        return header;
    }

}