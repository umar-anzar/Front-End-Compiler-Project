/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.FunctionTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */


public class FunctionTable extends HashMap<String, FunctionTableRow> {
        
    public FunctionTableRow add(FunctionTableRow row) {
        return this.put(row.keyGenerate(), row);
    }
    
}
