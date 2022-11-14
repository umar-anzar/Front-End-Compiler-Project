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
    
    //Table---------------------------------------------------------------------
    
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
    
    //Current Class Ref Functions-----------------------------------------------
    public void referenceOn(MainTableRow row) {
        if (row.isClass())
            currentCt = row.DT;
    }
    public void referenceOff() {
        currentCt = null;
    }
    
    //Insert Functions----------------------------------------------------------
    
    //insertMT(n,type,tm,dim,pl,te,ac,pc,ext)
    public boolean insertMT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String PARAM_LIST,
            String ACCESSMODIFIER, String PARAMETRIC_CLASS, String EXTEND, int line) { 
        
        MainTableRow row = lookUpMT(NAME, PARAM_LIST);
        
        //If already declared
        if (row != null) return false;
        
        //if not primitive type then search in lookUpMT also check whether obj can be made
        typeExist(TYPE, line);
        
        row = new MainTableRow(NAME, TYPE, TYPE_MODIFIER, 
                PARAM_LIST, ACCESSMODIFIER, PARAMETRIC_CLASS, EXTEND);
        mt.add(row);
        
        //If row is a class then class table is put in currentCt
        referenceOn(row);

        
        return true;
    }
    
    //insertCT(n,type,tm,dim,pl,te,ac,Static)
    public boolean insertCT(String NAME, String TYPE, String TYPE_MODIFIER, 
            String PARAM_LIST, 
            String ACCESS_MODIFIER, String STATIC, int line) { 
        
        ClassTableRow row = lookUpDT_singleClass(NAME, PARAM_LIST, currentCt);
        
        //If already declared
        if (row != null) return false;
        
        //if not primitive type then search in lookUpMT also check whether obj can be made
        typeExist(TYPE, line);
        
        row = new ClassTableRow(NAME, TYPE, TYPE_MODIFIER, 
                PARAM_LIST, ACCESS_MODIFIER, STATIC);
        
        currentCt.add(row);

        return true;
    }
    
    //insertFT(n,type,tm,dim)
    public boolean insertFT(String NAME, String TYPE, String TYPE_MODIFIER, int line) { 
        
        FunctionTableRow row = lookUpScopeStack(NAME, "");
                
        //If already declared
        if (row != null) return false;
        
        //if not primitive type then search in lookUpMT also check whether obj can be made
        typeExist(TYPE, line);
        
        row = new FunctionTableRow(NAME, TYPE, TYPE_MODIFIER, stack.scope);
        fdt.add(row);
        
        return true;
    }
    
    
    public boolean insertFT_Param(String NAME_LIST, String TYPE_LIST, int line) {
        String [] NAMES = NAME_LIST.split(",") , 
                  TYPES = TYPE_LIST.split(",");
        for (int i = 0; i < TYPES.length; i++) {
            
            //if not primitive type then search in lookUpMT also check whether obj can be made
            typeExist(TYPES[i], line);

            insertFT(NAMES[i], TYPES[i], "",line);
        }
        return true;
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
     * @param PARAM_LIST
     * @param ct
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
     * @return ClassTableRow
     */
    public ClassTableRow lookUpDT(String NAME, String PARAM_LIST, 
            ClassTable ct, int line) {
        ClassTableRow row = lookUpDT(NAME, PARAM_LIST, ct);
        canOverRide(row, NAME, line);
        return row;
    } 
    
    /**
     * Function and Var Use in Function table
     * @param NAME
     * @param PARAM_LIST
     * @param ct
     * @param out
     * @return String
     */
    public String/*RetType*/ lookUpFT(String NAME, String PARAM_LIST, 
            ClassTable ct, RetOutInfo out) 
    {
        
        
        ParentTableAttr row = null;
        boolean found = false;
        
        // Search In Scope Stack
        if (!found) {
            row = lookUpScopeStack(NAME, PARAM_LIST);
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
    
    public FunctionTableRow lookUpScopeStack(String NAME, String PARAM_LIST) {
        stack.resetIter(); //Bring pointer on top of the stack
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
     * @param NAME
     * @param PARAM_LIST
     * @param ct
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
    
    //Compatibility functions---------------------------------------------------
    public String compatibility(String TYPE1, String TYPE2, String operator, int line) {
        TYPE1 = PrimitiveType.airthematicTypeDictionary.get(TYPE1);
        TYPE2 = PrimitiveType.airthematicTypeDictionary.get(TYPE2);
        
        
        
        if (compatibility_op(TYPE1, operator) && compatibility_op(TYPE2, operator)) {
            
            if (TYPE1.equals(TYPE2)) {
                if ("bool".equals(TYPE1)) {
                    String [] boolType = PrimitiveType.operatorDictionary.get(operator);
                    if (boolType != null) {
                        return boolType[0];
                    }
                }
            }
            if ("str".equals(TYPE1) || "str".equals(TYPE2)) {
                return "str";
            }
            
            if (PrimitiveType.typeSize.get(TYPE1) > PrimitiveType.typeSize.get(TYPE2))
                return TYPE1;
            else
                return TYPE2;
            
        }
        
        addError(line, "Incompatible operation of ("+operator+")", "between "+TYPE1+" and "+TYPE2);
        return TYPE1;
    }
    public boolean compatibility_op(String TYPE, String operator) {
        if (PrimitiveType.isAirthematicType(TYPE)) {
            TYPE = PrimitiveType.airthematicTypeDictionary.get(TYPE);
        }
        String [] types = PrimitiveType.operatorDictionary.get(operator);
        for (String type : types) {
            if (type.equals(TYPE)) {
                return true;
            }
        }
        return false;
    }
    public boolean compareType(String T1, String T2, String NAME, int line) {
        
        if (T1.equals(T2)) {
            
            return true;
        } else {
            
            if (lookUpMT(T1, "") != null && lookUpMT(T2, "")!= null)
                ;//HERE I ALSO HAVE TO CHECK PARENT REFERENCE CHECK 
        }
        
        
        T1 = PrimitiveType.airthematicTypeDictionary.get(T1);
        T2 = PrimitiveType.airthematicTypeDictionary.get(T2);
        
        if (T1!= null && T2 != null) {
            
            if (T1.equals(T2)) {
                return true;
                
            } else {
                Integer T1_size = PrimitiveType.typeSize.get(T1), 
                        T2_size = PrimitiveType.typeSize.get(T2);
                
                if (T1_size != null && T2_size != null) {
                    if (T1_size >= T2_size)
                        return true;
                }
                
            }

        }


        addError(line,"Incompatible Type Assign to variable",NAME);
        return false;
    }
    
    //Print table functions-----------------------------------------------------
    public void printST() {
        ArrayList<String> mainTable = mt.printMT();
        ArrayList<String> functionTable = fdt.printFT();
        
        for (String row : mainTable) {
            System.out.println(row);
        }
        System.out.println("");
        for (String row : functionTable) {
            System.out.println(row);
        }
        System.out.println("");
        
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
        if (!error.isEmpty())
            System.out.println("ERROR LIST:");
        for (String string : error) {
            System.out.println(string);
        }
    }
    
    //helpful functions---------------------------------------------------------
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
    public boolean canMakeObj(String TYPE, int line) {
        MainTableRow row = lookUpMT(TYPE, "");
        if (lookUpMT(TYPE, "") != null)
            if ( !row.isAbstract() )
                return true;
            else
                addError(line, "Class Data Type is Abstract, Cannot be initialize", TYPE);
        return false;
    }
    public boolean typeExist(String TYPE,int line) {
        
        //Break array part to get type int[][] -> int only
        TYPE = TYPE.split("\\[")[0];
        
        if (!PrimitiveType.isPrimitiveType(TYPE))
            if ( lookUpMT(TYPE, "") == null ) {
                addError(line, "Unknown Type", TYPE);
                return false;
            }
        return true;
    }
//IMP
//                } else if (!canMakeObj(TYPE)) /*If cannot make objs*/{
//                addError(line, "Class Data Type is Abstract", TYPE);
//            }
    

    
    public static void main(String[] args) {
        SymbolTable x = new  SymbolTable();
        
        
        x.insertMT("B", "Class", "const", "", "", "", "C,H",0);
        
        x.insertMT("var1", "int", "const", "", "", "", "",0);
        
        x.insertMT("C", "Class", "const", "", "", "", "E,G",0);

        
        x.insertMT("x2", "int", "const", "", "", "", "",0);
        x.insertMT("a3", "int", "const", "", "", "", "",0);
        
        x.insertMT("D", "Class", "const", "", "", "", "E,F",0);
        
        x.insertMT("b4", "int", "const", "", "", "", "",0);
        x.insertMT("q5", "int", "const", "", "", "", "",0);
        x.insertMT("var6", "int", "const", "", "", "", "",0);
        x.insertMT("asd", "Class", "const", "[][]", "", "", "",0);
        x.insertMT("rasd", "Class", "const", "[][]", "", "", "",0);
        
        x.insertMT("E", "Class", "const", "", "", "", "G,H",0);
        
        
        x.insertMT("F", "Class", "const", "", "", "", "",0);
        
        
        x.insertMT("G", "Class", "const", "", "", "", "",0);
        
        
        
        x.insertMT("H", "Class", "const", "", "", "", "",0);
        
        
        x.insertMT("A", "Class", "const", "", "", "", "D,B,E",0);
        
        
        MainTableRow row;
        
        row = x.lookUpMT("B", "");
        x.currentCt = row.DT;
        x.insertCT("b", "point", "", "", "private", "Static",0);
        
        row = x.lookUpMT("C", "");
        x.currentCt = row.DT;
        x.insertCT("c", "point", "", "", "private", "Static",0);
        
        row = x.lookUpMT("D", "");
        x.currentCt = row.DT;
        x.insertCT("d", "point", "", "", "private", "Static",0);
        
        row = x.lookUpMT("E", "");
        x.currentCt = row.DT;
        x.insertCT("e", "point", "", "",  "private", "Static",0);
        
        row = x.lookUpMT("F", "");
        x.currentCt = row.DT;
        x.insertCT("f", "point", "", "",  "private", "Static",0);
        
        row = x.lookUpMT("G", "");
        x.currentCt = row.DT;
        x.insertCT("g", "int", "", "",  "private", "Static",0);
        x.insertCT("g1", "int", "", "",  "private", "Static",0);
        x.insertCT("g2", "int", "", "",  "private", "Static",0);

        
        row = x.lookUpMT("H", "");
        x.currentCt = row.DT;
        x.insertCT("h", "point", "", "", "private", "Static",0);

        
        row = x.lookUpMT("A", "");
        x.currentCt = row.DT;
        x.insertCT("a", "point", "", "", "", "Static",0);
        
        
        System.out.println(x.lookUpFT("e", "", x.currentCt, new RetOutInfo()));
        
//        x.printST();
    }
    
}

