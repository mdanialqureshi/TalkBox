package config.talkbox;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

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
		controlsProfileSplit = new JSplitPane();
		setupFrame();
	}
	
	private void setupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.setContentPane(controlsProfileSplit);
	}

}
