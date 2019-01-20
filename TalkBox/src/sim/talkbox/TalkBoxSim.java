package sim.talkbox;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import config.talkbox.TalkBoxConfig;

public class TalkBoxSim extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		setBounds(100, 100, 1200, 400);
		setResizable(false);
		JPanel buttonPanel = new ButtonPanel();
		setContentPane(buttonPanel);
	}
}
