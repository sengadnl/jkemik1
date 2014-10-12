/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author admin
 */
public class BoardStatus implements Comparator<PointScore>{
    ArrayList<PointScore> status;
    public BoardStatus(){
        this.status = new ArrayList<PointScore>();
    }
    public boolean add(PointScore p){
        if(this.status.add(p)){
            Collections.sort(status);
            return true;
        }
        return false;
    }
    public ArrayList<PointScore> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<PointScore> status) {
        this.status = status;
    }
    public String toString(){
        for(PointScore p : this.status){
            System.out.println("\n" + p.toString());
        }
        return "";
    }
    @Override
    public int compare(PointScore o1, PointScore o2) {
        return o1.compareTo(o2);
    }
//    public static void main(String[] args){
//        BoardStatus test = new BoardStatus();
//        test.add(new PointScore("223.0,3.0",0));
//        test.add(new PointScore("2.0,40.0",6));
//        test.add(new PointScore("2.0,3.0",2));
//        test.add(new PointScore("2.0,100.0",0));
//        test.add(new PointScore("2.0,3.0",3));
//        test.add(new PointScore("2.0,1.0",8));
//        System.out.println("\n" + test.toString());
//    }
}
