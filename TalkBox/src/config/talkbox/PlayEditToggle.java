package config.talkbox;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JLabel;


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
		    	SimPreviewEditMode.EditMode();
		    	modeLbl.setText("Edit Mode");

		      } else if(ev.getStateChange()==ItemEvent.DESELECTED) {
		    	modeLbl.setText("Playback Mode");
		        SimPreviewEditMode.PlayMode();
		      }
		   }
		});
		
	}
	
	public static boolean isEditMode() {
		if (toggleBtn.isSelected() == true) return true;
		return false;
	}
}
