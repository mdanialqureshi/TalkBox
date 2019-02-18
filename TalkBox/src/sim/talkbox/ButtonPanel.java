package sim.talkbox;

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
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected ArrayList<AudioButton> buttons = new ArrayList<AudioButton>();
	private TalkBoxDeserializer getInfo = new TalkBoxDeserializer();
	protected JPanel buttonsPanel;
	private int nButtons = 0;
	private int nButtonsPrev = 0;
	private HashMap<Integer,String> buttonsMap;


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

		for(AudioButton b : buttons) {
			
			b.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					playSound(b.fileName);
				}
				
			});
		}
		
//		if (buttons.size() >= 2) {
//			// adding audio functionality to some of the buttons
//			buttons.get(0).addActionListener(new ActionListener() {
//				// button 1 has an actionListener which calls PlaySound Method and plays sound
//				// of the file. (When button is clicked)
//				public void actionPerformed(ActionEvent e) {
//					// String helloFile = "hello.wav";
//					playSound(getInfo.audioFileNames[0][0]);
//
//				}
//			});
//
//			buttons.get(1).addActionListener(new ActionListener() {
//				// button 2 has an actionListener which calls PlaySound Method and plays sound
//				// of the file. (When button is clicked)
//				public void actionPerformed(ActionEvent e) {
//					playSound(getInfo.audioFileNames[0][1]);
//				}
//			});
//		}
	}

	/**
	 * ActionListeners of the buttons call playSound() method which plays the sound
	 * of the button. The Argument being passed in is the name of the Audio file
	 * which the button will play.
	 * 
	 * @param soundName name of audio file associated with the respective button
	 */

	private void playSound(String soundName) {
		try {
			File file = new File("audio/" + soundName); // gets the file from its
																		// package using file name
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}

	public class AudioButton extends JButton {
		
		private static final long serialVersionUID = 1L;
		public String fileName;
		public int buttonNumber;
		
		public AudioButton(String text) {
			super(text);
			buttonNumber = Integer.parseInt(text);
			setVerticalAlignment(SwingConstants.BOTTOM);
			setFont(new Font("Chalkboard", Font.PLAIN, 25));
			setPreferredSize(new Dimension(70, 40));
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
				buttons.add(new AudioButton("" + (i + 1)));
				buttonsPanel.add(buttons.get(i));
			}
		}
		nButtonsPrev = nButtons;
		
		
		for(Map.Entry<Integer, String> entry: buttonsMap.entrySet()) {
			for(int i = 0; i < nButtons; i++) {
				if(buttons.get(i).buttonNumber == entry.getKey()) {
					buttons.get(i).fileName = entry.getValue();
				}
			}
		}
		
		
	}

	public void updateButtons(int nButtons) {
		this.nButtons = nButtons;
		setupButtons();
		revalidate();
		repaint();
	}

}
