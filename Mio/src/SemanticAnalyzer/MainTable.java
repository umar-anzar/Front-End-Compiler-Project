/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.MainTableRow;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author omera
 */

public class MainTable extends HashMap<String, MainTableRow> {

    public void add(MainTableRow row) {
        this.put(row.keyGenerate(), row);
    }
    
    public ArrayList<String> printMT() {
        ArrayList<String> table = new ArrayList<>();
        for (String key : this.keySet() ) {
            table.add(printRow(this.get(key).tablevalues()));
            
            if (this.get(key).DT != null) {
                table.add("\n");
                for (String classRow : this.get(key).DT.printCT()) {
                    table.add("\t"+classRow);
                }
                table.add("\n");
            }
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