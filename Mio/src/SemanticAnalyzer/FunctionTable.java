/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.FunctionTableRow;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author omera
 */


public class FunctionTable extends HashMap<String, FunctionTableRow> {
        
    public FunctionTableRow add(FunctionTableRow row) {
        return this.put(row.keyGenerate(), row);
    }
    
    public ArrayList<String> printFT() {
        ArrayList<String> table = new ArrayList<>();
        for (String key : this.keySet() ) {
            table.add(printRow(this.get(key).tablevalues()));
        }
        return table;
    }
    
    public String printRow(ArrayList<String> row) {
        String strRow = "";
        for (String attr : row) {
            strRow += attr +'\t';  
        }
        return strRow;
    }
}
