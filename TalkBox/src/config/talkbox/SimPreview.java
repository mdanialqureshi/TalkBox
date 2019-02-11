package config.talkbox;

import java.awt.BorderLayout;
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
	JPanel buttonsPanel;
	int nButtons = 0;
	int nButtonsPrev = 0;

	public SimPreview() {
		setBackground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(10, 10));

		JLabel simTitle = new JLabel("TalkBox");
		simTitle.setHorizontalAlignment(SwingConstants.CENTER);
		simTitle.setVerticalAlignment(SwingConstants.TOP);
		simTitle.setFont(new Font("Chalkboard", Font.PLAIN, 50));
		simTitle.setForeground(Color.WHITE);
		add(simTitle, BorderLayout.PAGE_START);

		buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
		buttonsPanel.setBackground(Color.DARK_GRAY);
		add(buttonsPanel);
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
			File file = new File(soundName); // gets the file from its
																		// package using file name
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}

	
	private class AudioButton extends JButton {
		private AudioButton(String text) {
			super(text);
			setVerticalAlignment(SwingConstants.BOTTOM);
			setFont(new Font("Chalkboard", Font.PLAIN, 25));
			setPreferredSize(new Dimension(70, 40));
		}
	}

	private void setupButtons() {
		if (nButtons < nButtonsPrev) {
			for (int i = nButtonsPrev - 1; i >= nButtons; i--) {
				buttonsPanel.remove(buttons.get(i));
			}
		} else {
			for (int i = nButtonsPrev; i < nButtons; i++) {
				buttons.add(new AudioButton("" + (i + 1)));
				buttonsPanel.add(buttons.get(i));
			}
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