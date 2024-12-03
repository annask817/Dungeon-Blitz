import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class TitleScreen extends World {
    public TitleScreen() {
        super(800, 600, 1);
        prepare();
    }

    private void prepare() {
        // Set the background image
        GreenfootImage backgroundImage = new GreenfootImage("title_screen.png");

        setBackground(backgroundImage);

        // Add text buttons for "New Game" and "Controls" - WILL IMPLEMENT CONTROLS BY IMPLEMENTATION 2
        addObject(new TextButton("New Game"), getWidth() / 4+20, getHeight() - 250);
        addObject(new TextButton("Instructions"), getWidth() / 4+20, getHeight() - 175);
    }
}