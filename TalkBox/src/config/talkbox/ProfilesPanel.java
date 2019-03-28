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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilities.TalkBoxLogger;

public class ProfilesPanel extends JPanel {
	private final Logger logger = Logger.getGlobal();
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
	JButton btnNextLog;
	JButton btnPreviousLog;

	public ProfilesPanel() {
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setupProfiles();
	}

	private void setupProfiles() {
		setMinimumSize(MINIMUM_SIZE);

		// Label
		JLabel lblProfiles = new JLabel("Profiles");
		lblProfiles.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		lblProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfiles.setVerticalAlignment(SwingConstants.CENTER);

		// Profiles Selector
		profilesListModel = new DefaultListModel<String>();

		profilesJList = new JList<String>(profilesListModel);
		profilesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(profilesJList);

		for (int i = 1; i <= TalkBoxConfig.numAudSets; ++i) {
			profilesListModel.addElement("profile-" + i);
		}
		profilesJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					selectedProfile = ((JList<String>) e.getSource()).getSelectedIndex();
					logger.log(Level.INFO, "Selected profile {0} in Profiles List",
							new Object[] { selectedProfile + 1 });
					TalkBoxConfig.profilesList.setCurrentProfile(selectedProfile);
				}
			}
		});
		profilesJList.setSelectedIndex(selectedProfile);

		JScrollPane profiles = new JScrollPane(profilesJList);
		profiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		profiles.setViewportBorder(new LineBorder(Color.GRAY));

		// Profiles search
		JLabel lblProfSearch = new JLabel("Search for a profile:");

		JTextField textField = new JTextField();
		profiles.setColumnHeaderView(textField);
		textField.setColumns(10);

		// Buttons
		loadProf = new JButton("Load Profile");
		loadProf.setMargin(new Insets(0, 0, 0, 0));
		loadProf.setHorizontalAlignment(SwingConstants.CENTER);
		loadProf.setVerticalAlignment(SwingConstants.CENTER);
		loadProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				loadSelectedProfile();
			}
		});

		newProf = new JButton("New Profile");
		newProf.setMargin(new Insets(0, 0, 0, 0));
		newProf.setHorizontalAlignment(SwingConstants.CENTER);
		newProf.setVerticalAlignment(SwingConstants.CENTER);
		newProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				createNewProfile();
			}
		});

		delProf = new JButton("Delete Profile");
		delProf.setMargin(new Insets(0, 0, 0, 0));
		delProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				deleteProfile();
			}
		});
		delProf.setHorizontalAlignment(SwingConstants.CENTER);
		delProf.setVerticalAlignment(SwingConstants.CENTER);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		readLogs();

		JLabel label = new JLabel("Logs: ");

		btnPreviousLog = new JButton("Previous Log");
		btnPreviousLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("prev");
			}
		});

		btnNextLog = new JButton("Next Log");
		btnNextLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("next");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(87).addComponent(lblProfiles,
								GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lblProfSearch,
								GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(profiles,
								GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(20)
								.addComponent(loadProf, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(newProf, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(78).addComponent(delProf,
								GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(21).addComponent(label,
								GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING,
										groupLayout.createSequentialGroup().addGap(25).addComponent(scroll))
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addGap(23)
										.addComponent(btnPreviousLog, GroupLayout.PREFERRED_SIZE, 117,
												GroupLayout.PREFERRED_SIZE)
										.addGap(13).addComponent(btnNextLog, GroupLayout.PREFERRED_SIZE, 117,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(22, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(23)
						.addComponent(lblProfiles, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addComponent(lblProfSearch, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGap(4).addComponent(profiles, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(loadProf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(newProf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGap(5).addComponent(delProf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE).addGap(2)
						.addComponent(scroll, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE).addGap(7)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnPreviousLog)
								.addComponent(btnNextLog))));
		setLayout(groupLayout);

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
		logger.log(Level.INFO, "Loaded profile {0}", new Object[] { loadedProfile + 1 });

	}

	protected void deleteProfile() {
		logger.log(Level.INFO, "Deleted profile {0}", new Object[] { selectedProfile + 1 });
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
		logger.log(Level.INFO, "Created profile {0}", new Object[] { TalkBoxConfig.numAudSets });
	}

	public String getProfileName(int profileIndex) {
		return profilesListModel.getElementAt(profileIndex);
	}

	public String getSelectedProfileName() {
		return profilesListModel.getElementAt(selectedProfile);
	}
}
