/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

import java.util.ArrayList;

/**
 *
 * @author omera
 */
public class FunctionTableRow extends ParentTableAttr{
    
    public int 
            SCOPE;

    public FunctionTableRow(String NAME, String TYPE, String TYPE_MODIFIER, int SCOPE) {
        super(NAME, TYPE, TYPE_MODIFIER);
        this.SCOPE = SCOPE;
    }

    @Override
    public ArrayList<String> tablevalues() {
        ArrayList<String> record = super.tablevalues();
        //row interchanging
        record.add(record.get(1));
        record.set(1, Integer.toString(SCOPE));
        return record;
    }
    
    @Override
    public ArrayList<String> tableheading() {
        
        ArrayList<String> header = super.tableheading();
        header.add(header.get(1));
        header.set(1,"SCOPE");

        return header;
    }
    
    @Override
    public String keyGenerate() {
        return super.keyGenerate() + "," + SCOPE;
    }

}