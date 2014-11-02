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
        this.status = new ArrayList<>();
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
        AIGame game = (AIGame) JKemik.getGame();
        //try{
            Cell c;
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
               updatePointStatusBasedAxises(temp, game);
               updatePointStatusBasedDiagonals(temp,game);
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
    private void updatePointStatusBasedDiagonals(Point p, AIGame game){
        int max;
        max = 0;
        int id = p.getId();
        Point[] diagonals;
        
        diagonals = p.axisBox(Grid.squareSize);
        for(int i = 0; i < diagonals.length - 1; i++){ 
            Point diagonalp = game.getCollection().get(diagonals[i].toString());
            
            if(diagonalp == null){
                    continue;
             }
            
            //Opponent uncaptured point
            if(id != diagonalp.getId() && diagonalp.getStatus() == Point.PLAYED){
                max++;
                continue;
            }

            //Opponent connected point
            if(id != diagonalp.getId() && diagonalp.getStatus() == Point.CONNECTED){
                max++;
                max++;
                continue;
            }
            
            //my points
            if(id == diagonalp.getId()){
                max--;
            }
        }
        //p.setHeatLevel(p.getHeatLevel() + max);
        p.setHeatLevel(max);
    }
    private void updatePointStatusBasedAxises(Point p, AIGame game){
        //System.out.println("\nStarting Heat for: " + p + " " + p.getHeatLevel());
        int max;
        max = 0;
        int id = p.getId();
        Point[] axises;
        
        axises = p.axisBox(Grid.squareSize);
        for(int i = 0; i < axises.length - 1; i++){ 
            Point axep = game.getCollection().get(axises[i].toString());
            
            if(axep == null){
                    continue;
             }
            
            //Opponent uncaptured point
            if(id != axep.getId() && axep.getStatus() == Point.PLAYED){
                max++;
                max++;
                continue;
            }

            //Opponent connected point
            if(id != axep.getId() && axep.getStatus() == Point.CONNECTED){
                max++;
                max++;
                max++;
                continue;
            }
            
            //my points
            if(id == axep.getId()){
                max--;
                max--;
                continue;
            }
        }
        p.setHeatLevel(p.getHeatLevel() + max);
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
