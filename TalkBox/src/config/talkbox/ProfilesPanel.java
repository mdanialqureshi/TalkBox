package config.talkbox;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ProfilesPanel extends TalkBoxConfig {

	public static void setupProfiles(JPanel j) {
		j.setLayout(null);
		
		//Label
		JLabel lblProfiles = new JLabel("Profiles");
		lblProfiles.setLocation(87, 23);
		lblProfiles.setSize(122, 33);
		lblProfiles.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		lblProfiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfiles.setVerticalAlignment(SwingConstants.CENTER);
		j.add(lblProfiles);
		
		//Profiles Selector
		DefaultListModel<String> modelList = new DefaultListModel<String>();
		modelList.addElement("Default");
		modelList.addElement("Weather");
		JList<String> list = new JList<String>(modelList);
		
		list.setBounds(175, 51, 242, 368);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		j.add(list);
		
		JScrollPane profiles = new JScrollPane(list);
		profiles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		profiles.setViewportBorder(new LineBorder(Color.GRAY));
		profiles.setBounds(24, 93, 261, 375);
		j.add(profiles);
		
		//Profiles search
		JLabel lblProfSearch = new JLabel("Search for a profile:");
		lblProfSearch.setBounds(24, 68, 261, 21);
		j.add(lblProfSearch);

		JTextField textField = new JTextField();
		profiles.setColumnHeaderView(textField);
		textField.setColumns(10);
		
		
		//Buttons
		JButton setProf = new JButton("Set Profile");
		setProf.setBounds(24, 470, 114, 33);
		setProf.setHorizontalAlignment(SwingConstants.CENTER);
		setProf.setVerticalAlignment(SwingConstants.CENTER);
		j.add(setProf);
		
		JButton createProf = new JButton("Create New Profile");
		createProf.setBounds(68, 503, 141, 33);
		createProf.setHorizontalAlignment(SwingConstants.CENTER);
		createProf.setVerticalAlignment(SwingConstants.CENTER);
		j.add(createProf);
		
		JButton delProf = new JButton("Delete Profile");
		delProf.setBounds(139, 470, 132, 33);
		delProf.setHorizontalAlignment(SwingConstants.CENTER);
		delProf.setVerticalAlignment(SwingConstants.CENTER);
		j.add(delProf);
	}

	
}
