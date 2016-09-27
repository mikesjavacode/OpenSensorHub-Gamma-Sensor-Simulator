
package sensorsimulator;

/**
 * This class provides thread-safe communication between the thread and the 
 * GUISensor class.
 * 
 * @author Mike Fouche
 */
public class ThreadQueue 
{
    private boolean simStatus;
    
    /**
     * Constructor
     */
    public ThreadQueue()
    {
        simStatus = true;
    }

    //--------------------------------------------------------------------------
    /**
     *
     * @param simS Boolean which tells the thread if it should remain active
     * or not.
     */
    public synchronized void setSimStatus(boolean simS)
    {
        simStatus = simS;
    }
    
    //--------------------------------------------------------------------------
    /**
     *
     * @return The status of boolean which determines if the thread should
     * remain active or not.
     */
    public synchronized boolean getSimStatus()
    {
        return simStatus;
    } 
    
} // end of class ThreadQueue
