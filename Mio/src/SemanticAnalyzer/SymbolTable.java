/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import SemanticAnalyzer.TableStructure.ClassTableRow;
import SemanticAnalyzer.TableStructure.FunctionTableRow;
import SemanticAnalyzer.TableStructure.MainTableRow;
import SemanticAnalyzer.TableStructure.ParentTableAttr;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author omera
 */
public class SymbolTable {
    
    //Table--------------------------------------------------------------------
    
    MainTable mt = new MainTable();
    FunctionTable fdt = new FunctionTable();
    
    //Stack---------------------------------------------------------------------
    
    ScopeStack stack = new ScopeStack();
    
    //Global Current Class and Function Data Table------------------------------
    
    ClassTable currentCt;
    
    
    //Insert Functions----------------------------------------------------------
    //insertMT(n,type,tm,dim,pl,te,ac,ext)
    public boolean insertMT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION, String PARAM_LIST, String TYPE_EXP, 
            String ACCESSMODIFIER, String EXTEND) { 
        
        MainTableRow row = lookUpMT(NAME, PARAM_LIST);
        
        //If already declared
        if (row != null) return false;
        
        row = new MainTableRow(NAME, TYPE, TYPE_MODIFIER, DIMENSION, 
                PARAM_LIST, TYPE_EXP, ACCESSMODIFIER, EXTEND);
        mt.add(row);
                
        
        return true;
    }
    
    public boolean insertCT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION, String PARAM_LIST, String TYPE_EXP, 
            String ACCESS_MODIFIER, String STATIC) { 
        
        ClassTableRow row = lookUpDT(NAME, PARAM_LIST, currentCt);
        
        //If already declared
        if (row != null) return false;
        
        row = new ClassTableRow(NAME, TYPE, TYPE_MODIFIER, DIMENSION, 
                PARAM_LIST, TYPE_EXP, ACCESS_MODIFIER, STATIC);
        currentCt.add(row);

        return true;
    }
    
    public boolean insertFT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION) { 
        
        String Type = lookUpFT(NAME, "", currentCt, new Modifier());
        
        //If already declared
        if (Type != null) return false;
        
        FunctionTableRow row = new FunctionTableRow(NAME, TYPE, TYPE_MODIFIER, 
                DIMENSION, stack.scope);
        fdt.add(row);
        
        return false;
    }
    
    //LookUp Functions----------------------------------------------------------
    
    /**
     * Function ,Class and Var in main table
     * @param NAME
     * @param PARAM_LIST
     * @return 
     */
    public MainTableRow lookUpMT(String NAME, String PARAM_LIST) {
        return mt.get(NAME+","+PARAM_LIST);
    }
    
    /**
     * Function and Var Use in class table
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public ClassTableRow lookUpDT(String NAME, String PARAM_LIST, 
            ClassTable ct) 
    {      
        return ct.get(NAME+","+PARAM_LIST);
    }
    
    /**
     * Function and Var Use in Function table
     * Search in scope stack to find
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public String/*RetType*/ lookUpFT(String NAME, String PARAM_LIST, 
            ClassTable ct, Modifier out) 
    {
        stack.resetIter(); //Bring pointer on top of the stack
        
        ParentTableAttr row = null;
        boolean found = false;
        
        // Search In Scope Stack
        if (! PARAM_LIST.isEmpty()) {
            while (stack.iter.hasNext()) {
                int scope = stack.iter.next();
                row = fdt.get(NAME+Integer.toString(scope));
                if (row != null) {
                    found = true;
                    break;
                }
            }
        }
                
        // Search in class 
        if (ct != null && !found) {
            if(lookUpDT(NAME, PARAM_LIST, ct) != null) {
                row = lookUpDT(NAME, PARAM_LIST, ct);
                found = true;
            } else if ( BFS_inheritedClasses(NAME, PARAM_LIST, ct) ) {
                found = true;
            }
        }

        
        // Search in main table
        if (lookUpMT(NAME, PARAM_LIST) != null && !found) {
            row = lookUpMT(NAME, PARAM_LIST);
            found = true;
        }

        
        // If row is found
        if (found) {
            if (row instanceof FunctionTableRow functionTableRow) {
                out.setTM(functionTableRow.TYPE_MODIFIER );
            }
            else if (row instanceof ClassTableRow classTableRow) {
                out.setAM(classTableRow.ACCESS_MODIFIER );
                out.setTM(classTableRow.TYPE_MODIFIER );
            }
            else if (row instanceof MainTableRow mainTableRow) {                     
                out.setAM(mainTableRow.ACCESS_MODIFIER );
                out.setTM(mainTableRow.TYPE_MODIFIER );
            }
            
            return row.TYPE;
        }
        
        return null;
    }
    
    /**
     * Searching in Multilevel and Hierarchical Inheritance 
     * Left to Right breath first search
     * @param NAME
     * @param PARAM_LIST
     * @param Initialnode
     * @return 
     */
    public boolean BFS_inheritedClasses (String NAME, String PARAM_LIST, ClassTable ct) {
        
        MainTableRow classrow = lookUpMT(ct.NAME, "");
        
        //Initial inherited class list from current class 
        String[] Initialnodes = classrow.inheritedClasses();


        MyQueue<String> queue = new MyQueue<>();
        HashSet<String> visited = new HashSet<>();

        //Adding all the inherited class names of current class into queue and visited set
        for (String node : Initialnodes) {
            queue.enqueue(node);
            visited.add(node);
        }


        //while queue not empty
        while (!queue.isEmpty()) {

            String current = queue.dequeue();//left most becomes current class name, popFirst from queue
            System.out.print(current+"->");

            //If search value found in classtable return true
            if ( isInClass(NAME, PARAM_LIST, current)) {
                System.out.println("true");
                return true;
            }
            
            //Open the edges and add them in queue and visited so that they will visit once from queue
            for (String node : edges(current)) {
                if (!visited.contains(node)) {
                    queue.enqueue(node);
                    visited.add(node);
                }
            }

        }

        return false;
    }
    
    public boolean isInClass(String NAME, String PARAM_LIST, String className) {
        MainTableRow row = lookUpMT(className, "");
        if (!row.isAbstract()) {
            ClassTable ct =  lookUpMT(className, "").DT;
            if (lookUpDT(NAME, PARAM_LIST, ct) != null )
                return true;
        }
        return false;
    }
    public String[] edges(String className) {
        return lookUpMT(className, "").inheritedClasses();
    }
    
    public void printST() {
        ArrayList<String> mainTable = mt.printMT();
        ArrayList<String> classTable;
        ArrayList<String> functionTable = fdt.printFT();
        
        for (String row : mainTable) {
            System.out.println(row);
        }
        
    }
    
    public static void main(String[] args) {
        SymbolTable x = new  SymbolTable();
        x.insertMT("A", "Class", "const", "[][]", "", "", "", "");
        x.insertMT("B", "Class", "const", "", "", "", "", "E,F");
        x.insertMT("C", "Class", "const", "[][]", "", "", "", "D");
        x.insertMT("D", "Class", "const", "[][]", "", "", "", "G");
        x.insertMT("E", "Class", "const", "[][]", "", "", "", "");
        x.insertMT("F", "Class", "const", "[][]", "", "", "", "");
        x.insertMT("G", "Class", "const", "[][]", "", "", "", "");
        
        
        x.insertMT("X", "Class", "const", "[][]", "", "", "", "A,B,C");
        
        
        MainTableRow row = x.lookUpMT("A", "");
        x.currentCt = row.DT;
        x.insertCT("a", "point", "", "", "", "", "", "Static");
        
        row = x.lookUpMT("B", "");
        x.currentCt = row.DT;
        x.insertCT("b", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("C", "");
        x.currentCt = row.DT;
        x.insertCT("c", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("D", "");
        x.currentCt = row.DT;
        x.insertCT("d", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("E", "");
        x.currentCt = row.DT;
        x.insertCT("e", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("F", "");
        x.currentCt = row.DT;
        x.insertCT("f", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("G", "");
        x.currentCt = row.DT;
        x.insertCT("g", "point", "", "", "", "", "private", "Static");
        
        row = x.lookUpMT("X", "");
        x.currentCt = row.DT;
        x.insertCT("x", "point", "", "", "", "", "private", "Static");
        
        x.BFS_inheritedClasses("e", "", x.currentCt);
        
        //x.printST();
    }
}

class Modifier {
    public String
            TYPE_MODIFIER,
            ACCESS_MODIFIER;

    public void setTM(String TYPE_MODIFIER) {
        this.TYPE_MODIFIER = TYPE_MODIFIER;
    }

    public void setAM(String ACCESS_MODIFIER) {
        this.ACCESS_MODIFIER = ACCESS_MODIFIER;
    }
       
    public String getTM() {
        return TYPE_MODIFIER;
    }

    public String getAM() {
        return ACCESS_MODIFIER;
    }
    
}