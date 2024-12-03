import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MiniMapBG here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniMapBG extends Actor
{
    private GreenfootImage miniBG = new GreenfootImage("mini-map-bg.png");
    /**
     * Act - do whatever the MiniMapBG wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setImage(miniBG);
        // Add your action code here.
    }
}
