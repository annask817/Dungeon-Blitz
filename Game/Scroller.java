import greenfoot.*;
/**
 * CLASS: Scroller extend Object
 * AUTHOR: danpost (greenfoot.org username)
 * DATE: November 11, 2016
 * 
 * DESCRIPTION:  This is a support class for a scrolling world.  It contains two constructors;
 * one for unlimited scrolling and one for limited scrolling.  Both constructors have an 'image'
 * parameter.  Because image manipulation can hog up CPU time, it is important to remember that
 * it is better not to have a scrolling background image (having an Actor for the background is
 * probably worse than having the background scroll).  For unlimited scrolling using a background
 * image, the smaller that background image to be tiled, the better.  Making the viewport (the
 * size of the world that is visible) smaller can help in CPU expense, also.  Scrolling worlds
 * should be unbounded, allowing actors to move beyond the visible area.  Ensuring that actors
 * are removed from the world if no longer needed when out of view will help to prevent lag,
 * as well.  
 * 
 * It is the responsibility of the World object that creates a Scroller object to determine when
 * to scroll and by how much.
 */
public class Scroller
{
    private World world; // view window world
    private GreenfootImage scrollImage; // scrolling image
    private boolean limited;
    private int scrolledX, scrolledY;
         
    /**
     * This constructor is for an unlimited scrolling world;
     * If 'image' is null, the background will not change; else the given image is wrapped
     * 
     * @param viewWorld the world that scrolling will be performed on
     * @param image the background image that will be tiled, if needed, and wrap with scrolling
     */
    public Scroller(World viewWorld, GreenfootImage image)
    {
        world = viewWorld;
        scrollImage = image;
        scroll(0, 0); // sets initial background image
    }
     
    /**
     * This constructor is for a limited scrolling world;
     * If 'image' is smaller than the given total scrolling area, it will be tiled
     * If 'image' is null, the background will not change
     * 
     * @param viewWorld the world that scrolling will be performed on
     * @param image the background image that will be tiled, if needed, to fill the scrolling area
     * @param wide the width of the visible area encompassed through scrolling;
     * the given value must be at least equal to the width of 'viewWorld' and
     * is given in world cells (not in pixels)
     * @param high the height of the visible area encompassed through scrolling;
     * the given value must be at least equal to the height of 'viewWorld' and
     * is given in world cells (not in pixels)
     */
    public Scroller(World viewWorld, GreenfootImage image, int wide, int high)
    {
        limited = true;
        world = viewWorld;
        if (image != null)
        {
            // create an image as large as scrolling area; tiled, if needeed
            scrollImage = new GreenfootImage(wide*world.getCellSize(), high*world.getCellSize());
            for (int x=0; x<wide*world.getCellSize(); x+= image.getWidth())
                for (int y=0; y<high*world.getCellSize(); y+=image.getHeight())
                    scrollImage.drawImage(image, x, y);
            // set initial background image
            scroll(0, 0);
        }
    }
     
    /**
     * performs scrolling on 'world' by the given distances along the horizontal and vertical;
     * if 'limited' is false, requested distances are actual scrolling distances;
     * if 'limited' is true, the distances may be adjusted due to the limits of scrolling
     *
     * @param dsx the requested distance to shift everything horizontally
     * @param dsy the requested distance to shift everything vertically
     */
    public void scroll(int dsx, int dsy)
    {
        if (scrollImage != null)
        {
            if (limited)
            {
                // calculate limits of scrolling
                int maxX = scrollImage.getWidth()/world.getCellSize()-world.getWidth();
                int maxY = scrollImage.getHeight()/world.getCellSize()-world.getHeight();
                // apply limits to distances to scroll
                if (scrolledX+dsx < 0) dsx = -scrolledX;
                if (scrolledX+dsx >= maxX) dsx = maxX-scrolledX;
                if (scrolledY+dsy < 0) dsy = -scrolledY;
                if (scrolledY+dsy >= maxY) dsy = maxY-scrolledY;
                // update scroll positions
                scrolledX += dsx;
                scrolledY += dsy;
                // adjust background image
                world.getBackground().drawImage
                (   
                    scrollImage,
                    -scrolledX*world.getCellSize(),
                    -scrolledY*world.getCellSize()
                );
            }
            else
            {
                // update scroll positions
                scrolledX += dsx;
                scrolledY += dsy;
                // create working variables of scroll positions
                int imageX = scrolledX*world.getCellSize();
                int imageY = scrolledY*world.getCellSize();
                // find a similar positive value for scroll positions
                while (imageX < 0) imageX += 100*scrollImage.getWidth();
                while (imageY < 0) imageY += 100*scrollImage.getHeight();
                // get new starting positions for drawing 'scrollImage'
                imageX = imageX%scrollImage.getWidth();
                imageY = imageY%scrollImage.getHeight();
                // create image of appropriate size and tile fill 'scrollImage' onto it
                GreenfootImage hold = new GreenfootImage(scrollImage);
                hold.drawImage(scrollImage, -imageX, -imageY);
                if (imageX > 0) hold.drawImage(scrollImage, scrollImage.getWidth()-imageX, -imageY);
                if (imageY > 0) hold.drawImage(scrollImage, -imageX, scrollImage.getHeight()-imageY);
                if (imageX > 0 && imageY > 0)
                    hold.drawImage(scrollImage, scrollImage.getWidth()-imageX, scrollImage.getHeight()-imageY);
                // set image to background of 'world'
                world.setBackground(hold);
            }
        }
        // adjust position of all actors (that can move with 'setLocation')
        for (Object obj : world.getObjects(SmoothMover.class))
        {
            Actor actor = (Actor) obj;
            actor.setLocation(actor.getX()-dsx, actor.getY()-dsy);
        }
        /*
        for (Object obj : world.getObjects(EnemyBase.class))
        {
            Actor enemy = (Actor) obj;
            enemy.setLocation(enemy.getX()-dsx, enemy.getY()-dsy);
        }*/
    }
     
    /**
     * getter method for the current total scrolled distance horizontally
     *
     * @return the current total offset of horizontal scrolling
     */
    public int getScrolledX()
    {
        return scrolledX;
    }
     
    /**
     * getter method for the current total scrolled distance vertically
     *
     * @return the current total offset of vertical scrolling
     */
    public int getScrolledY()
    {
        return scrolledY;
    }
}