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
import config.talkbox.SimPreview.SimPreviewMode;

public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SimPreview simPreview;
	protected JToggleButton toggleBtn;
	private AudioButton currentBtn;
	protected JTextField buttonLbl;
	int numOfButtons = TalkBoxConfig.numAudButtons;
	JLabel instruction;
	Recorder recObj;
	int doubleClick;
	JFrame confirmAudio;
	JPanel confirmationPrompt;
	JButton addToButton;
	JButton cancel;
	JLabel fileName;
	JButton updateButtonLbl;

	/**
	 * 
	 */
	public PlayEditToggle(SimPreview simPreview, Recorder recObj) {
		confirmAudio = new JFrame("Please Confirm");
		confirmationPrompt = new JPanel(new FlowLayout());
		addToButton = new JButton("Add recording to button");
		cancel = new JButton("Cancel");
		fileName = new JLabel();
		confirmationPrompt.add(fileName);

		confirmAudio.setSize(200, 120);
		confirmAudio.setResizable(false);
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
		updateButtonLbl = new JButton("Update Label");
		add(updateButtonLbl);

		for (int i = 0; i < numOfButtons; i++) {
			AudioButton b = simPreview.buttons.get(i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttonLbl.setText(b.getText());
				}
			});
		}

		setupButtonLbl();
		setupUpdateButtonLbl();

		toggleBtn.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					editMode();
					modeLbl.setText("Edit Mode");
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					playMode();
					modeLbl.setText("Playback Mode");
				}
			}

		});

	}

	private void setupUpdateButtonLbl() {
		updateButtonLbl.setEnabled(false);
		updateButtonLbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateLabel();
			}
		});
	}

	private void setupButtonLbl() {
		buttonLbl.setEnabled(false);
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

	private void editMode() {
		simPreview.mode = SimPreviewMode.EDIT_MODE;
		recObj.recordBtn.setEnabled(true);
		int numOfButtons = TalkBoxConfig.numAudButtons;
		buttonLbl.setEnabled(true);
		updateButtonLbl.setEnabled(true);
		recObj.recordInfo.setText("Begin recording?");

		currentBtn = simPreview.currentBtn;
		simPreview.highlightBtn();

		for (int i = 0; i < numOfButtons; i++) {
			editLabelandAudio(simPreview.buttons.get(i));
		}

	}

	private void playMode() {
		simPreview.mode = SimPreviewMode.PLAY_MODE;
		updateButtonLbl.setEnabled(false);
		buttonLbl.setEnabled(false);
		recObj.recordBtn.setEnabled(false);
		recObj.recordInfo.setText("Switch to edit mode to begin recording.");

		for (int i = 0; i < numOfButtons; i++) {
			resetPlayMode(simPreview.buttons.get(i));
		}

		simPreview.removeHighlight();
	}

	private void editLabelandAudio(AudioButton b) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBtn = b;
			}
		});
	}

	public void updateLabel() {
		if (currentBtn != null) {
			currentBtn.setText(buttonLbl.getText());
			TalkBoxConfig.buttonsMap.put(currentBtn.buttonNumber-1, currentBtn.getText());
		}
	}

	private void resetPlayMode(AudioButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}

}