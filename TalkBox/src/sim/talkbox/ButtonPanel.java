package sim.talkbox;
import config.talkbox.*; //package import

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	TalkBoxDeserializer getInfo = new TalkBoxDeserializer();
	
	
	
	public ButtonPanel() {
		setBackground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel TalkBoxLabel = new JLabel("TalkBox");
		TalkBoxLabel.setBounds(370, 21, 177, 64);
		TalkBoxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TalkBoxLabel.setFont(new Font("Chalkboard", Font.PLAIN, 50));
		TalkBoxLabel.setForeground(Color.WHITE);

		TalkBoxLabel.setVerticalAlignment(SwingConstants.TOP);
		add(TalkBoxLabel);
		
		//Get number of audio buttons from TalkBoxDeserializer 
		
		int numOfButtons = getInfo.getNumberOfAudioButtons();
		
		//fields to help add correct amount of buttons with respect to size of JFrame of simulator. 
		int trackColumns = 0;
		int trackRows = 0;
		
		//loop to create buttons

		if(numOfButtons < 19) {
			
		for(int i = 0; i < numOfButtons; i++) {	

			if(20 + ((i+1 - trackColumns)*150) > TalkBoxSim.frameWidth && (80 + trackRows) < TalkBoxSim.frameHeight) {
				trackColumns += 6;
				trackRows += 80;
				}
			
			if(20 + ((i+1 - trackColumns)*150) < TalkBoxSim.frameWidth && (80 + trackRows) < TalkBoxSim.frameHeight) { //+1 to account for checking LAST Jbutton that fits in Frame
				buttons.add(new JButton("Button " + (i+1)));
				buttons.get(i).setBounds(20 + ((i-trackColumns)*150), 100 + trackRows, 140, 70);
				buttons.get(i).setFont(new Font("Chalkboard", Font.PLAIN, 25));
				add(buttons.get(i));
			} 
		}
		
		}else { //if user wants more then 18 buttons
			for(int i = 0; i < numOfButtons; i++) {	
				/*check if buttons will fit in frame going left to right if not go to next row in frame
				 * i -11 each row b/c 11 buttons per row. 
				 */
				if(20 + ((i+1 - trackColumns)*80) > TalkBoxSim.frameWidth && (80 + trackRows) < TalkBoxSim.frameHeight) {
					trackColumns += 11;
					trackRows += 40;
					}
				
				if(20 + ((i+1 - trackColumns)*80) < TalkBoxSim.frameWidth && (80 + trackRows) < TalkBoxSim.frameHeight) { //+1 to account for checking LAST JButton that fits in Frame
					buttons.add(new JButton("Button " + (i+1)));
					buttons.get(i).setBounds(20 + ((i-trackColumns)*80), 80 + trackRows , 80, 40);
					buttons.get(i).setFont(new Font("Chalkboard", Font.PLAIN, 25));
					add(buttons.get(i));
				}	
		}
		}
		
		
		
		//adding audio functionality to some of the buttons 
		
		buttons.get(0).addActionListener(new ActionListener() {
			// button 1 has an actionListener which calls PlaySound Method and plays sound
			// of the file. (When button is clicked)
			public void actionPerformed(ActionEvent e) {
				String helloFile = "hello.wav";
				playSound(helloFile);
			}

		});	
		buttons.get(1).addActionListener(new ActionListener() {
			// button 2 has an actionListener which calls PlaySound Method and plays sound
			// of the file. (When button is clicked)
			public void actionPerformed(ActionEvent e) {
				playSound("bye.wav"); // file name must be passed in as a String parameter.
			}
		});
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
			File file = new File(this.getClass().getResource("/" + soundName).getFile()); // gets the file from its
																							// package using file name
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}
}
