import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class MyWorld extends World
{
    private static final int worldWidth = 1500, worldHeight=900;
    private int centerW = worldWidth/2;
    private int centerH = worldHeight/2;   
    private GreenfootImage floor = new GreenfootImage("floor_0.png"); // allows for tiling

    private static final int viewW = 800;
    private static final int viewH = 600;
    private Scroller scroller= null;
    Actor scrollActor;
    
    private int currentLevel =1;
    private int levelStartDelay = 0; //
    private boolean startingNextLevel = false; 
    
    private Actor startPlayer(){
        return scrollActor = new Player();
    }
    
    private Scroller startScroll(){
        // Create actual world dimension
        return new Scroller(this, floor, worldWidth, worldHeight);
    }
    
    private void startLevel(int level){
        removeObjects(getObjects(Actor.class)); // Clear the world before starting a new level
        
        // Add Scroller and Player
        
        if(level ==1){
            scrollActor = startPlayer();
            scroller = startScroll();
        }
        addObject(scrollActor, viewW/2, viewH/2);
        
        // Mini-map elements and health bar
        addObject(new MiniMapBG(), 83, 50);
        addObject(new MiniMapPlayer(), viewW/10, viewH/10);
        addObject(new HealthBar(viewW/2+viewW/3,viewH/15), 0, 0);
        
        // Enemy spawning logic according to level
        spawnLevelEnemies(level);
    }
    
    // Spawn enemies
    private void spawnLevelEnemies(int level){
        for(int i=0; i<1; i++){
            addObject(new Masked(), Greenfoot.getRandomNumber(worldWidth), 
                Greenfoot.getRandomNumber(worldHeight));
        }
        if (level == 2 || level == 4 || level == 5) {
            // Spawn 1 demon for levels 2, 4, and 5
            addObject(new Demon(), Greenfoot.getRandomNumber(worldWidth), 
                Greenfoot.getRandomNumber(worldHeight));
        }
        if (level == 3 || level == 4) {
            // Spawn 1 ogre for levels 3 and 4
            addObject(new Ogre(), Greenfoot.getRandomNumber(worldWidth), 
                Greenfoot.getRandomNumber(worldHeight));
        }
        if (level == 5) {
            // Spawn an additional ogre for level 5
            addObject(new Ogre(), Greenfoot.getRandomNumber(worldWidth), Greenfoot.getRandomNumber(worldHeight));
        }
    }
    
    // Return whether there are any enemies remaining
    private boolean areEnemiesRemaining(){
        // Get all objects of type Enemy in the world
        java.util.List<EnemyBase> enemiesInWorld = getObjects(EnemyBase.class);
        // If the list is empty, return false (no enemies remaining)
        return !enemiesInWorld.isEmpty();
    }
    
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create scroll view dimensions
        super(viewW, viewH, 1, true);
        
        // Title Screen displayed
        Greenfoot.setWorld(new TitleScreen());
        startLevel(currentLevel);
        
    } 
  
    
    public void act(){
        java.util.List<Player> playersInWorld = getObjects(Player.class);
        if (playersInWorld.isEmpty()) {
            displayLosingMessage();
            Greenfoot.delay(200);
            Greenfoot.setWorld(new TitleScreen());// return to title screen
            return; // Exit the method to avoid further processing
        }
        if (scrollActor!=null) {
            scroll();
        }
        if (!areEnemiesRemaining() && !startingNextLevel)
        {
            if (currentLevel == 5)
            {
                displayVictoryMessage();
                Greenfoot.delay(200);
                Greenfoot.setWorld(new TitleScreen());// return to title screen
                return; // Exit the method to avoid further processing
            }
            displayLevelStartText(currentLevel + 1); // Display next level text
            startingNextLevel = true;
            levelStartDelay = 0; // Reset the delay counter
        }
        
        if (startingNextLevel)
        {
            levelStartDelay++;

            // Approximately 5 seconds delay
            if (levelStartDelay >= 300)
            {
                clearLevelStartText(); // Clear the level start text
                removeObjects(getObjects(Actor.class)); // Clears the world for the next level
                currentLevel++;
                startLevel(currentLevel);
                startingNextLevel = false; 
            }
        }   
    }
    
    
    // Display text
    private void displayLevelStartText(int level)
    {
        // Display "Level X now starting" at the center of the world
        showText("Level " + level + " now starting", getWidth() / 2, getHeight() / 2);
    }
    private void clearLevelStartText()
    {
        showText("", getWidth() / 2, getHeight() / 2); // Clear the level start text
    }
    private void displayVictoryMessage()
    {
        showText("You have won!", getWidth() / 2, getHeight() / 2);
    }  
    private void displayLosingMessage(){
        showText("You have lost!", getWidth() / 2, getHeight() / 2);
    }
    // Return real x location of player, not relative to view location
    public int scrollMapX(){
        int x = scrollActor.getX() + scroller.getScrolledX();        
        return x;
    }
    
    // Return real y location of player, not relative to view location
    public int scrollMapY(){   
        int y = scrollActor.getY()+ scroller.getScrolledY();
        return y;
    }
    
    // Allows for some player movement before scrolling begins
    private void scroll(){
        int xThresh = 200;  // Threshold for x
        int yThresh = 150;  // Threshold for y
        int loX=xThresh;
        int hiX=viewW-xThresh;
        int loY=yThresh;
        int hiY =viewH-yThresh;
        int dsx = 0, dsy=0;
        if(scrollActor.getX()<loX) dsx = scrollActor.getX()-loX;
        if(scrollActor.getX()>hiX) dsx = scrollActor.getX()-hiX;
        if(scrollActor.getY()<loY) dsy = scrollActor.getY()-loY;
        if(scrollActor.getY()>hiY) dsy = scrollActor.getY()-hiY;
        scroller.scroll(dsx, dsy);
    }
    
}