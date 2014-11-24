/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.Iterator;
import java.util.LinkedList;

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
    }
    public void updateWalls(){
    }
    /*Always returns true*/
    protected Wall insert(Point p){
        /*Variable declaration*/
        Iterator iterator;
        boolean add;
        //Wall insertionLocation;
        
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
            /*Add if a point adjacent to p was found*/
            if(a.add(p)){
                System.err.println("iserted " + p.toString());
                return a;
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
    public static void main(String[] args){
        Walls wall = new Walls(1);
      
        //wall.insert(new Point(2.0,2.0));
        wall.insert(new Point(2.0,3.0));
        //wall.insert(new Point(3.0,3.0));
        wall.insert(new Point(3.0,7.0));
        wall.insert(new Point(2.0,8.0));
        wall.insert(new Point(4.0,5.0));
       // wall.insert(new Point(5.0,5.0));
        wall.insert(new Point(4.0,4.0));
        
        //wall.insert(new Point(2.0,6.0));
        wall.insert(new Point(6.0,4.0));
        wall.insert(new Point(5.0,9.0));
         wall.insert(new Point(3.0,1.0));

        //wall.insert(new Point(4.0,8.0));
         wall.insert(new Point(5.0,7.0));
          wall.insert(new Point(6.0,6.0));
           wall.insert(new Point(3.0,5.0));
        System.out.println(wall.toString());
        
    }
}
