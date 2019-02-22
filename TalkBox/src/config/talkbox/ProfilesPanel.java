package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilities.TalkBoxDeserializer;

public class ProfilesPanel extends JPanel {
	private static final Dimension MINIMUM_SIZE = new Dimension(400, 640);
	private final JFileChooser fc;
	DefaultListModel<String> profilesList;
	JList<String> list;
	private int loadedProfile;
	private int selectedProfile;

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
		profilesList = new DefaultListModel<String>();

		list = new JList<String>(profilesList);

		list.setBounds(175, 51, 242, 368);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(list);

		for (int i = 1; i <= TalkBoxConfig.numAudSets; ++i) {
			profilesList.addElement("profile-" + i);
		}
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedProfile = e.getFirstIndex();
			}
		});

		JScrollPane profiles = new JScrollPane(list);
		profiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		profiles.setViewportBorder(new LineBorder(Color.GRAY));
		profiles.setBounds(25, 93, 260, 375);
		add(profiles);

		// Profiles search
		JLabel lblProfSearch = new JLabel("Search for a profile:");
		lblProfSearch.setBounds(24, 68, 261, 21);
		add(lblProfSearch);

		JTextField textField = new JTextField();
		profiles.setColumnHeaderView(textField);
		textField.setColumns(10);

		// Buttons
		JButton loadProf = new JButton("Load Profile");
		loadProf.setBounds(30, 470, 110, 30);
		loadProf.setHorizontalAlignment(SwingConstants.CENTER);
		loadProf.setVerticalAlignment(SwingConstants.CENTER);
		add(loadProf);
		loadProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSelectedProfile();
			}
		});

		JButton newProf = new JButton("New Profile");
		newProf.setBounds(164, 470, 110, 30);
		newProf.setHorizontalAlignment(SwingConstants.CENTER);
		newProf.setVerticalAlignment(SwingConstants.CENTER);
		add(newProf);
		newProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewProfile();
			}
		});

		JButton delProf = new JButton("Delete Profile");
		delProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteProfile();
			}
		});
		delProf.setBounds(30, 505, 110, 30);
		delProf.setHorizontalAlignment(SwingConstants.CENTER);
		delProf.setVerticalAlignment(SwingConstants.CENTER);
		add(delProf);
	}

	protected void loadSelectedProfile() {
		String selectedProfileName = getProfileName(selectedProfile);
		String[] audioFiles = TalkBoxConfig.profilesMap.get(selectedProfileName);
		loadedProfile = selectedProfile;
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			TalkBoxConfig.buttonsMap.put(i, audioFiles[i]);
			SimRecorderSplit.simPreview.buttons.get(i).setAudioFile(audioFiles[i]);
		}
	}

	protected void deleteProfile() {

		profilesList.remove(selectedProfile);
	}

	protected void createNewProfile() {
		TalkBoxConfig.numAudSets++;
		String profileName = "profile-" + TalkBoxConfig.numAudSets;
		profilesList.addElement(profileName);
		TalkBoxConfig.profilesMap.put(profileName, new String[TalkBoxConfig.numAudButtons]);
	}

	public String getProfileName(int profileIndex) {
		return profilesList.getElementAt(profileIndex);
	}
}
