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
            TYPE="",
            TYPE_MODIFIER="",
            ACCESS_MODIFIER="",
            PARAMETRIC_CLASS="",
            EXTEND="";

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

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
