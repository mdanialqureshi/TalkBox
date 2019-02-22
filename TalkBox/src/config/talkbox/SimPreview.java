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
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SimPreview extends JPanel {

	private static final long serialVersionUID = 1L;

	ArrayList<AudioButton> buttons = new ArrayList<AudioButton>();
	protected JPanel buttonsPanel;
	AudioButton currentBtn;
	private int nButtons = 0;
	private int nButtonsPrev = 0;

	public enum SimPreviewMode {
		PLAY_MODE, EDIT_MODE;
	}

	public SimPreviewMode mode = SimPreviewMode.PLAY_MODE;

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
		currentBtn = buttons.get(0);
		addButtonAudio();
	}

	private void addButtonAudio() {
		for (AudioButton b : buttons) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (mode == SimPreviewMode.PLAY_MODE) {
						currentBtn = b;
						b.playSound();
					} else if (mode == SimPreviewMode.EDIT_MODE) {
						removeHighlight();
						currentBtn = b;
						highlightBtn();
					}
				}
			});
		}
	}

	public void removeHighlight() {
		if (currentBtn != null) {
			currentBtn.setForeground(Color.BLACK);
			currentBtn.setFont(new Font("Chalkboard", Font.PLAIN, 25));
		}
	}

	public void highlightBtn() {
		if (currentBtn != null) {
			currentBtn.setForeground(Color.BLUE);
			currentBtn.setFont(new Font("Chalkboard", Font.BOLD, 25));
		}
	}

	public class AudioButton extends JButton {

		private static final long serialVersionUID = 1L;
		private String fileName;
		private File profileFolder;
		private File audioFile;
		public int buttonNumber;

		public AudioButton(int buttonNumber, String text) {
			super(text);
			this.buttonNumber = buttonNumber;
			setVerticalAlignment(SwingConstants.BOTTOM);
			setFont(new Font("Chalkboard", Font.PLAIN, 25));
			setPreferredSize(new Dimension(70, 40));
		}

		public void setAudioFile(String fileName) {
			this.fileName = fileName;
			this.profileFolder = TalkBoxConfig.profilesList.getCurrentProfileFolder();
			if (fileName != null) {
				audioFile = new File(profileFolder, fileName);
			} else {
				audioFile = null;
			}
		}

		public void playSound() {
			if (audioFile != null) {
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(audioFile));
					clip.start(); // allows audio clip to be played
				} catch (Exception e) {
					System.err.println("Could not play back audio.");
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("No audio file associated with this button.");
			}
		}
	}

	private void setupButtons() {
		if (nButtons < nButtonsPrev) {
			for (int i = nButtonsPrev - 1; i >= nButtons; i--) {
				buttonsPanel.remove(buttons.get(i));
				buttons.remove(i);
			}
		} else {
			for (int i = nButtonsPrev; i < nButtons; i++) {
				AudioButton ab = new AudioButton(i + 1, Integer.toString(i + 1));
				if (TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile().get(i) != null) {
					System.out.println(TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile().get(i));
					ab.setAudioFile(TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile().get(i));
				}

				if (TalkBoxConfig.buttonsMap.get(i) != null) {
					ab.setText(TalkBoxConfig.buttonsMap.get(i));
				}
				buttons.add(ab);
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