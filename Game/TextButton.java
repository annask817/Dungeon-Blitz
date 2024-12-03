import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TextButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextButton extends Actor {
    private String text;

    public TextButton(String text) {
        this.text = text;
        updateImage();
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            handleButtonClick();
        }
    }

    private void handleButtonClick() {
        if (text.equals("New Game")) {
            Greenfoot.setWorld(new MyWorld()); 
        } else if (text.equals("Controls")) {
            // Show controls explanation in implementation 2
        }
        else if(text.equals("Instructions")){
            Greenfoot.setWorld(new InstructionScreen());
        }
        else if(text.equals("Play Game")){// handle "Play Game" button click
            Greenfoot.setWorld(new MyWorld());// start the game directly
        }
        if(text.equals("Back")){
            Greenfoot.setWorld(new TitleScreen());
        }
    }

    private void updateImage() {
        GreenfootImage image = new GreenfootImage(text, 48, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(image);
    }
}