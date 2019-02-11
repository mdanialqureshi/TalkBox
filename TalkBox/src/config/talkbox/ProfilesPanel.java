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

import sim.talkbox.TalkBoxDeserializer;

public class ProfilesPanel extends JPanel {
	private static final Dimension MINIMUM_SIZE = new Dimension(400, 640);
	private final JFileChooser fc;

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
		DefaultListModel<String> modelList = new DefaultListModel<String>();
		modelList.addElement("Default");
		modelList.addElement("Weather");
		JList<String> list = new JList<String>(modelList);

		list.setBounds(175, 51, 242, 368);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(list);

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
		JButton saveProf = new JButton("Save Profile");
		saveProf.setBounds(164, 505, 110, 30);
		saveProf.setHorizontalAlignment(SwingConstants.CENTER);
		saveProf.setVerticalAlignment(SwingConstants.CENTER);
		add(saveProf);
		saveProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSaveFileDialog();
			}
		});

		JButton loadProf = new JButton("Load Profile");
		loadProf.setBounds(30, 470, 110, 30);
		loadProf.setHorizontalAlignment(SwingConstants.CENTER);
		loadProf.setVerticalAlignment(SwingConstants.CENTER);
		add(loadProf);
		loadProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openLoadFileDialog();
			}
		});

		JButton newProf = new JButton("New Profile");
		newProf.setBounds(164, 470, 110, 30);
		newProf.setHorizontalAlignment(SwingConstants.CENTER);
		newProf.setVerticalAlignment(SwingConstants.CENTER);
		add(newProf);

		JButton delProf = new JButton("Delete Profile");
		delProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		delProf.setBounds(30, 505, 110, 30);
		delProf.setHorizontalAlignment(SwingConstants.CENTER);
		delProf.setVerticalAlignment(SwingConstants.CENTER);
		add(delProf);
	}

	private void openSaveFileDialog() {
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File talkBoxDataPath = fc.getSelectedFile();
			TalkBoxSerializer tbs = new TalkBoxSerializer(talkBoxDataPath);
		}
	}

	private void openLoadFileDialog() {
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File talkBoxDataPath = fc.getSelectedFile();
			TalkBoxDeserializer tbds = new TalkBoxDeserializer(talkBoxDataPath);
		}
	}
}
