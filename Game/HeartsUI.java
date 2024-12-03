import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HeartsUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeartsUI extends Actor
{
    private GreenfootImage full = new GreenfootImage("ui_heart_full.png");
    private GreenfootImage half = new GreenfootImage("ui_heart_half.png");
    private GreenfootImage empty = new GreenfootImage("ui_heart_empty.png");
    public int healthPoints;
    boolean initial = false;
    
    /**
     * Act - do whatever the HeartsUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void initialize(){
        setImage(full);
    }
    
    
    public void act()
    {
        /*
        if(!initial) initialize();
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if(playersInWorld.isEmpty()) return;
        healthPoints = currentHealth();
        */
    }

    
    private int currentHealth(){
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        Player player = playersInWorld.get(0);
        int hp = player.getHealth();
        return hp;
    }
    
    void updateHeart(int i){
        if(i == 1){
            setImage(full);
        }else if(i == 2){
            setImage(half);
        }else if(i == 3){
            setImage(empty);
        }else{
            return;
        }
        
    }
    
}