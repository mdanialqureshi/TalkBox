package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class ProfilesPanel extends JPanel {
	private static final Dimension MINIMUM_SIZE = new Dimension(400, 640);
	private final JFileChooser fc;
	DefaultListModel<String> profilesListModel;
	JList<String> profilesJList;
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
		profilesListModel = new DefaultListModel<String>();

		profilesJList = new JList<String>(profilesListModel);

		profilesJList.setBounds(175, 51, 242, 368);
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

		JScrollPane profiles = new JScrollPane(profilesJList);
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
		loadedProfile = selectedProfile;
		ArrayList<String> audioFiles = TalkBoxConfig.profilesList.get(selectedProfile).getAudioFileNames();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			SimRecorderSplit.simPreview.buttons.get(i).setAudioFile(audioFiles.get(i));
		}
	}

	protected void deleteProfile() {
		profilesListModel.remove(selectedProfile);
	}

	protected void createNewProfile() {
		TalkBoxConfig.numAudSets++;
		String profileName = "profile-" + TalkBoxConfig.numAudSets;
		profilesListModel.addElement(profileName);
		Profile newProfile = new Profile(profileName);
		TalkBoxConfig.profilesList.add(newProfile);
	}

	public String getProfileName(int profileIndex) {
		return profilesListModel.getElementAt(profileIndex);
	}
}
