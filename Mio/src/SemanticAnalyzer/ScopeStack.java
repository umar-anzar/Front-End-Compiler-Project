/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.LinkedList;

/**
 *
 * @author omera
 */
public class ScopeStack<T> extends LinkedList<T> {
    /*The LinkedList maintains pointers both to the first and last node in the 
    list. So it doesn't have to traverse the list to get to the last node.*/
    
    @Override
    public void push(T element) {
        this.addLast(element);
    }
    
    @Override
    public T pop() {
        if (this.isEmpty()) {
            return null;
        }
        return this.removeLast();
        
    }
    
    @Override
    public boolean isEmpty() {
        return (this.size() <= 0);
    }
    
    public T top() {
        return this.getLast();
    }
}
