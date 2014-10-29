/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import controler.JKemik;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author admin
 */
public class BoardStatus implements Comparator<HotPoint>{
    ArrayList<HotPoint> status;
    public BoardStatus(){
        this.status = new ArrayList<HotPoint>();
    }
    public boolean add(HotPoint p){
        if(this.status.add(p)){
            Collections.sort(status);
            return true;
        }
        return false;
    }
    public void updateStatus(){
        //System.out.println("Before updating status " + this.status.toString());
        AIGame game = (AIGame) JKemik.game;
        
        try{
        Cell c = game.getLastCell();
        ArrayList<Point> cellWall = c.getCellContour();
        ArrayList<Point> captures = c.getAreaIncell();
       
        //remove dead points from boardStatus
        for(Point p: captures){
            // System.out.println("Status of p = " + p.getStatus());
            if(p.getStatus() == Point.DEAD || p.getStatus() == Point.CAPTURED){
                for(HotPoint s: this.status){
                    if(s.getKey().equals(p.toString())){
                        System.out.println("Removing" + s.toString());
                        this.status.remove(s);
                        break;
                    }
                }
            }
        }
        
        //Increase score for all connected dots
        for(Point pt: cellWall){
            if(pt.getStatus() == Point.CONNECTED){
                for(HotPoint s: this.status){
                    if(s.getKey().equals(pt.toString())){
                        s.setScore(s.getScore() + 1);
                        break;
                    }
                }
            }
        }
        }catch(NullPointerException ex){
            System.out.println("Exception in BoardStatus:updateStatus" + ex.getMessage());
        }
        System.out.println("After updating status  " + this.status.toString());
    }
    public ArrayList<HotPoint> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<HotPoint> status) {
        this.status = status;
    }
    public String toString(){
        for(HotPoint p : this.status){
            System.out.println("\n" + p.toString());
        }
        return "";
    }
    @Override
    public int compare(HotPoint o1, HotPoint o2) {
        return o1.compareTo(o2);
    }
//    public static void main(String[] args){
//        BoardStatus test = new BoardStatus();
//        test.add(new HotPoint("223.0,3.0",0));
//        test.add(new HotPoint("2.0,40.0",6));
//        test.add(new HotPoint("2.0,3.0",2));
//        test.add(new HotPoint("2.0,100.0",0));
//        test.add(new HotPoint("2.0,3.0",3));
//        test.add(new HotPoint("2.0,1.0",8));
//        System.out.println("\n" + test.toString());
//    }
}
