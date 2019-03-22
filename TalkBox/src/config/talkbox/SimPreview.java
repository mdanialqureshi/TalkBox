package config.talkbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final Logger logger = Logger.getGlobal();
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
	private int currentProfile = 0;
	static JLabel profileNumber;
	Clip clip;

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

		// allButtonsPanel.add(swapButtonsPanel, BorderLayout.NORTH);
		add(swapButtonsPanel, BorderLayout.WEST);
		swap3 = new JButton("Profile 3");
		swapButtonsPanel.add(swap3);
		swapAll = new JButton("  Swap   ");
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
		profileNumber = new JLabel();
		profileNumber.setForeground(Color.CYAN);
		profileNumber.setText("  Profile 1");
		swapButtonsPanel.add(profileNumber);

		addButtonAudio();
		setUpSwapButtons();
	}

	private void setUpSwapButtons() {

		swap1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setProfile(0);
			}
		});
		swap2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setProfile(1);
			}
		});
		swap3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setProfile(2);
			}
		});

		swapAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (TalkBoxConfig.profilesList.size() > 0) {
					int nextProfile = (currentProfile + 1) % TalkBoxConfig.numAudSets;
					setProfile(nextProfile);
				}
			}
		});

	}

	protected void setProfile(int newProfile) {
		if (currentProfile != newProfile && TalkBoxConfig.profilesList.size() > newProfile) {
			profileNumber.setText("  Profile " + (newProfile + 1));
			revalidate();
			repaint();
			loadProfile(currentProfile);
			logger.log(Level.INFO, "switching from profile {0} to profile {1}",
					new Object[] { currentProfile + 1, newProfile + 1 });
			currentProfile = newProfile;
		}
	}

	protected void loadProfile(int newProfile) {
		TalkBoxConfig.profilesList.setCurrentProfile(newProfile);
		ArrayList<String> profileFileNames = TalkBoxConfig.profilesList.get(newProfile).getAudioFileNames();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			buttons.get(i).setAudioFile(profileFileNames.get(i));
		}
	}

	private void addButtonAudio() {
		for (AudioButton b : buttons) {
			if (b.getActionListeners().length < 1) {
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (mode == SimPreviewMode.PLAY_MODE) {

							if (clip != null && clip.isActive()) {
								clip.close();
							}
							currentBtn = b;
							b.playSound();
							logger.log(Level.FINE, "Button number {0} was pressed.", new Object[] { b.buttonNumber });
						} else if (mode == SimPreviewMode.EDIT_MODE) {
							removeHighlight();
							currentBtn = b;
							highlightBtn();
							logger.log(Level.FINE, "Button number {0} was pressed.", new Object[] { b.buttonNumber });
						}
					}
				});
			}
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

		protected static final long serialVersionUID = 1L;
		protected String fileName;
		protected File profileFolder;
		protected File audioFile;
		public int buttonNumber;

		public AudioButton(int buttonNumber, String text) {
			super(text);
			this.buttonNumber = buttonNumber;
			setMargin(new Insets(0, 0, 0, 0));
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
					clip = AudioSystem.getClip();
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
				ArrayList<String> audioFilePaths = TalkBoxConfig.profilesList.getAudioFilesOfCurrentProfile();
				if (i < audioFilePaths.size()) {
					if (audioFilePaths.get(i) != null) {
						String audioFilePath = audioFilePaths.get(i);
						System.out.println(audioFilePath);
						ab.setAudioFile(audioFilePath);
					}
				} else {
					for (Profile p : TalkBoxConfig.profilesList) {
						p.audioFileNames.add(null);
					}
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
		addButtonAudio();
		revalidate();
		repaint();
	}
}