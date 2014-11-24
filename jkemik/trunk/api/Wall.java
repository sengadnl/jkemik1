/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author admin
 */
public class Wall{
    int sqrSize;
    private LinkedList<Point> list;
    public Wall(int sqrSize){
        super();
        this.sqrSize = sqrSize;
        this.list = new LinkedList<>();
        stitchingLock = new ReentrantLock();
    }
    /*
    *Adds point e at the beginning or a the end of the list
    */
    public boolean add(Point e){
        Point last,first;
//         boolean add;
//         add = false;
        if(this.list.isEmpty()){
           this.list.add(e);
            return true;
        }
        
        /*Return false if this point already exists*/
        for(Point p: this.list){
            if(p.compareTo(e) == 0){
                System.out.println("Point " + e.toString() + " already exists...");
                return false;
            }
        }
        
        //try adding at the end
        last = this.list.getLast();
        if(e.adjacentTo(last,this.sqrSize)){
            this.list.addLast(e);
            return true;
        }
        
        //try adding at the beginning
        first = this.list.getFirst();
        if(e.adjacentTo(first,this.sqrSize)){
            this.list.addFirst(e);
            System.out.println("Adding " + e.toString() + " at the biginning...");
            return true;
        }
        System.out.println("Point " + e.toString() + " was not adjacent...");
        return false;
    }
    
    public int getSqrSize() {
        return sqrSize;
    }

    public void setSqrSize(int sqrSize) {
        this.sqrSize = sqrSize;
    }

    public LinkedList<Point> getList() {
        return list;
    }

    public void setList(LinkedList<Point> list) {
        this.list = list;
    }
    public boolean stitchTo(Wall wall){
        stitchingLock.lock();
        Iterator i,it;
        Point wallFirst, wallLast,objFirst, objLast;
        try{
            
            /*Stitch at the end of the wall*/
            wallFirst = wall.getList().getFirst();
            objLast = this.list.getLast();
            
            i = wall.getList().iterator();
            
            if(objLast.adjacentTo(wallFirst, sqrSize)){
                while(i.hasNext()){
                    Point t = (Point) i.next();
                    this.add(t);
                }
                System.out.println(wall.toString() + " was added  at the end");
                return true;
            }

            /*Stitch at the biggining of the wall*/
            wallLast = wall.getList().getLast();
            objFirst = this.list.getFirst();
            
            it = this.list.iterator();
            
            if(wallLast.adjacentTo(objFirst, sqrSize)){
                while(it.hasNext()){
                    Point p = (Point) it.next();
                    wall.add(p);
                }
                this.list = wall.getList();
                System.out.println(this.toString() + " was added  at begginning");
                return true;
            }
            
        }catch(NullPointerException ex){
            System.out.println("StitchTo NullPointerException: " + ex.getMessage());
        }catch(NoSuchElementException ex){
            System.out.println("StitchTo NoSuchElementException " + ex.getMessage());
        }catch(ConcurrentModificationException ex){
            System.out.println("StitchTo ConcurrentModificationException " + ex.getMessage());
        }
        finally{
            stitchingLock.unlock();
        }
        System.out.println("Lists can not be stitched, they are not adjacent..");
        return false;
    }
    /*Trancate wall at x and return the tracated part*/
    public Wall trancateAt(Point x){
        Wall wall;
        Iterator i;
        i = this.list.iterator();
        
        wall = new Wall(this.sqrSize);
        try{
            /*Move iterator after x*/
            while(i.hasNext()){
                Point temp = (Point) i.next();
                if(temp.compareTo(x) == 0){
                    //System.out.println("Cursor is at " + i.next());
                    break;
                }
            }
             
            /*Add trancated elements to another list*/
            while(i.hasNext()){
                wall.add((Point) i.next());
            }
            /*Trancate the list*/
            this.list.removeAll(wall.getList());
        }catch(NoSuchElementException ex){
            System.out.println("Trancate " + ex.getMessage());
        }
         /*Return the trancated list*/
         return wall;
    }
    public String toString(){
        
        String hold;
        Iterator i;
        hold = "";
        i = this.list.iterator();
        while(i.hasNext()){
            hold += " " + i.next();
        }
        return "" + hold;
    }
    public static void main(String[] args){
         //LinkedList<Point> wall = new LinkedList<>();
        Wall wall = new Wall(1);
        Wall wall2 = new Wall(1);
        
        wall2.add(new Point(0.0,0.0));
        wall2.add(new Point(1.0,1.0));
        wall2.add(new Point(2.0,1.0));
        
        wall.add(new Point(2.0,2.0));
        wall.add(new Point(3.0,2.0));
        wall.add(new Point(3.0,0.0));
        //wall.add(new Point(1.0,1.0));
       
        System.out.println("Whole list: " + wall.toString());
        System.out.println("Whole list2: " + wall2.toString());
        System.out.println("\nStitch list to list2: " + wall2.stitchTo(wall));
        System.out.println("\nNew list2: " + wall2.toString());
        System.out.println("\nStitch new list2 to list: " + wall.stitchTo(wall2));
        System.out.println("\nResult: " + wall.toString());
    }
    private Lock stitchingLock;
}
