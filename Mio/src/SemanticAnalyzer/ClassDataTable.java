/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.ArrayList;

/**
 *
 * @author omera
 */
public class ClassDataTable {
    
    public String
            ABSTRACT,
            STATIC,
            CONSTANT,
            TYPE,
            SIZE,
            ACCESSMODIFIER,
            DIMENSION,
            NAME;
    
    public ArrayList<String>
            PARAMETER;

    /**
     * For Function Dec
     * @param ABSTRACT
     * @param STATIC
     * @param CONSTANT
     * @param TYPE
     * @param ACCESSMODIFIER
     * @param DIMENSION
     * @param NAME
     * @param PARAMETER 
     */
    public ClassDataTable(String ABSTRACT, String STATIC, String CONSTANT, String TYPE, String ACCESSMODIFIER, String DIMENSION, String NAME, ArrayList<String> PARAMETER) {
        this.ABSTRACT = ABSTRACT;
        this.STATIC = STATIC;
        this.CONSTANT = CONSTANT;
        this.TYPE = TYPE;
        this.ACCESSMODIFIER = ACCESSMODIFIER;
        this.DIMENSION = DIMENSION;
        this.NAME = NAME;
        this.PARAMETER = PARAMETER;
    }

    /**
     * For Attribute DEC
     * @param STATIC
     * @param CONSTANT
     * @param TYPE
     * @param SIZE
     * @param ACCESSMODIFIER
     * @param DIMENSION
     * @param NAME 
     */
    public ClassDataTable(String STATIC, String CONSTANT, String TYPE, String SIZE, String ACCESSMODIFIER, String DIMENSION, String NAME) {
        this.STATIC = STATIC;
        this.CONSTANT = CONSTANT;
        this.TYPE = TYPE;
        this.SIZE = SIZE;
        this.ACCESSMODIFIER = ACCESSMODIFIER;
        this.DIMENSION = DIMENSION;
        this.NAME = NAME;
    }
    
    
}



