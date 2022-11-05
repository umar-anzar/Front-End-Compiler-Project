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

public class ClassTableRow extends ChildTableAttr {
    public String
            STATIC;

    public ClassTableRow(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION, String PARAM_LIST, String TYPE_EXP, 
            String ACCESS_MODIFIER, String STATIC) 
    {
        super(NAME, TYPE, TYPE_MODIFIER, DIMENSION, PARAM_LIST, 
                TYPE_EXP, ACCESS_MODIFIER);
        if (this.ACCESS_MODIFIER.isEmpty() ) {
            this.ACCESS_MODIFIER = "public"; //empty Am means public
        }
        this.STATIC = STATIC;
    }

    @Override
    public ArrayList<String> tablevalues() {
        ArrayList<String> header = super.tablevalues();
        header.add(STATIC);
        return header;
    }

}

