package sim.talkbox;

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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import config.talkbox.TalkBoxConfig;
import utilities.TalkBoxDeserializer;
import utilities.TalkBoxLogger;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ArrayList<AudioButton> buttons = new ArrayList<AudioButton>();
	private TalkBoxDeserializer getInfo = new TalkBoxDeserializer(TalkBoxSim.talkBoxDataPath);
	protected JPanel buttonsPanel;
	public int nButtons = 0;
	protected JPanel swapButtonsPanel;
	protected JPanel allButtonsPanel;
	private int nButtonsPrev = 0;
	private int currentProfile = 0;
	private AudioButton currentBtn;
	JButton swap1;
	JButton swap2;
	JButton swap3;
	JButton swapAll;
	JButton stopAudio;
	private HashMap<Integer, String> buttonsMap;
	private HashMap<Integer, Icon> iconButtonsMap;
	JLabel profileNumber;
	Logger logger = Logger.getGlobal();

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
		nButtons = getInfo.getNumberOfAudioButtons();
		buttonsMap = getInfo.getButtonsMap();
		iconButtonsMap = getInfo.getIconButtonsMap();
		profileNumber = new JLabel();
		profileNumber.setForeground(Color.CYAN);
		profileNumber.setText("  Profile 1");
		swapButtonsPanel.add(profileNumber);

		swapButtonsPanel.add(Box.createVerticalStrut(50));
		stopAudio = new JButton("Stop Audio");
		stopAudio.setToolTipText("Stop currently playing audio.");
		swapButtonsPanel.add(stopAudio);

		setupButtons();
		addButtonAudio();
		setUpSwapButtons();
		setUpStopAudioButton();
	}

	private void addButtonAudio() {
		for (AudioButton b : buttons) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logger.log(Level.INFO, "Button number {0} was pressed.", new Object[] { b.buttonNumber });
					if (currentBtn.clip != null && currentBtn.clip.isActive()) {
						currentBtn.clip.stop();
					}
					currentBtn = b;
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

	public class AudioButton extends JButton {

		private static final long serialVersionUID = 1L;
		private String fileName;
		private Icon imageIcon;
		private File profileFolder;
		private File audioFile;
		public int buttonNumber;
		private Clip clip;

		public AudioButton(int buttonNumber, String text) {
			super(text);
			this.buttonNumber = buttonNumber;
			setMargin(new Insets(0, 0, 0, 0));
			setFont(new Font("Chalkboard", Font.PLAIN, 25));
			setPreferredSize(new Dimension(80, 80));
			setVerticalAlignment(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setIconTextGap(-5);
		}

		public void setAudioFile(String fileName) {
			this.fileName = fileName;
			this.profileFolder = getInfo.getProfilesList().getCurrentProfileFolder();
			if (fileName != null) {
				audioFile = new File(profileFolder, fileName);
			} else {
				audioFile = null;
			}
			if (this.clip != null) {
				if (this.clip.isActive()) {
					this.clip.stop();
				}
				Clip clip = this.clip;
				closeClip(clip);
				this.clip = null;
			}
		}

		private void closeClip(Clip clip) {
			Thread clipStopper = new Thread(new Runnable() {
				public void run() {
					clip.close();
				}
			});
			clipStopper.start();
		}

		public void playSound() {
			if (audioFile != null) {
				try {
					if (clip == null) {
						clip = AudioSystem.getClip();
					}
					if (!clip.isOpen()) {
						AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
						clip.open(ais);
						ais.close();
					}

					clip.setMicrosecondPosition(0);
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
					ab.setIcon(null);
				}
				if (iconButtonsMap.get(i) != null) {
					ab.setIcon(iconButtonsMap.get(i));
				}
				buttons.add(ab);
				buttonsPanel.add(buttons.get(i));
			}
		}
		nButtonsPrev = nButtons;
		currentBtn = buttons.get(0);
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
				TalkBoxLogger.logButtonPressEvent(e);
				setProfile(0);
			}
		});
		swap2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				setProfile(1);
			}
		});
		swap3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				setProfile(2);
			}
		});

		swapAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				if (getInfo.getProfilesList().size() > 0) {
					int nextProfile = (currentProfile + 1) % getInfo.getNumberOfAudioSets();
					setProfile(nextProfile);
				}
			}
		});

	}

	private void setUpStopAudioButton() {
		stopAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				if (currentBtn.clip != null) {
					currentBtn.clip.stop();
				}
			}

		});
	}

	protected void setProfile(int newProfile) {
		if (currentProfile != newProfile && getInfo.getProfilesList().size() > newProfile) {
			profileNumber.setText("  Profile " + (newProfile + 1));
			revalidate();
			repaint();
			loadProfile(newProfile);
			logger.log(Level.INFO, "Switching from profile {0} to profile {1}",
					new Object[] { currentProfile + 1, newProfile + 1 });
			currentProfile = newProfile;
		}
	}

	protected void loadProfile(int newProfile) {
		getInfo.getProfilesList().setCurrentProfile(newProfile);
		ArrayList<String> profileFileNames = getInfo.getProfilesList().get(newProfile).getAudioFileNames();
		ArrayList<Icon> imageIcons = getInfo.getProfilesList().get(newProfile).getImageIcons();
		for (int i = 0; i < TalkBoxConfig.numAudButtons; ++i) {
			buttons.get(i).setAudioFile(profileFileNames.get(i));
		}
	}
}
