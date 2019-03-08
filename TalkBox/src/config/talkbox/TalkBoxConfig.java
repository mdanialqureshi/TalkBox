package config.talkbox;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	public static boolean testmode = false;

	public static File talkBoxDataPath;
	private File tbc;
	public static int numAudButtons = 20;
	static int numAudSets = 1;
	static int numSwapButtons = 2;
	static String[][] audFileNames = new String[1][numAudButtons];
	static HashMap<Integer, String> buttonsMap = new HashMap<Integer, String>();
	static ProfileList profilesList;

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
		profilesList = new ProfileList(talkBoxDataPath);
		Profile defaultProfile = new Profile("default");
		defaultProfile.add(0, "button-1.wav");
		profilesList.add(defaultProfile);

		loadTalkBoxConfiguration();
		// constructs the entire configuration app in
		controlsProfileSplit = new ControlsProfileSplit(width, height);
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
		profilesList = tbd.getProfilesList();
	}

	private void loadTalkBoxConfigurationFolder() {
		if (!testmode) {
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
				profilesList.setTalkBoxDataPath(talkBoxDataPath);
				tbc = new File(talkBoxDataPath, "TalkBoxConfiguration.tbc");
			} else if (returnValue == JFileChooser.CANCEL_OPTION) {
				System.exit(1);
			}

		} else {
			talkBoxDataPath = new File(System.getProperty("user.home"), "TalkBoxData");
		}
	}

	private void setupFrame() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(null,
						"Please make sure you have saved any changes, then click yes to exit.", "Exit warning",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
