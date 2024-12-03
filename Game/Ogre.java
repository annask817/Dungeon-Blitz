import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ogre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ogre extends EnemyBase
{
    public Ogre(){
        setName("ogre");
        setHealth(5);
        setDamage(3);
        setSpeed(1);
        setCoolTimer(60);
        setImages();
        
    }
}
