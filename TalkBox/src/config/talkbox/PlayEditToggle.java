package config.talkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JToggleButton toggleBtn;
	private ImageIcon editIcon = new ImageIcon("images/edit-icon.png");
	private ImageIcon playbackIcon = new ImageIcon("images/playback-icon.png");
	/**
	 * 
	 */
	
	public PlayEditToggle() {
		toggleBtn = new JToggleButton("Switch modes");
		add(toggleBtn);
		toggleBtn.addItemListener(new ItemListener() {
		   public void itemStateChanged(ItemEvent ev) {
		      if(ev.getStateChange()==ItemEvent.SELECTED) {
		        System.out.println("Edit Mode");
		      } else if(ev.getStateChange()==ItemEvent.DESELECTED) {
		        System.out.println("Playback Mode");
		      }
		   }
		});
		
	}
	
	public static boolean isEditMode() {
		if (toggleBtn.isSelected() == true) return true;
		return false;
	}
}
