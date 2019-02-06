package config.talkbox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JToggleButton toggleBtn;
	private static JLabel modeLbl;

	/**
	 * 
	 */
	
	public PlayEditToggle() {		
		JLabel modeLbl = new JLabel("Playback Mode");
		add(modeLbl);
		toggleBtn = new JToggleButton("Switch Modes");
		add(toggleBtn);
		toggleBtn.addItemListener(new ItemListener() {
		   public void itemStateChanged(ItemEvent ev) {
		      if(ev.getStateChange()==ItemEvent.SELECTED) {
		    	EditMode();
		    	modeLbl.setText("Edit Mode");

		      } else if(ev.getStateChange()==ItemEvent.DESELECTED) {
		    	modeLbl.setText("Playback Mode");
		        PlayMode();
		      }
		   }
		});
		
	}
	
	public static boolean isEditMode() {
		if (toggleBtn.isSelected() == true) return true;
		return false;
	}


	public static void EditMode() {
	
		int numOfButtons = TalkBoxConfig.numAudButtons;
	
			for (int i=0; i<numOfButtons; i++) {
			editLabel(SimPreview.buttons.get(i));
		}

	}

	public static void PlayMode() {

		int numOfButtons = TalkBoxConfig.numAudButtons;
	
			for (int i=0; i<numOfButtons; i++) {
			resetPlayMode(SimPreview.buttons.get(i));
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