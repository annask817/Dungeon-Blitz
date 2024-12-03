import greenfoot.*;
/**
 * 
 */

public class InstructionScreen extends World {

    private static final String INSTRUCTIONS_TEXT = "Welcome to Dungeon Blitz!\n\n"
            + "Instructions:\n"
            + "Use the WASD keys to move your character.\n"
            + "Use the left mouse button to aim and shoot.\n"
            + "Try to avoid enemies arrows to keep your health.\n"
            + "Defeat all enemies by shooting arrows to win.\n"
            + "Good luck and have fun!";

    public InstructionScreen() {
        super(800, 600, 1);
        displayInstructions();
        prepare();
    }

    private void displayInstructions() {
        GreenfootImage backgroundImage = new GreenfootImage("title_screen.png");
        setBackground(backgroundImage);

        GreenfootImage text = new GreenfootImage(INSTRUCTIONS_TEXT, 24, Color.WHITE, Color.BLACK);
        int x = (getWidth() - text.getWidth()) / 4 - 50;
        int y = 325;
        getBackground().drawImage(text, x, y);
    }

    private void prepare() {
        addObject(new TextButton("Back"), getWidth() / 2, getHeight() - 50); // return to title
    }


}