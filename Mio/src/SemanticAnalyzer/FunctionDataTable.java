/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.FunctionDataTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */


public class FunctionDataTable extends HashMap<String, FunctionDataTableRow> {
        
    public FunctionDataTableRow add(FunctionDataTableRow row) {
        return this.put(row.keyGenerate(), row);
    }
    
}
