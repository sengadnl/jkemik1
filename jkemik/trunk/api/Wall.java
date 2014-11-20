/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


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
    }
    public boolean add(Point e){
        Point last;
         boolean add;
         add = false;
        if(this.list.isEmpty()){
            add = this.list.add(e);
            return add;
        }
        last = this.list.getLast();
        if(e.adjacentTo(last,this.sqrSize)){
            this.list.addLast(e);
            add = true;
            return add;
        }
        return add;
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
    public Wall stitchTo(Wall wall){
        Iterator i;
        Point wallFirst, objLast;
        try{
            wallFirst = wall.getList().getFirst();
            objLast = this.list.getLast();

            if(!objLast.adjacentTo(wallFirst, sqrSize)){
                return null;
            }

            i = wall.getList().iterator();
        
            while(i.hasNext()){
                Point t = (Point) i.next();
                this.add(t);
            }
        }catch(NullPointerException ex){
            System.out.println("StitchTo NullPointerException: " + ex.getMessage());
        }catch(NoSuchElementException ex){
            System.out.println("StitchTo NoSuchElementException " + ex.getMessage());
        }
        return this;
    }
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
            hold += "\n" + i.next();
        }
        return "" + hold;
    }
//    public static void main(String[] args){
//         //LinkedList<Point> wall = new LinkedList<>();
//        Wall wall = new Wall(1);
//        wall.add(new Point(0.0,0.0));
//        wall.add(new Point(1.0,1.0));
//        wall.add(new Point(2.0,1.0));
//        wall.add(new Point(2.0,2.0));
//        wall.add(new Point(3.0,2.0));
//        wall.add(new Point(3.0,0.0));
//       
//        System.out.println("Whole list: " + wall.toString());
//        Wall tranc = wall.trancateAt(new Point(1.0,1.0));
//        System.out.println("\nNew list: " + wall.toString());
//         System.out.println("\nTrancated list: " + tranc.toString());
//         System.out.println("\nStitched list: " + wall.stitchTo(tranc));
//    }
}
