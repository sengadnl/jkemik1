/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import utilities.Globals;
import utilities.Tools;

/**
 *
 * @author admin
 */
public class Walls{
    private LinkedList<Wall> walls;
    private int sqrSize;
   
    public Walls(int sqrSize){
        this.sqrSize = sqrSize;
        this.walls = new LinkedList<>();
        //boadsInsertionLock = new ReentrantLock();
    }
    public void updateWalls(){
    }
    /*Always returns true*/
    protected Wall insert(Point p){
        /*Variable declaration*/
        Iterator iterator;
        boolean add;
        //Wall insertionLocation;
//        boadsInsertionLock.lock();
//        try{
        /*Create a wall if there isn't any wall and the first point*/
        if(this.walls.isEmpty()){
            System.out.println("Creating the first wall...");
            Wall w = new Wall(this.sqrSize);
            w.add(p);
            this.walls.add(w);
            return w;
        }
        
        /*Get an iterator*/
        iterator = this.walls.iterator();
        //insertionLocation = this.walls.get(0);

        add = false;
        //insertionLocation = null;
        
        /*Try to insert p into current walls*/
        while(iterator.hasNext()){
            Wall a = (Wall) iterator.next();
            /*Return null if p already exists*/
            int ret = a.add(p);
            
            /*split and insert if a point cafisoca */
            if(ret == Globals.CUT){
                //Point temp = a.getCut();
                //Wall half = a.trancateAt(temp);
                //Wall half = Tools.trancate(a,temp);
                //a.add(p);
                //this.walls.add(half);
                System.out.println("slip next time...");
            }
            
            /*Add if a is adjacent to p was found*/
            if(ret == Globals.ADD){
                System.err.println("iserted " + p.toString());
                return a;
            }
            
            /*Don't do anything if it exists*/
            if(ret == Globals.EXITS){
                return null;
            }
        }
        
        /*If an adjacent point was not found create a new wall*/
        if(!add){
            Wall y = new Wall(this.sqrSize);
            y.add(p);
            this.walls.add(y);
            return y;
        }

        return null;
//        }finally{
//            boadsInsertionLock.unlock();
//        }
    }
    /*Scan for any stitchable walls*/
    public Wall stitchScan(Wall insersionp){
        Wall t;
        Iterator i = this.walls.iterator();
        
        //t = null;
        while(i.hasNext()){
            t = (Wall) i.next();
            if(insersionp.stitchTo(t)){
                System.err.println("stitching..." + t.toString());
                return t;
            }
        }
        return null;
    }

    public LinkedList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(LinkedList<Wall> walls) {
        this.walls = walls;
    }
    
    protected Wall checkCaptures(){
        return null;
    }
    public String toString(){
        String hold;
        Iterator i;
        hold = "";
        i = this.walls.listIterator();
        int index = 0;
        while(i.hasNext()){
            hold += index + " -" + i.next().toString() + "\n";
            index++;
        }
        return "" + hold;
    }
    //private Lock boadsInsertionLock;
    public static void main(String[] args){
        Walls wall = new Walls(1);
      
        wall.insert(new Point(6.0,9.0));
        wall.insert(new Point(4.0,1.0));
        wall.insert(new Point(2.0,5.0));
        wall.insert(new Point(3.0,2.0));
        wall.insert(new Point(5.0,2.0));
        wall.insert(new Point(5.0,10.0));
        wall.insert(new Point(3.0,4.0));
        wall.insert(new Point(4.0,3.0));
//        wall.insert(new Point(1.0,1.0));
//        wall.insert(new Point(6.0,1.0));
//        wall.insert(new Point(5.0,9.0));
//        wall.insert(new Point(3.0,1.0));
//        wall.insert(new Point(4.0,8.0));
//        wall.insert(new Point(5.0,7.0));
//        wall.insert(new Point(6.0,6.0));
//        wall.insert(new Point(3.0,5.0));
        System.out.println(wall.toString());
    }
}
