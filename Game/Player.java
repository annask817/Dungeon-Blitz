import greenfoot.*;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Player extends SmoothMover
{
    private static int pSPEED = 5;
    private GreenfootImage idle[] = new GreenfootImage[4];
    private GreenfootImage run[] = new GreenfootImage[4];
    private int frame = 0, rate = 0;
    public int health = 10;
    public int damage=1;
    public boolean dead = false;
    private boolean isFacingRight = true;
    
    private boolean shooting = false;
    private int shootDelay = 0;
    
    
    // Set projectile damage
    public void setDamage(int arrowDamage){
        damage= arrowDamage;
    }
    
    // Get player health
    public int getHealth(){
        return health;
    }

    // Alter player health
    public void editHealth(int edit){
        health+= edit;
    }
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        setImage("knight_idle_0.png");
        for(int i =0; i<4; i++){       // Set player animation images
            idle[i] = new GreenfootImage("knight_idle_" + i + ".png");
            run[i] = new GreenfootImage("knight_run_" + i + ".png");
        }
    }

    public void act()
    {
       animate();
       checkHealth();
       if (dead) return;
       checkDamage();
       // WASD movement
       if(Greenfoot.isKeyDown("w")){
           setRotation(270);
           move(pSPEED);
       }
       if(Greenfoot.isKeyDown("a")){
           setRotation(180);
           move(pSPEED);
       }    
       if(Greenfoot.isKeyDown("s")){
           setRotation(90);
           move(pSPEED);
       }
       if(Greenfoot.isKeyDown("d")){
           setRotation(0);
           move(pSPEED);
       }
       setRotation(0); // prevent image from rotating
       
       if(Greenfoot.mouseClicked(null)){
           shoot();
       }
       /*
       if(shooting && shootDelay == 0){
               shoot();
               shootDelay = 30; // A smaller number here will increase firerate.
       }
       if (shooting && (Greenfoot.mouseDragEnded(null) || Greenfoot.mouseClicked(null))) shooting = false;
       if (!shooting && Greenfoot.mousePressed(null)) shooting = true;
       if (shootDelay > 0) shootDelay--;*/
    }
    
    /**
     * Animate - animate player character for running or idle
     */
    private void animate(){
        if (frame == 4){    // Prevent out of bounds, only 4 images
            frame = 0;
        }        
        int i = frame;
        if(rate != 10){     // Controls animation speed
            rate++;
            return;
        }
        
        // If running, set running animation
        if(Greenfoot.isKeyDown("w")||Greenfoot.isKeyDown("a")||
            Greenfoot.isKeyDown("s")||Greenfoot.isKeyDown("d")){
                if(Greenfoot.isKeyDown("a") && isFacingRight) {
                    for(GreenfootImage frame : run) {
                        frame.mirrorHorizontally();
                    }
                    for(GreenfootImage frame : idle) {
                        frame.mirrorHorizontally();
                    }
                    isFacingRight = false;
                }
                else if(Greenfoot.isKeyDown("d") && !isFacingRight) {
                    for(GreenfootImage frame : run) {
                        frame.mirrorHorizontally();
                    }
                    for(GreenfootImage frame : idle) {
                        frame.mirrorHorizontally();
                    }
                    isFacingRight = true;
                }
                setImage(run[i]);
            }
        else{ // else, set idle animation
            setImage(idle[i]);
        }
        frame++;
        rate=0;   
    }
    
    // Shoot - aim and shoot projectile with mouse
    private void shoot(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse == null) return;
        int x = mouse.getX();
        int y = mouse.getY();
        pArrow arrow = new pArrow();
        arrow.setDamage(damage);
        getWorld().addObject(arrow, getX(), getY());
        arrow.turnTowards(x, y);
        //Greenfoot.playSound("whoosh.mp3");
    }
    
    // Check if alive 
    private void checkHealth(){
        if(health<=0){
            getWorld().removeObject(this);
            dead = true;
        }
    }
    
    // Check damage received
    private void checkDamage(){
        Actor intersectingObject = getOneIntersectingObject(Arrow.class);
        if(intersectingObject!=null && intersectingObject instanceof Arrow){
            Arrow arrow = (Arrow) intersectingObject;
            boolean check = arrow.getEnemyOrPlayer();
            if(!check){ // If it's an arrow from an enemy, damage
                health -= arrow.getDamage();
                getWorld().removeObject(arrow);
            }
        }
    }   
}