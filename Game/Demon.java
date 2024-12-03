import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Demonn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Demon extends EnemyBase
{
    public Demon() {
        setName("demon");
        setHealth(5);
        setDamage(2);
        setSpeed(1.0);
        setCoolTimer(60);
        setImages();
    }
}
