package config.talkbox;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import config.talkbox.SimPreview.AudioButton;
import config.talkbox.SimPreview.SimPreviewMode;
import utilities.TalkBoxLogger;

public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getGlobal();
	private SimPreview simPreview;
	protected JToggleButton switchModesButton;
	private AudioButton currentBtn;
	protected JTextField updateButtonLabelTextField;
	int numOfButtons = TalkBoxConfig.numAudButtons;
	JLabel instruction;
	Recorder recObj;
	int doubleClick;
	JFrame confirmAudio;
	JPanel confirmationPrompt;
	JButton addToButton;
	JButton cancel;
	JLabel fileName;
	JButton updateButtonLabelButton;
	JFileChooser fileChooser;
	JLabel modeLbl;

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
		modeLbl = new JLabel("Playback Mode");
		add(modeLbl);
		switchModesButton = new JToggleButton("Switch Modes");
		add(switchModesButton);
		updateButtonLabelTextField = new JTextField("Button Label");
		add(updateButtonLabelTextField);
		updateButtonLabelTextField.setColumns(10);
		updateButtonLabelButton = new JButton("Update Label");
		add(updateButtonLabelButton);

		for (int i = 0; i < numOfButtons; i++) {
			AudioButton b = simPreview.buttons.get(i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateButtonLabelTextField.setText(b.getText());
				}
			});
		}

		setupUpdateButtonLabelTextField();
		setupUpdateButtonLabelButton();

		switchModesButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				logger.log(Level.INFO, "Pressed Switch Modes button");
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					editMode();
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					playMode();
				}
			}

		});

	}

	private void setupUpdateButtonLabelButton() {
		updateButtonLabelButton.setEnabled(false);
		updateButtonLabelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TalkBoxLogger.logButtonPressEvent(e);
				updateLabel();
			}
		});
	}

	private void setupUpdateButtonLabelTextField() {
		updateButtonLabelTextField.setEnabled(false);
		updateButtonLabelTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Enter key with focus on Button Label text field");
				updateLabel();
			}
		});
		updateButtonLabelTextField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				updateButtonLabelTextField.setText("");
			}

			public void focusLost(FocusEvent e) {
				if (updateButtonLabelTextField.getText().isEmpty() && currentBtn != null) {
					updateButtonLabelTextField.setText(currentBtn.getText());
				}
			}
		});
	}

	private void editMode() {
		simPreview.mode = SimPreviewMode.EDIT_MODE;
		recObj.recordBtn.setEnabled(true);
		recObj.btnUploadAudio.setEnabled(true);
		recObj.btnUploadImage.setEnabled(true);
		int numOfButtons = TalkBoxConfig.numAudButtons;
		updateButtonLabelTextField.setEnabled(true);
		updateButtonLabelButton.setEnabled(true);
		recObj.recordInfo.setText("Begin recording?");
		modeLbl.setText("Edit Mode");

		currentBtn = simPreview.currentBtn;
		simPreview.highlightBtn();

		for (int i = 0; i < numOfButtons; i++) {
			addUpdateCurrentButtonListener(simPreview.buttons.get(i));
		}
		logger.log(Level.INFO, "Switched to Edit mode");
	}

	private void playMode() {
		simPreview.mode = SimPreviewMode.PLAY_MODE;
		updateButtonLabelButton.setEnabled(false);
		recObj.btnUploadAudio.setEnabled(false);
		recObj.btnUploadImage.setEnabled(false);
		updateButtonLabelTextField.setEnabled(false);
		recObj.recordBtn.setEnabled(false);
		recObj.recordInfo.setText("Switch to edit mode to begin recording.");
		modeLbl.setText("Playback Mode");

		for (int i = 0; i < numOfButtons; i++) {
			resetPlayMode(simPreview.buttons.get(i));
		}

		simPreview.removeHighlight();
		logger.log(Level.INFO, "Switched to Play mode");
	}

	private void addUpdateCurrentButtonListener(AudioButton b) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBtn = b;
			}
		});
	}

	public void updateLabel() {
		if (currentBtn != null) {
			currentBtn.setText(updateButtonLabelTextField.getText());
			TalkBoxConfig.buttonsMap.put(currentBtn.buttonNumber - 1, currentBtn.getText());
		}
	}

	private void resetPlayMode(AudioButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}

}