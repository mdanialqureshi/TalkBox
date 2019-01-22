package config.talkbox;

import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

public class Recorder extends JPanel {

	final private SoundRecorder recorder = new SoundRecorder();
	private boolean isRecording = false;

	// Creating the Recorder sector of the TalkBox Configuration Application.
	public Recorder() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -34, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -69, SpringLayout.EAST, this);
		add(progressBar);

		Button button = new Button("Record");
		springLayout.putConstraint(SpringLayout.WEST, button, 200, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, button, -33, SpringLayout.NORTH, progressBar);
		add(button);

		Button button_1 = new Button("Pause");
		springLayout.putConstraint(SpringLayout.WEST, button_1, 6, SpringLayout.EAST, button);
		springLayout.putConstraint(SpringLayout.SOUTH, button_1, 0, SpringLayout.SOUTH, button);
		add(button_1);

		JButton record = new JButton();
		springLayout.putConstraint(SpringLayout.SOUTH, record, -21, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, record, -44, SpringLayout.EAST, this);
		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordAudio();
			}
		});

		Image mic = new ImageIcon(this.getClass().getResource("/mic-icon.png")).getImage(); // getting image from its
																							// package and making a new
																							// ImageIcon

		record.setIcon(new ImageIcon(mic)); // setting button Icon to the image
		record.setForeground(Color.DARK_GRAY);
		add(record);
	}

	private void recordAudio() {
		if (isRecording) {
			recorder.finish();
			isRecording = false;
		} else {
			isRecording = true;
			Thread stopper = new Thread(new Runnable() {
				public void run() {
					recorder.start();
				}
			});
			stopper.start();
		}

	}

}
