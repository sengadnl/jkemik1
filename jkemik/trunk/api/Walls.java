/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.LinkedList;

/**
 *
 * @author admin
 */
public class Walls extends LinkedList<Wall>{
    public Walls(){
    }
    public void updateWalls(){
    }
    public Wall checkCaptures(){
        return this.get(0);
    }
}
