package sim.talkbox;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class TalkBoxSim extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int frameWidth = 950;
	public static final int frameHeight = 500;
	JPanel buttonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxSim frame = new TalkBoxSim();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	

	/**
	 * Constructor calls buildGUI method which builds the frame and GUI components
	 * for TalkBoxSim
	 */
	public TalkBoxSim() {
		buildGUI();
	}

	/**
	 * GUI for TalkBox Simulator is created.
	 */
	private void buildGUI() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, frameWidth, frameHeight);
		setResizable(false); 
		buttonPanel = new ButtonPanel(); //ButtonPanel class constructs the TalkBox simulator GUI.
		setContentPane(buttonPanel); //sets the ContentPane of the TalkBox Simulator to the one created in ButtonPanel class. 
		//ButtonPanel class extends JPanel.
	}
}
