import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Minimap_Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniMapPlayer extends SmoothMover 
{
    public MiniMapPlayer(){
        setImage("Player_dot.png");
    }
    /**
     * Act - do whatever the Minimap_Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {   
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (playersInWorld.isEmpty()){  // If player dead, remove
            getWorld().removeObject(this);
        }
        else{
            locate();
        }   
    }

    // Gets players location and sets the icon to be at the 1/8th 
    private void locate(){
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (!playersInWorld.isEmpty()) {
            Player player = playersInWorld.get(0);
            int scaleF = 10; // scale to xth value
            
            double miniX = ((MyWorld)getWorld()).scrollMapX() / scaleF;
            double miniY = ((MyWorld)getWorld()).scrollMapY() / scaleF;
            setLocation(miniX,miniY);
        }
        
    }
}