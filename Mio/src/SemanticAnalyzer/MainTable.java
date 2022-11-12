/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.MainTableRow;
import java.util.ArrayList;
import java.util.Arrays;
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
        boolean headerOn = true;
        for (String key : this.keySet() ) {
            
            //header on once
            if (headerOn) {
                table.add(printHeader(this.get(key).tableheading()));
                headerOn = false;
            }
            
            table.add(printRow(this.get(key).tablevalues()));
            if (this.get(key).DT != null) {
                
                headerOn = true;//when printing of class start MT header gets on
                
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
            if (attr == null)
                attr = "--";
            else if (attr.isEmpty())
                attr = "--";
            else if("Abstract".equals(attr))
                attr = attr.substring(0,3);
            
            strRow += attr +"\t\t";  
        }
        return strRow;
    }
    
    public String printHeader(ArrayList<String> header) {
        String strRow = "";
        for (String attr : header) {
            strRow += attr +"\t\t";  
        }
        return strRow;
    }
}