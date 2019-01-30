package config.talkbox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class Recorder extends JPanel {

	private static final long serialVersionUID = 1L;
	final private SoundRecorder recorder = new SoundRecorder();
	private boolean isRecording = false;
	private JButton recordBtn;
	private ImageIcon micOff;
	private ImageIcon micOn;
	private ImageIcon infoIcon;
	private JLabel recordInfo;

// Creating the Recorder sector of the TalkBox Configuration Application.
	public Recorder() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -34, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -69, SpringLayout.EAST, this);
		add(progressBar);

		recordBtn = new JButton();
		springLayout.putConstraint(SpringLayout.NORTH, recordBtn, -207, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.WEST, recordBtn, 365, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, recordBtn, -57, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, recordBtn, 515, SpringLayout.WEST, this);
		recordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordAudio();
			}
		});

		// getting image from its package and making a new ImageIcon
		micOff = new ImageIcon("images/mic-off-icon.png");
		micOn = new ImageIcon("images/mic-on-icon.png");

		recordBtn.setIcon(micOff); // setting button Icon to the image
		recordBtn.setForeground(Color.DARK_GRAY);
		add(recordBtn);

		infoIcon = new ImageIcon("images/info-icon.png");
		recordInfo = new JLabel("Begin recording?", SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.WEST, recordInfo, 345, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, recordInfo, 20, SpringLayout.EAST, recordBtn);
		recordInfo.setIcon(infoIcon);
		springLayout.putConstraint(SpringLayout.NORTH, recordInfo, 6, SpringLayout.SOUTH, recordBtn);
		springLayout.putConstraint(SpringLayout.SOUTH, recordInfo, 26, SpringLayout.SOUTH, recordBtn);
		add(recordInfo);
		
		PlayEditToggle toggle = new PlayEditToggle();
		springLayout.putConstraint(SpringLayout.NORTH, toggle, 34, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, toggle, 28, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, toggle, 103, SpringLayout.NORTH, recordBtn);
		springLayout.putConstraint(SpringLayout.EAST, toggle, 161, SpringLayout.WEST, this);
		add(toggle);
		
		
	}

	private void recordAudio() {
		if (isRecording) {
			recorder.finish();
			resetRecordBtn();
			isRecording = false;
		} else {
			Thread stopper = new Thread(new Runnable() {
				public void run() {
					try {
						isRecording = true;
						recordInfo.setText("Recording in progress.");
						recordBtn.setIcon(micOn);
						recorder.start();
					} catch (LineUnavailableException lue) {
						System.out.println("Line not supported. Recording not started.");
						recordBtn.setIcon(micOff);
						recordInfo.setText("Mic not detected");
						isRecording = false;
						recorder.finish();
					}
				}
			});
			stopper.start();
		}

	}

	private void resetRecordBtn() {
		recordBtn.setIcon(micOff);
		recordInfo.setText("Begin recording?");
	}
}
