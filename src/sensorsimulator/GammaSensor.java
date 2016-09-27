
package sensorsimulator;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is the main thread that encodes and sends the simulated Gamma Sensor 
 * data to the serial port.
 * 
 * @author Mike Fouche
 */

public class GammaSensor extends Thread
{
    private SerialPort sPort;
    private OutputStream out;
    private ThreadQueue tQ;
    private DataObject dataObject;
    
    /**
     * Constructor
     * 
     * @param sP The Serial port object.
     * @param tQueue The Thread communications object.
     * @param dO The Data object.
     */
    public GammaSensor(SerialPort sP, ThreadQueue tQueue, DataObject dO)
    {
        // Serial port object
        this.sPort = sP;
        // Thread communications
        this.tQ = tQueue;
        // Set up input and output stream objects.
        this.dataObject = dO;
        
        try
        {
            out = sPort.getOutputStream();
        } 
        catch ( IOException e )
        {
            System.out.println("Exception: "+e+", in AttRecv");
        }
    }
    
    @Override
    public void run()
    {
        // Average hourly radiation dose
        int hourRadNum;
        // Measured radiation dose per second
        short secRadNum;
        // Hex value of hourRadNum
        String hourWord;
        // Hex value of secRadNum
        String secWord;
        // Text data to display in window
        String displayData;
        
        // There are 15 bytes per data packet.
        //
        // 1st 8 bytes represent the hourly average
        // next 4 bytes represent the per second dosage
        // next byte is Carriage return
        // last byte is Linefeed
        byte[] sendBytes = new byte[15];
        // Load the Linefeed character.
        sendBytes[14] = 10; 
        // Load the carriage return character.
        sendBytes[13] = 13; 
        
        try
        {
            // Set value for hourly average dose
            hourRadNum = 7248;
            // Set start value for per second dose
            secRadNum  = 26;
           
            // | 4 Hex bytes | 8 Hex bytes | . | CR | LF |
            // Total bytes = 4 + 8 + 1 + 1 + 1 = 15
            
            while( tQ.getSimStatus() )
            {
                // Update the values.
                hourRadNum = 7248;
                secRadNum  = (short)(secRadNum + 1);

                //--------------------------------------------------------------
                // Set up the 8 Hexbyte word - average hourly radiation dose
                // and display it for validation purposes.
                //--------------------------------------------------------------
                hourWord = Integer.toHexString(hourRadNum);
                displayData = "Avg Radiation Dose per hour = "+hourRadNum+
                                                    " uR, Hex value = 0x"+hourWord;
                dataObject.setCLogDisplayText(displayData);

                //--------------------------------------------------------------
                // Set up the 4 Hexbyte word - radiation dose per second
                // and display it for validation purposes.
                //--------------------------------------------------------------
                secWord = Integer.toHexString(secRadNum);
                displayData = "Radiation Dose per second = "+secRadNum+
                                                    " uR, Hex value = 0x"+secWord;
                dataObject.setCLogDisplayText(displayData+"\n");
                
                //--------------------------------------------------------------
                // Set up and load the 4 byte array in the byte array 
                // (to be sent to the RS-232 COM port.)
                //--------------------------------------------------------------
                int bCtr = secWord.length()-1;
                for(int i = 12; i > 12-secWord.length(); i--)
                {
                    sendBytes[i] = (byte)secWord.charAt(bCtr);
                    bCtr--;
                }

                //--------------------------------------------------------------
                // Set up and load the 8 byte array in the byte array 
                // (to be sent to the RS-232 COM port.)
                //--------------------------------------------------------------
                bCtr = hourWord.length()-1;
                for(int i = 8; i > 8-hourWord.length(); i--)
                {
                    sendBytes[i] = (byte)hourWord.charAt(bCtr);
                    bCtr--;
                }    
                
                // Send the loaded byte array to the serial port.
                out.write(sendBytes);

                // Add a 1000 ms delay - simulates sensor output freqency.
                try 
                {
                    Thread.sleep(1000); // do nothing for 1000 milliseconds
                } 
                catch(InterruptedException e)
                {
                    System.out.println("Exception: "+e+", in Sleep thread in class GammaSensor");
                }                    
            }   
        } 
        catch ( IOException e )
        {
            System.out.println("Exception: "+e+", in GammaSensor");
        }
        
        displayData = "Exiting Gamma Sensor Simulator thread ...";
        dataObject.setCLogDisplayText(displayData+"\n");
    }
    
} // end of class GammaSensor
