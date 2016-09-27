
package sensorsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

/**
 * This class builds the main JPanel and associated JPanels, and sets up the
 * GUI.
 * 
 * @author Mike Fouche
 */
@SuppressWarnings("OverridableMethodCallInConstructor")
public class BuildMainPanel extends JPanel
{

    /**
     * Builds the main JPanel - loads associated parts and pieces as well.
     */
    public BuildMainPanel()
    {
        // This is the main JPanel which sits in the JFrame.
        this.setLayout(new FlowLayout(1,50,10));
        this.setMinimumSize(new Dimension(400,400));
        this.setPreferredSize(new Dimension(400,400));
        this.setBackground(new Color(160,160,160));

        // Build the top JPanel which will hold the control buttons.
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(300,30));
        topPanel.setLayout(new FlowLayout(1,5,5));
        topPanel.setBackground(new Color(160,160,160));
        
        // Add the top JPanel to the main JPanel.
        this.add(topPanel);
        
        // Build a JPanel for holding the text window.
        JPanel panelLogger = new JPanel();
        panelLogger.setMinimumSize(new Dimension(380,320));
        panelLogger.setPreferredSize(new Dimension(380,320));
        panelLogger.setLayout(new FlowLayout(1,5,5));
        panelLogger.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        // Build the text window.
        JTextArea clogDisp = new JTextArea();
        JScrollPane flogScroll = new JScrollPane(clogDisp);
        flogScroll.setPreferredSize(new Dimension(365,300));
        
        panelLogger.add(flogScroll);

        // Build the data object.
        DataObject dObj = new DataObject();
        // Load the JTextArea object.
        dObj.setCLogDisplay(clogDisp);
        
        // Add the text window JPanel to the main JPanel.
        this.add(panelLogger);

        // Set up GUI controls.
        GUISensor gc = new GUISensor(topPanel,dObj);
    }

} // end of class BuildMainPanel
