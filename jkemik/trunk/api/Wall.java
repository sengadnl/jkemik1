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
import utilities.Globals;


/**
 *
 * @author admin
 */
public class Wall implements Comparable<Wall>{
    int sqrSize;
    private LinkedList<Point> list;
    private Point cut;
    public Wall(int sqrSize){
        super();
        this.sqrSize = sqrSize;
        this.list = new LinkedList<>();
        this.cut = null;
        //stitchingLock = new ReentrantLock();
    }
    /*
    *Adds point e at the beginning or a the end of the list
    *@return
     1 e was added
     -1 e not added because it already exists.
     0 e needs a new wall
    */
    public int add(Point e){
        Point last,first;
        int count, len;
        //stitchingLock.lock();
        //try{
            if(this.list.isEmpty()){
               this.list.add(e);
                return Globals.ADD;
            }

            /*Return false if this point already exists*/
            len = this.list.size();
            count = 1;
            for(Point p: this.list){
                if(p.adjacentTo(e,sqrSize) && count < len){
//                    this.cut = e;
//                    return Globals.CUT;
                    System.out.println("Found an adjacent point...");
                }
                if(p.compareTo(e) == 0){
                    System.out.println("Point " + e.toString() + " already exists...");
                    return Globals.EXITS;
                }
                count++;
            }

            //try adding at the end
            last = this.list.getLast();
            if(e.adjacentTo(last,this.sqrSize)){
                this.list.addLast(e);
                return Globals.ADD;
            }

            //try adding at the beginning
            first = this.list.getFirst();
            if(e.adjacentTo(first,this.sqrSize)){
                this.list.addFirst(e);
                System.out.println("Adding " + e.toString() + " at the biginning...");
                return Globals.ADD;
            }
            System.out.println("Point " + e.toString() + " was not adjacent...");
            return Globals.ADDTONEWWALL;
//        }finally{
//            //stitchingLock.unlock();
//        }
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

    public Point getCut() {
        return cut;
    }

    public void setCut(Point cut) {
        this.cut = cut;
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
       
        System.out.println("l1 : " + wall.toString());
        System.out.println("l2 : " + wall2.toString());
        
        System.out.println("Stitch l1 to l2 : " + wall2.stitchTo(wall));
        System.out.println("l2 : " + wall2.toString());
        System.out.println("Stitch new l2 to l1: " + wall.stitchTo(wall2));
        System.out.println("\nResult: " + wall.toString());
    }
    private Lock stitchingLock;

    @Override
    public int compareTo(Wall o) {
        Point objF,objL,otherF,otherL;
        int objSize, otherSize;
        
        objF = this.getList().getFirst();
        objL = this.getList().getLast();
        objSize = this.getList().size();
        
        otherF = o.getList().getFirst();
        otherL = o.getList().getLast();
        otherSize = o.getList().size();
        
        /*Equality*/
        if(objF.adjacentTo(otherF,sqrSize) && 
                objL.adjacentTo(otherL,sqrSize)){
            return 0;
        
        /*If they are stichable indicate that other must be place before obj*/
        }else if(objL.adjacentTo(otherF,sqrSize) && objF.adjacentTo(otherL,sqrSize)){
            return -1;
        }else{
            return 1;
        }
    }
    //private Lock wallInsertionLock;
}
