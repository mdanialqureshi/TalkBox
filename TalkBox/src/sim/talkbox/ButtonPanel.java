package sim.talkbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import config.talkbox.SimRecorderSplit;
import config.talkbox.TalkBoxConfig;
import utilities.TalkBoxDeserializer;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected ArrayList<AudioButton> buttons = new ArrayList<AudioButton>();
	private TalkBoxDeserializer getInfo = new TalkBoxDeserializer(TalkBoxSim.talkBoxDataPath);
	protected JPanel buttonsPanel;
	public int nButtons = 0;
	protected JPanel swapButtonsPanel;
	protected JPanel allButtonsPanel;
	private int nButtonsPrev = 0;
	private int currentProfile = 0;
	JButton swap1;
	JButton swap2;
	JButton swap3;
	JButton swapAll;
	private HashMap<Integer, String> buttonsMap;
	private int numOfSwaps = 3;

	public ButtonPanel() {
		setBackground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(10, 10));

		JLabel simTitle = new JLabel("TalkBox");
		simTitle.setHorizontalAlignment(SwingConstants.CENTER);
		simTitle.setVerticalAlignment(SwingConstants.TOP);
		simTitle.setFont(new Font("Chalkboard", Font.PLAIN, 50));
		simTitle.setForeground(Color.WHITE);
		add(simTitle, BorderLayout.PAGE_START);
		
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
		nButtons = getInfo.getNumberOfAudioButtons();
		buttonsMap = getInfo.getButtonsMap();
		setupButtons();
		addButtonAudio();
	}

	private void addButtonAudio() {
		for (AudioButton b : buttons) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					b.playSound();
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
			File file = new File("src/audioFiles/" + soundName); // gets the file from its
																	// package using file name
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}

	public class AudioButton extends JButton {

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
			this.profileFolder = getInfo.getProfilesList().getCurrentProfileFolder();
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

				String audioFilePath = getInfo.getAudioFileNames()[currentProfile][i];
				if (audioFilePath != null) {
					System.out.println(audioFilePath);
					ab.setAudioFile(audioFilePath);
				}
				if (buttonsMap.get(i) != null) {
					ab.setText(buttonsMap.get(i));
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
				if (numOfSwaps == getInfo.getNumberOfAudioSets())
					numOfSwaps = 3;
			}
		});

	}

	protected void loadProfileToSwap(int profileNumber) {
		getInfo.getProfilesList().setCurrentProfile(profileNumber);
		ArrayList<String> profileFileNames = getInfo.getProfilesList().get(profileNumber).getAudioFileNames();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			buttons.get(i).setAudioFile(profileFileNames.get(i));
		}
	}
}
