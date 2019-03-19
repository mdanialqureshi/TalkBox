package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProfilesPanel extends JPanel {
	private static final Dimension MINIMUM_SIZE = new Dimension(400, 640);
	private final JFileChooser fc;
	DefaultListModel<String> profilesListModel;
	JList<String> profilesJList;
	private int loadedProfile = 0;
	private int selectedProfile = 0;
	JButton loadProf;
	JButton newProf;
	JButton delProf;
	JTextArea textArea;
	File[] logFiles;
	int currentLogFile = 0;

	public ProfilesPanel() {
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setupProfiles();
	}

	private void setupProfiles() {
		setLayout(null);
		setMinimumSize(MINIMUM_SIZE);

		// Label
		JLabel lblProfiles = new JLabel("Profiles");
		lblProfiles.setLocation(87, 23);
		lblProfiles.setSize(122, 33);
		lblProfiles.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		lblProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfiles.setVerticalAlignment(SwingConstants.CENTER);
		add(lblProfiles);

		// Profiles Selector
		profilesListModel = new DefaultListModel<String>();

		profilesJList = new JList<String>(profilesListModel);

		profilesJList.setBounds(6, 34, 242, 368);
		profilesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(profilesJList);

		for (int i = 1; i <= TalkBoxConfig.numAudSets; ++i) {
			profilesListModel.addElement("profile-" + i);
		}
		profilesJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedProfile = ((JList<String>) e.getSource()).getSelectedIndex();
				TalkBoxConfig.profilesList.setCurrentProfile(selectedProfile);
			}
		});
		profilesJList.setSelectedIndex(selectedProfile);

		JScrollPane profiles = new JScrollPane(profilesJList);
		profiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		profiles.setViewportBorder(new LineBorder(Color.GRAY));
		profiles.setBounds(10, 93, 260, 236);
		add(profiles);

		// Profiles search
		JLabel lblProfSearch = new JLabel("Search for a profile:");
		lblProfSearch.setBounds(9, 68, 261, 21);
		add(lblProfSearch);

		JTextField textField = new JTextField();
		profiles.setColumnHeaderView(textField);
		textField.setColumns(10);

		// Buttons
		loadProf = new JButton("Load Profile");
		loadProf.setMargin(new Insets(0, 0, 0, 0));
		loadProf.setBounds(20, 329, 110, 30);
		loadProf.setHorizontalAlignment(SwingConstants.CENTER);
		loadProf.setVerticalAlignment(SwingConstants.CENTER);
		add(loadProf);
		loadProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSelectedProfile();
			}
		});

		newProf = new JButton("New Profile");
		newProf.setMargin(new Insets(0, 0, 0, 0));
		newProf.setBounds(142, 329, 110, 30);
		newProf.setHorizontalAlignment(SwingConstants.CENTER);
		newProf.setVerticalAlignment(SwingConstants.CENTER);
		add(newProf);
		newProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewProfile();
			}
		});

		delProf = new JButton("Delete Profile");
		delProf.setMargin(new Insets(0, 0, 0, 0));
		delProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteProfile();
			}
		});
		delProf.setBounds(78, 364, 110, 30);
		delProf.setHorizontalAlignment(SwingConstants.CENTER);
		delProf.setVerticalAlignment(SwingConstants.CENTER);
		add(delProf);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setBounds(25, 417, 245, 214);
		textArea.setLineWrap(true);
		readLogs();
		add(textArea);

		JLabel label = new JLabel("Logs: ");
		label.setBounds(21, 394, 261, 21);
		add(label);

		JButton btnPreviousLog = new JButton("Previous Log");
		btnPreviousLog.setBounds(23, 638, 117, 29);
		btnPreviousLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("prev");
			}
		});
		add(btnPreviousLog);

		JButton btnNextLog = new JButton("Next Log");
		btnNextLog.setBounds(153, 638, 117, 29);
		btnNextLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("next");
			}
		});
		add(btnNextLog);

	}

	protected void readCurrentLog(String target) {
		if (logFiles.length > 0) {
			if (target.equals("prev") && currentLogFile < logFiles.length - 1) {
				currentLogFile++;
			} else if (target.equals("next") && currentLogFile > 0) {
				currentLogFile--;
			}
		}
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(logFiles[currentLogFile])));
			textArea.read(input, "Reading Log File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void readLogs() {
		File simLogs = new File(TalkBoxConfig.talkBoxDataPath, "sim-logs");
		if (simLogs.isDirectory())
			logFiles = simLogs.listFiles();
		if (logFiles != null && logFiles.length > 0) {
			Arrays.sort(logFiles, Collections.reverseOrder());
			readCurrentLog("current");
		}

	}

	protected void loadSelectedProfile() {
		loadedProfile = selectedProfile;
		TalkBoxConfig.profilesList.setCurrentProfile(selectedProfile);
		ArrayList<String> audioFiles = TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			SimRecorderSplit.simPreview.buttons.get(i).setAudioFile(audioFiles.get(i));
		}
		SimPreview.profileNumber.setText("  Profile " + (TalkBoxConfig.profilesList.getCurrentProfile() + 1));
	}

	protected void deleteProfile() {
		profilesListModel.remove(selectedProfile);
		TalkBoxConfig.numAudSets--;
		selectedProfile = Math.floorMod(selectedProfile - 1, TalkBoxConfig.numAudSets);
		profilesJList.setSelectedIndex(selectedProfile);
	}

	protected void createNewProfile() {
		TalkBoxConfig.numAudSets++;
		selectedProfile = Math.floorMod(++selectedProfile, TalkBoxConfig.numAudSets);
		String profileName = "profile-" + TalkBoxConfig.numAudSets;
		profilesListModel.addElement(profileName);
		Profile newProfile = new Profile(profileName);
		TalkBoxConfig.profilesList.add(newProfile);
	}

	public String getProfileName(int profileIndex) {
		return profilesListModel.getElementAt(profileIndex);
	}

	public String getSelectedProfileName() {
		return profilesListModel.getElementAt(selectedProfile);
	}
}
