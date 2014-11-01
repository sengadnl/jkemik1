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
import view.Grid;
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
    //TODO make sure board is being updated
    public void updateStatus(Point obj){
        AIGame game = (AIGame) JKemik.game;
        
        //try{
            Cell c = null;
            if(null != game.getLastCell()){
                c = game.getLastCell();
                ArrayList<Point> cellWall = c.getCellContour();
                ArrayList<Point> captures = c.getAreaIncell();

                //remove dead points from boardStatus
                for(Point p: captures){
                    if(p.getStatus() == Point.DEAD || p.getStatus() == Point.CAPTURED){
                        for(HotPoint s: this.status){
                            if(s.getKey().equals(p.toString())){
                                System.out.println("Removing " + s.toString());
                                this.status.remove(s);
                                break;
                            }
                        }
                    }
                }
            } 
  
            //update status
            for(Point temp: game.getCollection().values()){
               updatePointStatus(temp, game);
            }
            
            //Make to score of connected points 0
            if(!this.status.isEmpty()){
                for(HotPoint s: this.status){
                    Point z = game.getCollection().get(s.getKey());
                    if(z != null){ 
                        if(z.getStatus() == Point.CONNECTED){
                            s.setScore(0);
                        }
                        s.setScore(z.heatLevel);
                    } 
                }
            }
            Collections.sort(status);
//        }catch(NullPointerException ex){
//            System.out.println("Exception in BoardStatus:updateStatus " + ex.getMessage());
//        }
        
    }
    private void updatePointStatus(Point p, AIGame game){
        //System.out.println("\nStarting Heat for: " + p + " " + p.getHeatLevel());
        int max;
        max = 0;
        int id = p.getId();
        Point[] box;
        box = p.axisBox(Grid.squareSize);
        for(int i = 0; i < box.length - 1; i++){ 
            Point temp = game.getCollection().get(box[i].toString());
            
            if(temp == null){
                    continue;
             }
            
            //Opponent uncaptured point
            if(id != temp.getId() && temp.getStatus() == Point.PLAYED){
                max++;
                continue;
            }

            //Opponent connected point
            if(id != temp.getId() && temp.getStatus() == Point.CONNECTED){
                max++;
                max++;
                continue;
            }
            
            //my points
            if(id == temp.getId()){
                max--;
                continue;
            }
        }
        p.setHeatLevel(max);
        //System.out.println("Ending Heat for: " + p + " " + p.getHeatLevel());
    }
    public ArrayList<HotPoint> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<HotPoint> status) {
        this.status = status;
    }
    @Override
    public String toString(){
        for(HotPoint p : this.status){
            System.out.println(p.toString());
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
