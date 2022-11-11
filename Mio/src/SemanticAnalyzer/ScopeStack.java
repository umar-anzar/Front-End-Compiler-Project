/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemanticAnalyzer;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author omera
 */
public class ScopeStack extends LinkedList<Integer> {
    /*The LinkedList maintains pointers both to the first and last node in the 
    list. So it doesn't have to traverse the list to get to the last node.*/
    
    Iterator<Integer> iter;
    Integer scope = 0;

    public void resetIter() {
        iter = this.iterator();
    }
    
    public void push() {
        scope++;
        super.push(scope);
    }
    
    @Override
    public Integer pop() {
        if (this.isEmpty()) 
            return null;
        return super.pop();
    }
    
    @Override
    public boolean isEmpty() {
        return (this.size() <= 0);
    }
    
    public Integer top() {
        return this.getFirst();
    }
    
}

class MyQueue<T> extends LinkedList<T>{
    
    public void enqueue(T e) {
        this.addFirst(e);
    }
    
    public T dequeue() {
        if (super.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Queue is empty!");
        }
        
        return this.removeLast();
    }
    
}