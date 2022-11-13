/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.HashMap;

/**
 *
 * @author omera
 */
public class PrimitiveType {
    //In Bytes
    public static final double INTEGER_SIZE = 8;
    public static final double POINT_SIZE = 8;
    public static final double CHAR_SIZE = 4;
    public static final double BOOL_SIZE = 2;
    public static final double VAL_SIZE = 8;
    
    public static final String INTEGER = "intConst";
    public static final String POINT = "floatConst";
    public static final String CHAR = "charConst";
    public static final String BOOL = "boolConst";
    public static final String VAL = "dynConst";

    public static final HashMap<String,String> typeDictionary = new HashMap<>(){
        {
            put(INTEGER, "int");
            put(POINT, "point");
            put(CHAR, "char");
            put(BOOL, "bool");
            put(VAL, "val");
            put("int", "int");
            put("point", "point");
            put("char", "char");
            put("bool", "bool");
            put("val", "val");
            
            put("package", "package");
            put("import", "import");
            put("Class", "Class");
        }
    };
    
    public static final boolean isPrimitiveType(String TYPE) {
        for (String typ : typeDictionary.keySet()) {
            if (typ.equals(TYPE)) {
                return true;
            } else if ("".equals(TYPE)) {
                return true;
            }
        }
        return false;
    }
}
