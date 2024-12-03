import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class healthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{

    int currentHealth;
    int maxHealth = 5;
    HeartsUI[] healthBar = new HeartsUI[maxHealth];
    int viewW;
    int viewH;
    boolean initial = false;
    /**
     * Act - do whatever the healthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        if(!initial) initialize();
        upDate();
    }

    public HealthBar(int viewW,int viewH){
        this.viewW = viewW;
        this.viewH = viewH;
    }
     public void initialize(){
        setImage((GreenfootImage)null);
        currentHealth = 5;
        for(int i = 0;i<maxHealth;i++){
            healthBar[i] = new HeartsUI();
            healthBar[i].updateHeart(1);
            getWorld().addObject(healthBar[i],viewW+i*25, viewH);
        }

    }
    private void upDate(){
        java.util.List<Player> playersInWorld = getWorld().getObjects(Player.class);
        if(playersInWorld.isEmpty()) return;
        Player player = playersInWorld.get(0);
        int currentHealth = player.getHealth();
        int trueHeart = currentHealth/2;
        int halfHeart = currentHealth%2;
        if(currentHealth >= 0){
            for(int i = trueHeart;i<maxHealth;i++){
                healthBar[i].updateHeart(3);
            }

        }

        if(halfHeart == 1){
            healthBar[trueHeart].updateHeart(2);
        }
    }
}