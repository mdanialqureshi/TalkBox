package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SimPreview extends JPanel {

	private static final long serialVersionUID = 1L;

	static ArrayList<JButton> buttons = new ArrayList<JButton>();
	int nButtons;
	int nButtonsPrev = 0;

	public SimPreview() {
		setBackground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

		JLabel TalkBoxLabel = new JLabel("TalkBox");
		TalkBoxLabel.setPreferredSize(new Dimension(900, 100));
		TalkBoxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TalkBoxLabel.setVerticalAlignment(SwingConstants.TOP);
		TalkBoxLabel.setFont(new Font("Chalkboard", Font.PLAIN, 50));
		TalkBoxLabel.setForeground(Color.WHITE);
		add(TalkBoxLabel);

		// Get number of audio buttons from TalkBoxDeserializer
		nButtons = TalkBoxConfig.numAudButtons;
		setupButtons();
		addButtonAudio();
	}

	public void addButtonAudio() {
		if (buttons.size() >= 2) {
			// adding audio functionality to some of the buttons
			buttons.get(0).addActionListener(new ActionListener() {
				// button 1 has an actionListener which calls PlaySound Method and plays sound
				// of the file. (When button is clicked)
				public void actionPerformed(ActionEvent e) {
					// String helloFile = "hello.wav";
					playSound(TalkBoxConfig.audFileNames[0][0]);
				}
			});

			buttons.get(1).addActionListener(new ActionListener() {
				// button 2 has an actionListener which calls PlaySound Method and plays sound
				// of the file. (When button is clicked)
				public void actionPerformed(ActionEvent e) {
					playSound(TalkBoxConfig.audFileNames[0][1]); // file name must be passed in as a String parameter.
				}
			});
		}
	}

	/**
	 * ActionListeners of the buttons call playSound() method which plays the sound
	 * of the button. The Argument being passed in is the name of the Audio file
	 * which the button will play.
	 * 
	 * @param soundName name of audio file associated with the respective button
	 */

	public void playSound(String soundName) {
		try {
			File file = new File("bin/TalkBoxData/audio/" + soundName); // gets the file from its
																		// package using file name
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}

	/*
	 * public static void editLabel(JButton b) { b.addActionListener(new
	 * ActionListener() { public void actionPerformed(ActionEvent e) { JFrame frame
	 * = new JFrame(); String input = JOptionPane.showInputDialog(frame,
	 * "Enter button label: "); b.setText(input);
	 * 
	 * }
	 * 
	 * });
	 * 
	 * }
	 */

	private void setupButtons() {
		for (int i = nButtonsPrev; i < nButtons; i++) {
			buttons.add(new JButton("" + (i + 1)));
			JButton btn = buttons.get(i);
			btn.setVerticalAlignment(SwingConstants.BOTTOM);
			btn.setFont(new Font("Chalkboard", Font.PLAIN, 25));
			btn.setPreferredSize(new Dimension(70,40));
			add(btn);
		}

		nButtonsPrev = nButtons;
	}

	public void updateButtons(int nButtons) {
		this.nButtons = nButtons;
		setupButtons();
		revalidate();
		repaint();
	}
}