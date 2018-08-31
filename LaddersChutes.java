
/**
 * Write a description of class LaddersChutes here.
 * 
 * @author Matthew MacFadyen
 * @version February 16th, 2017
 */
public class LaddersChutes
{
    // instance variables - replace the example below with your own
    private int start; 
    private int end; 

    /**
     * Constructor for objects of class LaddersChutes
     */
    public LaddersChutes(int start, int end)
    {
        this.start = start;
        this.end = end; 
    }

    public int getStart() 
    {
        return start; 
    }

    public int getEnd()
    {
        return end;
    }

    public boolean isChute()
    {
        boolean isChute = false; 
        if(start > end)
        {
            isChute = true;

        }
        return isChute;
    }

    public boolean isLadder() 
    {
        boolean isLadder = false; 
        if(start < end)
        {
            isLadder = true;

        }
        return isLadder;

    }
}
