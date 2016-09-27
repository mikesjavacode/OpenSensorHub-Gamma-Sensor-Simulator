
package sensorsimulator;

import gnu.io.SerialPort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class loads the GUI objects.
 * 
 * @author Mike Fouche
 */
public class GUISensor 
{
    // Declare the data object.
    private final DataObject dataObject;
    // Declare the thread communications object.
    private final ThreadQueue tQ;
    // Set COMM port value ("COM1" or "COM2", etc.).
    private final String ComSet = "COM3";
    // Declare serial port object.
    private SerialPort sPort;
    // Declare the serial port object.
    private final SerialCommsRS232 sc;

    /**
     * Constructor
     * 
     * @param topPanel The top JPanel in which the control buttons are loaded.
     * @param dObj The data object through commands / data is relayed.
     */
    public GUISensor(JPanel topPanel, DataObject dObj)
    {
        this.dataObject = dObj;
        
        tQ = new ThreadQueue();
        sc = new SerialCommsRS232();
        
        // Inner class with active listener
        SimStatus ss = new SimStatus();
        
        // Add "Start" button to top JPanel.
        JButton startSim = new JButton("Start");
        // Add listener
        startSim.addActionListener(ss);
        // Add to top JPanel
        topPanel.add(startSim);
        
        // Add "Stop" button to top JPanel.
        JButton stopSim = new JButton("Stop");
        // Add listener
        stopSim.addActionListener(ss);
        // Add to top JPanel
        topPanel.add(stopSim);
        
        // Add "Clear Display" button to top JPanel.
        JButton clearDisplay = new JButton("Clear Display");
        // Inner class with active listener
        ClearDisplay cD = new ClearDisplay();
        // Add listener
        clearDisplay.addActionListener(cD);
        // Add to top JPanel
        topPanel.add(clearDisplay);
    }

    //--------------------------------------------------------------------------
    // Inner class for Sensor simulation status options
    private class SimStatus implements ActionListener
    {   
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            String eAction = ae.getActionCommand();
            if( null != eAction )
            switch (eAction) 
            {
                case "Start":
                    // Display the initial information.
                    dataObject.setCLogDisplayText("Opening serial port ...\n");
                    // Set serial port properties and open.
                    sPort = sc.connect(ComSet,dataObject);
                    // Set sensor status to active.
                    tQ.setSimStatus(true);
                    // Launch Gamma sensor thread.
                    new GammaSensor(sPort,tQ,dataObject).start();
                    break;
                case "Stop":
                    // Set sensor status to inactive - this will stop the thread.
                    tQ.setSimStatus(false);
                    // Display the closing information.
                    dataObject.setCLogDisplayText("Closing serial port ...");
                    // Close the serial port.
                    sPort.close();
                    break;
            }        
        }
    } // end of inner class SimStatus 
    
    //--------------------------------------------------------------------------
    // Inner class for clear display button action
    private class ClearDisplay implements ActionListener
    {   
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            // Clear the display window.
            dataObject.clearDisplay();
        }
        
    } // end of inner class ClearDisplay
    
} // end of class GUISensor
