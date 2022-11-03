/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.MainTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */

public class MainTable extends HashMap<String, MainTableRow>{

    public void add(MainTableRow row) {
        this.put(row.keyGenerate(), row);
    }
    
    
}