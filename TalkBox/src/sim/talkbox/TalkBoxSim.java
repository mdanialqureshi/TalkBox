package sim.talkbox;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.talkbox.TalkBoxConfig;
import config.talkbox.TalkBoxSerializer;



public class TalkBoxSim extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static final int frameWidth = 950;


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
		TalkBoxConfig g = new TalkBoxConfig();
		g.run();
		TalkBoxDeserializer deserializer = new TalkBoxDeserializer();
		deserializer.setUpDeserializer();
		TalkBoxSerializer serializer = new TalkBoxSerializer();
		serializer.setUpSerialization();

		
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, frameWidth, 400);
		setResizable(false); 
		JPanel buttonPanel = new ButtonPanel(); //ButtonPanel class constructs the TalkBox simulator GUI.
		setContentPane(buttonPanel); //sets the ContentPane of the TalkBox Simulator to the one created in ButtonPanel class. 
		//ButtonPanel class extends JPanel.
	}
}
