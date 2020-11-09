/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasurehunt;

/**
 *
 * @author rael
 */          
public class Player { 
    private String name;
    private int age;
    private int digPoints;
    private int piratePoints;
    
    public Player(){
        
    }
    
    public String getName(){
        return this.name;                      // in this class the player is being defined.
                                               //all data is being assigned to the player. name, age, digPoints and piratePoints are being assigned as a part of the player. 
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAge(){
        return this.age;
        
    }
    public void setAge(int age){
        this.age = age; 
    }
    public int getDigPoints(){
        return this.digPoints;
    }
    public void setDigPoints(int digPoints){
        this.digPoints = digPoints;
    }
    public int getPiratPoints(){
        return this.piratePoints;
    }
    public void setPiratePoints(int piratePoints){
        this.piratePoints = piratePoints;
    }
    
    
    
    
}
