
package sensorsimulator;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * This is the serial communications class for building (and setting the properties
 * of) the serial port object.
 * 
 * @author Mike Fouche
 */

public class SerialCommsRS232
{
    private SerialPort sP;
    private DataObject dataObject;
    
    /**
     * Constructor
     */
    public SerialCommsRS232()
    {
        super();
    }
    
    /**
     *
     * @param portName The COM port number - "COM3".
     * @param dObj The data object.
     * 
     * @return The serial port object.
     */
    public SerialPort connect ( String portName, DataObject dObj )  
    {
        this.dataObject = dObj;
        String displayData = "";
        
        try
        {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            if ( portIdentifier.isCurrentlyOwned() )
            {
                displayData = "Error: Port is currently in use";
                dataObject.setCLogDisplayText(displayData);
             }
            else
            {
                CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
                
                // Verify that commPort is of the same type as SerialPort
                if ( commPort instanceof SerialPort ) 
                {
                    SerialPort serialPort = (SerialPort)commPort;
                    
                    serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,
                                     SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                    
                    sP = serialPort;
                }
                else
                {
                    displayData = "Error: Only serial ports are handled by this code.";
                    dataObject.setCLogDisplayText(displayData);
                }
            } 
        }
        catch(PortInUseException | UnsupportedCommOperationException | NoSuchPortException e)
        {
            displayData = "Exception: "+e+", in SerialCommsRS232";
            dataObject.setCLogDisplayText(displayData);
        }   
        
        // Return the serial port object.
        return sP;
    } 
    
} // end of class SerialCommsRS232

