/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.ClassTableRow;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author omera
 */

public class ClassTable extends HashMap<String, ClassTableRow> {
    
    public String NAME = "";
    
    public ClassTableRow add(ClassTableRow row) {
        return this.put(row.keyGenerate(), row);
    }
    
    public void setName(String n) {
        NAME = n;
    }
    
    public ArrayList<String> printCT() {
        ArrayList<String> table = new ArrayList<>();
        boolean headerOn = true;
        for (String key : this.keySet() ) {
            if (headerOn) {
                table.add(printHeader(this.get(key).tableheading()));
                headerOn = false;
            } 
            table.add(printRow(this.get(key).tablevalues()));
        }
        return table;
    }
    
    public String printRow(ArrayList<String> row) {
        String strRow = "";
        for (String attr : row) {
            if (attr == null)
                attr = "--";
            else if (attr.isEmpty())
                attr = "--";
            strRow += attr +'\t';  
        }
        return strRow;
    }
    
    public String printHeader(ArrayList<String> header) {
        String strRow = "";
        for (String attr : header) {
            strRow += attr +'\t';  
        }
        return strRow;
    }
}


