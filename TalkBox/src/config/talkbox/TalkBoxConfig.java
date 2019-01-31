package config.talkbox;


import java.awt.EventQueue;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class TalkBoxConfig extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JSplitPane controlsProfileSplit;
	private int width = 1280;
	private int height = 720;
	
	public static int numAudButtons = 19;
	public static int numAudSets = 1;
	public static int numSwapButtons = 2;
	public static Path path = null;
	public static String[][] audFileNames = new String[10][10];
	
	

	/**
	 * Launch the application.
	 */

	public static void main(String args[]) {
		
				
		EventQueue.invokeLater(new Runnable() {
		public void run() {
		try {
			TalkBoxConfig frame = new TalkBoxConfig();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		});
		
		
	
	}

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
		setLocationRelativeTo(null);
		this.setContentPane(controlsProfileSplit); //after all components are constructed by other classes it adds the 'master' JSplitPane to the JFrame.
		//ConstrolsPorfileSplit class extends JSplitPane
	}
}
