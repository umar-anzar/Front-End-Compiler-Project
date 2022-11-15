/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

/**
 *
 * @author omera
 */
public class RetOutInfo {
    public String
            NAME="",
            NAME2="",/*using in names of parameters*/
            beforeOpName = "", /*use before operator comes to have operator compaitble error*/
            TYPE="",
            TYPE_MODIFIER="",
            ACCESS_MODIFIER="",
            PARAMETRIC_CLASS="",
            EXTEND="",
            STATIC = "",
            TYPE2 = "",/*using in types of parameters*/
            OPERATOR = "",/*Used for not ! operator*/
            FLAGOP = "";/*Used for flag pm*/
    
    public boolean
            consturctor = false;

    public void onConsturctor() {
        this.consturctor = true;
    }
    public void offConsturctor() {
        this.consturctor = false;
    }
    
    

}
