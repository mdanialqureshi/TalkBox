package config.talkbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class PlayEditToggle extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JToggleButton toggleBtn;
	private static JLabel modeLbl;
	private static JTextField buttonLbl;
	private static int buttonNumber;
	private JButton currentBtn;
	static int numOfButtons = TalkBoxConfig.numAudButtons;

	/**
	 * 
	 */

	public PlayEditToggle() {
		JLabel modeLbl = new JLabel("Playback Mode");
		add(modeLbl);
		toggleBtn = new JToggleButton("Switch Modes");
		add(toggleBtn);
		buttonLbl = new JTextField("Button Label");
		add(buttonLbl);
		buttonLbl.setColumns(10);
		for (int i = 0; i < numOfButtons; i++) {
			JButton b = SimPreview.buttons.get(i);
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

	public static boolean isEditMode() {
		if (toggleBtn.isSelected() == true)
			return true;
		return false;
	}

	public void EditMode() {

		int numOfButtons = TalkBoxConfig.numAudButtons;

		for (int i = 0; i < numOfButtons; i++) {
			editLabel(SimPreview.buttons.get(i));
		}

	}

	public static void PlayMode() {

		for (int i = 0; i < numOfButtons; i++) {
			resetPlayMode(SimPreview.buttons.get(i));
		}
	}

	public void editLabel(JButton b) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentBtn = b;
			}
		});
	}

	public void updateLabel() {
		if (currentBtn != null) {
			currentBtn.setText(buttonLbl.getText());
		}

	}

	public static void resetPlayMode(JButton b) {
		ActionListener[] list = b.getActionListeners();
		b.removeActionListener(list[0]);
	}

}