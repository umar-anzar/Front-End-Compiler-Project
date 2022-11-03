/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer.TableStructure;

import SemanticAnalyzer.ClassDataTable;

/**
 *
 * @author omera
 */
public class MainTableRow extends ChildTableAttr{
    
    public String 
            EXTEND="";      
    public ClassDataTable 
            DT = new ClassDataTable();

    
    public MainTableRow(String NAME, String TYPE) {
        super(NAME, TYPE);
    }

}