/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author omera
 */
public class MainTable {
    
    public String 
            ABSTRACT,
            CONSTANT,
            TYPE,
            SIZE,
            ACCESSMODIFIER,
            DIMENSION,
            NAME;

    public ArrayList<String>
            EXTEND;
    
    public HashMap<String, ClassDataTable> DT;
    
    public HashMap<String, FunctionDataTable> FDT;

    public MainTable() {
        DT = new HashMap<>();
    }

    /**
     * For Class DEC
     * @param ABSTRACT
     * @param CONSTANT
     * @param TYPE
     * @param ACCESSMODIFIER
     * @param NAME
     * @param EXTEND 
     */
    public MainTable(String ABSTRACT, String CONSTANT, String TYPE, String ACCESSMODIFIER, String NAME, ArrayList<String> EXTEND) {
        this();
        this.ABSTRACT = ABSTRACT;
        this.CONSTANT = CONSTANT;
        this.TYPE = TYPE;
        this.ACCESSMODIFIER = ACCESSMODIFIER;
        this.NAME = NAME;
        this.EXTEND = EXTEND;
        this.DT = new HashMap<>();
    }

    /**
     * For Function DEC
     * @param TYPE
     * @param DIMENSION
     * @param NAME
     * @param PARAMETER 
     */
    public MainTable(String TYPE, String DIMENSION, String NAME) {
        this();
        this.TYPE = TYPE;
        this.DIMENSION = DIMENSION;
        this.NAME = NAME;
    }

    /**
     * For global DEC of variables and constant
     * @param CONSTANT
     * @param TYPE
     * @param DIMENSION
     * @param NAME 
     */
    public MainTable(String CONSTANT, String TYPE, String DIMENSION, String NAME) {
        this();
        this.CONSTANT = CONSTANT;
        this.TYPE = TYPE;
        this.DIMENSION = DIMENSION;
        this.NAME = NAME;
    }
    
    
    
    
}
