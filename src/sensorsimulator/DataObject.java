
package sensorsimulator;

import javax.swing.JTextArea;

/**
 * This class provides a means to exchange data between different classes.  It
 * is loaded into the constructors of those particular classes.
 * 
 * @author Mike Fouche
 */
public class DataObject 
{
    private JTextArea clogDisp;
    
    /**
     * Constructor
     */
    public DataObject()
    {
        
    }

    //--------------------------------------------------------------------------
    /**
     * This is used by other methods to send text to the display window.
     * 
     * @param text The text to be displayed in the panel window.
     */
    public void setCLogDisplayText(String text)
    {
        clogDisp.append(text+"\n");
        clogDisp.setCaretPosition(clogDisp.getDocument().getLength());
    }        
    
    //--------------------------------------------------------------------------
    /**
     * Loads the JTextArea object.
     * 
     * @param cD The JTextArea object into which the text is written.
     */
    public void setCLogDisplay(JTextArea cD)
    {
        this.clogDisp = cD;
    }
    
    //--------------------------------------------------------------------------
    /**
     * Clears the display.
     */
    public void clearDisplay()
    {
        clogDisp.setText("");
    }        
    
} // end of class DataObject
