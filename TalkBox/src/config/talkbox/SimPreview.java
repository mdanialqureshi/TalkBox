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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SimPreview extends JPanel {

	private static final long serialVersionUID = 1L;

	ArrayList<AudioButton> buttons = new ArrayList<AudioButton>();
	protected JPanel buttonsPanel;
	protected JPanel swapButtonsPanel;
	protected JPanel allButtonsPanel;
	AudioButton currentBtn;
	private int nButtons = 0;
	private int nButtonsPrev = 0;
	JButton swap1;
	JButton swap2;
	JButton swap3;
	JButton swapAll;
	private int numOfSwaps = 3;

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
		add(simTitle, BorderLayout.BEFORE_FIRST_LINE);

		swapButtonsPanel = new JPanel();
		swapButtonsPanel.setForeground(new Color(0, 0, 0));
		swapButtonsPanel.setBackground(Color.DARK_GRAY);
		swapButtonsPanel.setLayout(new BoxLayout(swapButtonsPanel, BoxLayout.Y_AXIS));
		swapButtonsPanel.add(Box.createVerticalStrut(15));
		swap1 = new JButton("Profile 1");
		swapButtonsPanel.add(swap1);
		swap2 = new JButton("Profile 2");
		swapButtonsPanel.add(swap2);

		//allButtonsPanel.add(swapButtonsPanel, BorderLayout.NORTH);
		add(swapButtonsPanel, BorderLayout.WEST);
		swap3 = new JButton("Profile 3");
		swapButtonsPanel.add(swap3);
		swapAll = new JButton("Swap");
		swapAll.setToolTipText("Swap through all profiles sequentially.");
		swapButtonsPanel.add(swapAll);
		// allButtonsPanel.add(buttonsPanel);
		// add(swapButtonsPanel, BorderLayout.EAST);

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
		setUpSwapButtons();
	}

	private void setUpSwapButtons() {

		swap1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				loadProfileToSwap(0);
			}

		});
		swap2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				loadProfileToSwap(1);
			}

		});
		swap3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				loadProfileToSwap(2);
			}
		});
		swapAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				loadProfileToSwap(numOfSwaps);
				numOfSwaps++;
				if (numOfSwaps == TalkBoxConfig.numAudSets)
					numOfSwaps = 3;
			}
		});

	}

	protected void loadProfileToSwap(int profileNumber) {
		TalkBoxConfig.profilesList.setCurrentProfile(profileNumber);
		ArrayList<String> profileFileNames = TalkBoxConfig.profilesList.get(profileNumber).getAudioFileNames();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			SimRecorderSplit.simPreview.buttons.get(i).setAudioFile(profileFileNames.get(i));
		}
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
				String audioFilePath = TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile().get(i);
				if (audioFilePath != null) {
					System.out.println(audioFilePath);
					ab.setAudioFile(audioFilePath);
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