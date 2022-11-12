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
    
    //Semantic Error------------------------------------------------------------
    ArrayList<String> error = new ArrayList<>();
    
    //Stack Functions-----------------------------------------------------------
    public void push(){stack.push();}
    public void pop(){stack.pop();}
    public int getScope(){return stack.scope;}
    
    //Insert Functions----------------------------------------------------------
    
    //insertMT(n,type,tm,dim,pl,te,ac,pc,ext)
    public boolean insertMT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String PARAM_LIST,
            String ACCESSMODIFIER, String PARAMETRIC_CLASS, String EXTEND) { 
        
        MainTableRow row = lookUpMT(NAME, PARAM_LIST);
        
        //If already declared
        if (row != null) return false;
        
        row = new MainTableRow(NAME, TYPE, TYPE_MODIFIER, 
                PARAM_LIST, ACCESSMODIFIER, PARAMETRIC_CLASS, EXTEND);
        mt.add(row);
        
        //If row is a class then class table is put in currentCt
        if (row.isClass())
            currentCt = row.DT;

        
        return true;
    }
    
    //insertCT(n,type,tm,dim,pl,te,ac,Static)
    public boolean insertCT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String PARAM_LIST, 
            String ACCESS_MODIFIER, String STATIC) { 
        
        ClassTableRow row = lookUpDT_singleClass(NAME, PARAM_LIST, currentCt);
        
        //If already declared
        if (row != null) return false;
        
        row = new ClassTableRow(NAME, TYPE, TYPE_MODIFIER, 
                PARAM_LIST, ACCESS_MODIFIER, STATIC);
        
        currentCt.add(row);

        return true;
    }
    
    //insertFT(n,type,tm,dim)
    public boolean insertFT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String DIMENSION) { 
        
        String Type = lookUpFT(NAME, "", currentCt, new RetOutInfo());
        
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
     * Look up only in class and not parents that is pass as argument
     * @param NAME
     * @param PARAM_LIST
     * @param ct
     * @return 
     */
    public ClassTableRow lookUpDT_singleClass(String NAME, String PARAM_LIST, ClassTable ct) {
        return ct.get(NAME +","+ PARAM_LIST);
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

        //Check in class which is in argument
        if (ct != null) {
            //Check in class first if found then return
            if (lookUpDT_singleClass(NAME, PARAM_LIST, ct) != null) {
                return lookUpDT_singleClass(NAME, PARAM_LIST, ct);
            }
        }
        
        //Check in all parents of the class which is in argument
        if (ct != null) {
            String className = BFS_inheritedClasses(NAME, PARAM_LIST, ct);
            if (className != null) {
                ct = lookUpMT(className, "").DT; //class dec
                return lookUpDT_singleClass(NAME, PARAM_LIST, ct);
            }
        }

        return null;
    }
    
    /**
     * This look use only in class's function dec
     * @param NAME
     * @param PARAM_LIST
     * @param ct
     * @param line
     * @return 
     */
    public ClassTableRow lookUpDT(String NAME, String PARAM_LIST, 
            ClassTable ct, int line) {
        ClassTableRow row = lookUpDT(NAME, PARAM_LIST, ct);
        canOverRide(row, NAME, line);
        return row;
    } 
    
    /**
     * Function and Var Use in Function table
     * Search in scope stack to find
     * @param NAME
     * @param PARAMETER_LIST
     * @return 
     */
    public String/*RetType*/ lookUpFT(String NAME, String PARAM_LIST, 
            ClassTable ct, RetOutInfo out) 
    {
        stack.resetIter(); //Bring pointer on top of the stack
        
        ParentTableAttr row = null;
        boolean found = false;
        
        // Search In Scope Stack
        if (!found) {
            row = loopUpScopeStack(NAME, PARAM_LIST);
            if (row != null)
                    found = true;
        }
                
        // Search in class 
        if (!found) {
            row = lookUpDT(NAME, PARAM_LIST, ct);
            if (row != null)
                    found = true;
        }

        
        // Search in main table
        if (!found) {
            row = lookUpMT(NAME, PARAM_LIST);
            if (row != null)
                found = true;
        }

        
        // If row is found
        if (found) {
            if (row instanceof FunctionTableRow functionTableRow) {
                out.TYPE_MODIFIER = functionTableRow.TYPE_MODIFIER ;
            }
            else if (row instanceof ClassTableRow classTableRow) {
                out.ACCESS_MODIFIER = classTableRow.ACCESS_MODIFIER ;
                out.TYPE_MODIFIER = classTableRow.TYPE_MODIFIER ;
            }
            else if (row instanceof MainTableRow mainTableRow) {                     
                out.ACCESS_MODIFIER= mainTableRow.ACCESS_MODIFIER ;
                out.TYPE_MODIFIER = mainTableRow.TYPE_MODIFIER ;
            }
            
            return row.TYPE;
        }
        
        return null;
    }
    
    public FunctionTableRow loopUpScopeStack(String NAME, String PARAM_LIST) {
        FunctionTableRow row = null;
        if (! PARAM_LIST.isEmpty()) {
            while (stack.iter.hasNext()) {
                int scope = stack.iter.next();
                row = fdt.get(NAME+Integer.toString(scope));
                if (row != null) {
                    return row;
                }
            }
        }
        return row;
    }
    
    //Searching of multiple inheritance functions-------------------------------
    /**
     * Searching in Multilevel and Hierarchical Inheritance 
     * Left to Right breath first search
     * @param NAME
     * @param PARAM_LIST
     * @param Initialnode
     * @return 
     */
    public String BFS_inheritedClasses (String NAME, String PARAM_LIST, ClassTable ct) {
        
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
                return current;
            }
            
            //Open the edges and add them in queue and visited so that they will visit once from queue
            for (String node : edges(current)) {
                if (!visited.contains(node)) {
                    queue.enqueue(node);
                    visited.add(node);
                }
            }

        }

        return null;
    }
    public boolean isInClass(String NAME, String PARAM_LIST, String className) {
        MainTableRow row = lookUpMT(className, "");
        if (!row.isAbstract()) {
            ClassTable ct =  lookUpMT(className, "").DT;
            if (lookUpDT_singleClass(NAME, PARAM_LIST, ct) != null )
                return true;
        }
        return false;
    }
    public String[] edges(String className) {
        return lookUpMT(className, "").inheritedClasses();
    }
    
    //Print table functions-----------------------------------------------------
    public void printST() {
        ArrayList<String> mainTable = mt.printMT();
        ArrayList<String> classTable;
        ArrayList<String> functionTable = fdt.printFT();
        
        for (String row : mainTable) {
            System.out.println(row);
        }
        
        for (String row : functionTable) {
            System.out.println(row);
        }
        
    }
    
    //Error Functions-----------------------------------------------------------
    public void addError(String statement) {
        error.add(statement);
    }
    public void addError(int line, String statement, String identifier) {
        error.add("line no "+line+": "+statement+", "+identifier);
    }
    public void printError() {
        if (lookUpMT("begin", "") == null)
            error.add("File has no executable function {begin}");
        for (String string : error) {
            System.out.println(string);
        }
    }
    
    //helpful functions--------------------------------------------------------
    public void canInhert(String className, int line) {
        MainTableRow row = lookUpMT(className, "");
        if(row != null) {
            if (row.isClass()) {
                if (row.isFinal()){
                    addError(line,"Class is final, cannot extend",className);
                }
            }
        } else {
            addError(line,"Class not declared",className);
        }
    }
    public void canOverRide(ClassTableRow row, String NAME, int line) {
        if(row != null)
            if (row.isFunction() && row.isFinal()) 
                addError(line,"Function is final, cannot override",NAME);
    }
    
    public static void main(String[] args) {
        SymbolTable x = new  SymbolTable();
        
        
        x.insertMT("B", "Class", "const", "", "", "", "C,H");
        
        x.insertMT("var1", "int", "const", "", "", "", "");
        
        x.insertMT("C", "Class", "const", "", "", "", "E,G");

        
        x.insertMT("x2", "int", "const", "", "", "", "");
        x.insertMT("a3", "int", "const", "", "", "", "");
        
        x.insertMT("D", "Class", "const", "", "", "", "E,F");
        
        x.insertMT("b4", "int", "const", "", "", "", "");
        x.insertMT("q5", "int", "const", "", "", "", "");
        x.insertMT("var6", "int", "const", "", "", "", "");
        x.insertMT("asd", "Class", "const", "[][]", "", "", "");
        x.insertMT("rasd", "Class", "const", "[][]", "", "", "");
        
        x.insertMT("E", "Class", "const", "", "", "", "G,H");
        
        
        x.insertMT("F", "Class", "const", "", "", "", "");
        
        
        x.insertMT("G", "Class", "const", "", "", "", "");
        
        
        
        x.insertMT("H", "Class", "const", "", "", "", "");
        
        
        x.insertMT("A", "Class", "const", "", "", "", "D,B,E");
        
        
        MainTableRow row;
        
        row = x.lookUpMT("B", "");
        x.currentCt = row.DT;
        x.insertCT("b", "point", "", "", "private", "Static");
        
        row = x.lookUpMT("C", "");
        x.currentCt = row.DT;
        x.insertCT("c", "point", "", "", "private", "Static");
        
        row = x.lookUpMT("D", "");
        x.currentCt = row.DT;
        x.insertCT("d", "point", "", "", "private", "Static");
        
        row = x.lookUpMT("E", "");
        x.currentCt = row.DT;
        x.insertCT("e", "point", "", "",  "private", "Static");
        
        row = x.lookUpMT("F", "");
        x.currentCt = row.DT;
        x.insertCT("f", "point", "", "",  "private", "Static");
        
        row = x.lookUpMT("G", "");
        x.currentCt = row.DT;
        x.insertCT("g", "int", "", "",  "private", "Static");
        x.insertCT("g1", "int", "", "",  "private", "Static");
        x.insertCT("g2", "int", "", "",  "private", "Static");

        
        row = x.lookUpMT("H", "");
        x.currentCt = row.DT;
        x.insertCT("h", "point", "", "", "private", "Static");

        
        row = x.lookUpMT("A", "");
        x.currentCt = row.DT;
        x.insertCT("a", "point", "", "", "", "Static");
        
        
        System.out.println(x.lookUpFT("e", "", x.currentCt, new RetOutInfo()));
        
//        x.printST();
    }
    
}

