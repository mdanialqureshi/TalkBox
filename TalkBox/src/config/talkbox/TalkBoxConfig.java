package config.talkbox;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileSystemView;

import utilities.TalkBoxDeserializer;

public class TalkBoxConfig extends JFrame {

	private static final Dimension MINIMUM_SIZE = new Dimension(860, 580);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JSplitPane controlsProfileSplit;
	private int width = 1280;
	private int height = 720;

	public static File talkBoxDataPath;
	private File tbc;
	public static int numAudButtons = 20;
	static int numAudSets = 1;
	static int numSwapButtons = 2;
	static String[][] audFileNames = new String[200][200];
	static HashMap<Integer, String> buttonsMap = new HashMap<Integer, String>();

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
		loadTalkBoxConfiguration();
																		// ControlsProfileSplit class.
		controlsProfileSplit = new ControlsProfileSplit(width, height); // constructs the entire configuration app in
		setupFrame();

	}

	private void loadTalkBoxConfiguration() {
		loadTalkBoxConfigurationFolder();
		TalkBoxSerializer tbs = new TalkBoxSerializer(talkBoxDataPath);
		tbs.init();
		TalkBoxDeserializer tbd = new TalkBoxDeserializer(talkBoxDataPath);

		numAudButtons = tbd.getNumberOfAudioButtons();
		numAudSets = tbd.getNumberOfAudioSets();
		numSwapButtons = tbd.getNumberOfAudioSets();
		audFileNames = tbd.getAudioFileNames();
		buttonsMap = tbd.getButtonsMap();
	}

	private void loadTalkBoxConfigurationFolder() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Please choose a directory to save or load TalkBox data");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setVisible(true);
		// only show directories
		fileChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File talkBoxDataParentDir = fileChooser.getSelectedFile();
			talkBoxDataPath = new File(talkBoxDataParentDir, "TalkBoxData");
			tbc = new File(talkBoxDataPath, "TalkBoxConfiguration.tbc");
		} else if (returnValue == JFileChooser.CANCEL_OPTION) {
			System.exit(1);
		}
	}

	private void setupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setMinimumSize(MINIMUM_SIZE);
		setLocationRelativeTo(null);
		this.setContentPane(controlsProfileSplit); // after all components are constructed by other classes it adds the
													// 'master' JSplitPane to the JFrame.
		// ConstrolsPorfileSplit class extends JSplitPane
	}

	void setNumAudButtons(int numAudButtons) {
		TalkBoxConfig.numAudButtons = numAudButtons;
	}
}
