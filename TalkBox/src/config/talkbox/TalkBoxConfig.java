package config.talkbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.List;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class TalkBoxConfig extends JFrame {

	private JSplitPane controlsProfileSplit;
	private JSplitPane simRecorderSplit;
	private JPanel profile;
	private JScrollPane profiles;
	private JTextArea profilesSelector;
	private JPanel sim;
	private JPanel recorder;
	private JButton record;
	private JButton play;
	private JButton[] simButtons;
	private int width = 1280;
	private int height = 720;
	
	public static int numAudButtons = 16; 
	public static int numAudSets = 1;
	public static int numSwapButtons = 2;
	public static Path path = null;
	public static String[][] audFileNames = null;
	
	

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			TalkBoxConfig frame = new TalkBoxConfig();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	

	/**
	 * Create the frame.
	 */
	public TalkBoxConfig() {
				
		controlsProfileSplit = new ControlsProfileSplit(width, height); //constructs the entire configuration app in ControlsProfileSplit class.

		setupFrame();
	}

	private void setupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		this.setContentPane(controlsProfileSplit); //after all components are constructed by other classes it adds the 'master' JSplitPane to the JFrame.
		//ConstrolsPorfileSplit class extends JSplitPane
	}

}
