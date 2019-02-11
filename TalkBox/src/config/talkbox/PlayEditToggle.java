package config.talkbox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimPreview simPreview;
	private JToggleButton toggleBtn;
	private JLabel modeLbl;
	private JTextField buttonLbl;
	private int buttonNumber;
	int numOfButtons = TalkBoxConfig.numAudButtons;

	/**
	 * 
	 */
	
	public PlayEditToggle(SimPreview simPreview) {		
		this.simPreview = simPreview;
		JLabel modeLbl = new JLabel("Playback Mode");
		add(modeLbl);
		toggleBtn = new JToggleButton("Switch Modes");
		add(toggleBtn);
		buttonLbl = new JTextField("Button Label");
		add(buttonLbl);
		buttonLbl.setColumns(10);
		for (int i=0; i<numOfButtons; i++) {
			JButton b = simPreview.buttons.get(i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				    buttonLbl.setText(b.getText());
				}
			});
		}

		
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
	
	private boolean isEditMode() {
		if (toggleBtn.isSelected() == true) return true;
		return false;
	}


	private void EditMode() {
	
		int numOfButtons = TalkBoxConfig.numAudButtons;
	
			for (int i=0; i<numOfButtons; i++) {
			editLabel(simPreview.buttons.get(i));
		}

	}

	private void PlayMode() {
	
			for (int i=0; i<numOfButtons; i++) {
			resetPlayMode(simPreview.buttons.get(i));
		}
	}

	private void editLabel(JButton b) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    buttonLbl.setText(b.getText());
			    buttonLbl.addFocusListener(new FocusListener() {

					public void focusGained(FocusEvent e) {
						buttonLbl.setText("");
					}

					public void focusLost(FocusEvent e) {
						if (buttonLbl.getText().isEmpty()) {
							buttonLbl.setText(b.getText());
						}
					}
				});
				buttonLbl.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
					updateLabel(b);
					}
				});
			}		
		});
	}
	
	private void updateLabel(JButton b) {
		b.setText(buttonLbl.getText());
	}

	private void resetPlayMode(JButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}	
	
}