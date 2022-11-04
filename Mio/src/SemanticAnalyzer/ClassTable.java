/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.ClassTableRow;
import java.util.HashMap;

/**
 *
 * @author omera
 */

public class ClassTable extends HashMap<String, ClassTableRow> {
    
    public ClassTableRow add(ClassTableRow row) {
        return this.put(row.keyGenerate(), row);
    }
}


