package config.talkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SimPreviewEditMode extends SimPreview {
	
	public static void EditMode() {
		
		int numOfButtons = TalkBoxConfig.numAudButtons;
		
			for (int i=0; i<numOfButtons; i++) {
				editLabel(buttons.get(i));
			}

		}
	
	public static void PlayMode() {

		int numOfButtons = TalkBoxConfig.numAudButtons;
		
			for (int i=0; i<numOfButtons; i++) {
				resetPlayMode(buttons.get(i));
			}
	}
	
	
	public static void editLabel(JButton b) {
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    JFrame frame = new JFrame();
				    String input = JOptionPane.showInputDialog(frame, "Enter button label: ");
				    b.setText(input);   
			}		
		});
	}
	
	public static void resetPlayMode(JButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}
}

	