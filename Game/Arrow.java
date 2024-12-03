import greenfoot.*;
import java.lang.Object;

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arrow  extends SmoothMover
{
    private float aSpeed;
    private boolean enemyOrPlayer;  // true is player, false is enemy
    private int damage =1;
    
    public void setEnemyOrPlayer(boolean eOP){
        enemyOrPlayer = eOP;
    }
    
    public boolean getEnemyOrPlayer(){
        return enemyOrPlayer;
    }
    
    public void setDamage(int arrowDamage){
        damage = arrowDamage;
    }
    
    public void setSpeed(float speed){
        aSpeed = speed; 
    }
    
    public int getDamage(){
        return damage;
    }
    
    public void setPic(String type){
        setImage("weapon_arrow_" + type + ".png");
    }
    
    public Arrow()
    {
    
    }
    
    public void act(){
        move(aSpeed);
        // Remove arrow if it goes out of the world
        if (isAtEdge()) {        
            getWorld().removeObject(this);
        }
    }
    
}
