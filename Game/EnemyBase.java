import greenfoot.*;

/**
 * EnemyBase class will allow the basic actions
 * of attacking the player and will be animated.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyBase extends SmoothMover
{
    private String name;
    public double eSPEED;
    private GreenfootImage idle[] = new GreenfootImage[4];
    private GreenfootImage run[] = new GreenfootImage[4];
    private int frame = 0, rate = 0, coolDown=20;
    public int health, coolTimer;
    public int damage=1;
    public boolean dead = false;
    
    // Set enemy name/type
    public void setName(String enemyName){
        name = enemyName;   
    }
    
    // Get enemy health
    public int getHealth(){
        return health;
    }
    
    // Alter enemy health
    public void editHealth(int edit){
        health += edit;
    }
    
    // Set enemy health
    public void setHealth(int eHealth){
        health = eHealth;
    }
    
    // Set arrow damage
    public void setDamage(int arrowDamage){
        damage = arrowDamage;
    }
    
    // Set enemy speed
    public void setSpeed(double speed){
        eSPEED = speed;
    }
    
    // Set enemy arrow cooldown time
    public void setCoolTimer(int cTimer){
        coolTimer = cTimer;
    }
    
    // Set animation images for enemy
    public void setImages(){
        setImage(name + "_idle_0.png");
        for(int i =0; i<4; i++){
            idle[i] = new GreenfootImage(name + "_idle_" + i + ".png");
            run[i] = new GreenfootImage(name + "_run_" + i + ".png");
        }
    }
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        animate();
        checkHealth();
        if(dead) return;
        checkDamage();
        followPlayer();
        if(coolDown!=coolTimer){
            coolDown++;
        }
        else{
            shoot();
            coolDown=0;
        }
        
        // If player dead, stop moving
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (!playersInWorld.isEmpty()) {
            Player player = playersInWorld.get(0);
            if(player.getHealth()==0){
                eSPEED=0;
            }
        }

        move(eSPEED);
        checkCollision();
        setRotation(0); // Prevent image from rotating        
    }
    
    // Follow player movement
    private void followPlayer()
    {
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (!playersInWorld.isEmpty()) {
            Player player = playersInWorld.get(0);
            turnTowards(player.getX(), player.getY());
        }
        
    }

    // Aim and shoot at player
    private void shoot(){
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (!playersInWorld.isEmpty()) {
            Player player = playersInWorld.get(0);
            int x = getX();
            int y = getY();
            eArrow arrow = new eArrow();
            arrow.setDamage(damage);
            getWorld().addObject(arrow, getX(), getY());
            arrow.turnTowards(player.getX(), player.getY());
        }
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
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if (playersInWorld.isEmpty()) { // if player dead, set idle animation
            setImage(idle[i]);
        }
        else{
            setImage(run[i]);
        }
        frame++;            // Get next frame
        rate=0;
    }
    
    // Prevent enemies and player from "stacking"
    private void checkCollision(){
        EnemyBase enemy = (EnemyBase)getOneIntersectingObject(EnemyBase.class);
        if (enemy != null && enemy != this) {
            move(-10); // Move back
            //turn(180); // Turn around
        }
    }

    // Check if alive
    private void checkHealth(){
        if(health<=0 && !dead){
            getWorld().removeObject(this);
            dead = true;
            return;
        }
    }
    
    // Check damage received
    private void checkDamage(){
        Actor intersectingObject = getOneIntersectingObject(Arrow.class);
        if(intersectingObject!=null && intersectingObject instanceof Arrow){
            Arrow arrow = (Arrow) intersectingObject;
            boolean check = arrow.getEnemyOrPlayer();
            if(check){ // If it's an arrow from an enemy, damage
                editHealth(-arrow.getDamage());
                getWorld().removeObject(arrow);
            }
        }
    }   
}
