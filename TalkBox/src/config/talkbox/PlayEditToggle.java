package config.talkbox;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import config.talkbox.SimPreview.AudioButton;

public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimPreview simPreview;
	private JToggleButton toggleBtn;
	private AudioButton currentBtn;
	private JTextField buttonLbl;
	int numOfButtons = TalkBoxConfig.numAudButtons;
	JLabel instruction;
	Recorder recObj;
	int doubleClick;
	JFrame confirmAudio;
	JPanel confirmationPrompt;
	JButton addToButton;
	JButton cancel;

	/**
	 * 
	 */
	public PlayEditToggle(SimPreview simPreview,Recorder recObj) {
		confirmAudio = new JFrame("Please Confirm");
		confirmationPrompt = new JPanel(new FlowLayout());
		addToButton = new JButton("Add recording to button");
		cancel = new JButton("Cancel");
		
		confirmAudio.setSize(200, 100);
		confirmationPrompt.add(addToButton);
		confirmationPrompt.add(cancel);
		confirmAudio.add(confirmationPrompt);
		confirmAudio.setLocationRelativeTo(null);
		
		this.simPreview = simPreview;
		this.recObj = recObj;
		JLabel modeLbl = new JLabel("Playback Mode");
		add(modeLbl);
		toggleBtn = new JToggleButton("Switch Modes");
		add(toggleBtn);
		buttonLbl = new JTextField("Button Label");
		add(buttonLbl);
		buttonLbl.setColumns(10);

		for (int i = 0; i < numOfButtons; i++) {
			AudioButton b = simPreview.buttons.get(i);

			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					buttonLbl.setText(b.getText());
				}
			});
		}

		toggleBtn.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					EditMode();
					modeLbl.setText("Edit Mode");

				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					modeLbl.setText("Playback Mode");
					PlayMode();
				}
			}

		});

		buttonLbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateLabel();
			}
		});

		buttonLbl.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				buttonLbl.setText("");
			}

			public void focusLost(FocusEvent e) {
				if (buttonLbl.getText().isEmpty() && currentBtn != null) {
					buttonLbl.setText(currentBtn.getText());
				}
			}
		});
	}

	private void EditMode() {
		int numOfButtons = TalkBoxConfig.numAudButtons;

		for (int i = 0; i < numOfButtons; i++) {
			editLabelandAudio(simPreview.buttons.get(i));
		}
		
	}

	private void PlayMode() {

		for (int i = 0; i < numOfButtons; i++) {
			resetPlayMode(simPreview.buttons.get(i));
		}

	}

	private void editLabelandAudio(AudioButton b) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBtn = b;
				if(SoundRecorder.counter > 0) {
					addButtonAudio();
				}
				}
		});
	}

	public void updateLabel() {
		if (currentBtn != null) {
			currentBtn.setText(buttonLbl.getText());
		}
		// currentBtn.setBackground(null);
	}

	private void resetPlayMode(AudioButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}

	
	
	private void addButtonAudio() {
		
		confirmAudio.setVisible(true);
		
		addToButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				currentBtn.fileName = SoundRecorder.userAudioFileName + ".wav";
				confirmAudio.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				confirmAudio.dispose();
			}
		});
		
		
		
		
	}

}