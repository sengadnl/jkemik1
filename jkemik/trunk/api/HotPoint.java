/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class HotPoint implements Comparable<HotPoint>,Serializable{
    private String key;
    private int score;
    public HotPoint(String key, int score){
        this.key = key ;
        this.score = score;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString(){
        return key + " > " + score;
    }

    @Override
    public int compareTo(HotPoint o) {
        if(this.score > o.getScore()){
            return 1;
        }else if(this.score < o.getScore()){
            return -1;
        }else{
            return 0;
        }
    }

}
