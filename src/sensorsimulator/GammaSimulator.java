
package sensorsimulator;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * This application simulates the output data from a Model 2070 Gamma Detector
 * Module - made by Health Physics Instruments, a division of Far West Technology, 
 * Inc.  The results can be tested via the software tester, GammaSensorTester.
 * 
 * @author Mike Fouche
 */

public class GammaSimulator 
{

    /**
     * @param args There are none.
     */
    public static void main(String[] args) 
    {
        JFrame jf = new JFrame("Gamma Sensor Simulator");
        jf.setSize(400,400);
        jf.setResizable(false);
        jf.setLayout(new BorderLayout());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BuildMainPanel bmp = new BuildMainPanel();
        jf.add(bmp);
        
        jf.setVisible(true);
    }

} // end of class GammaSimulator
