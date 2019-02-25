package sim.talkbox;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class TalkBoxSim extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int frameWidth = 950;
	public static final int frameHeight = 500;
	public static File talkBoxDataPath;
	public JPanel buttonPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (args.length != 0) {
					
						TalkBoxSim frame = new TalkBoxSim(args[0]);
						frame.setVisible(true);
					} else {
						JFileChooser fileChooser = new JFileChooser(
								FileSystemView.getFileSystemView().getHomeDirectory());
						fileChooser.setDialogTitle("Please choose a directory to load TalkBox Data");
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fileChooser.setVisible(true);
						// only show directories
						fileChooser.setAcceptAllFileFilterUsed(false);
						int returnValue = fileChooser.showOpenDialog(null);
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							File talkBoxDataPath = fileChooser.getSelectedFile();
							TalkBoxSim frame = new TalkBoxSim(
									(talkBoxDataPath.toString()));
							frame.setVisible(true);
						} else if (returnValue == JFileChooser.CANCEL_OPTION) {
							System.exit(1);
						}
					}
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
	public TalkBoxSim(String talkBoxDataPath) {
		TalkBoxSim.talkBoxDataPath = new File(talkBoxDataPath);
		File tbcCheck = new File(talkBoxDataPath, "TalkBoxData.tbc");
			if (tbcCheck.exists()) {
			buildGUI();
		} else {
			JFrame warningPrompt = new JFrame("Warning");
			JPanel launchConfigPrompt = new JPanel();
			JLabel launchConfigLabel = new JLabel(
					"<html>You must first launch the TalkBox Configuration Application <br/> "
							+ " to set up the Simulator Application.</html>");

			launchConfigPrompt.add(launchConfigLabel);
			warningPrompt.add(launchConfigPrompt);
			warningPrompt.setLocationRelativeTo(null);
			warningPrompt.setSize(500, 100);
			warningPrompt.setVisible(true);

			warningPrompt.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {

					if (JOptionPane.CLOSED_OPTION != 0) {
						System.exit(0);
					}
				}
			});
		}
	}

	/**
	 * GUI for TalkBox Simulator is created.
	 */
	public void buildGUI() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, frameWidth, frameHeight);
		setResizable(false);
		buttonPanel = new ButtonPanel(); // ButtonPanel class constructs the TalkBox simulator GUI.
		setContentPane(buttonPanel); // sets the ContentPane of the TalkBox Simulator to the one created in
										// ButtonPanel class.
		// ButtonPanel class extends JPanel.
	}

}
