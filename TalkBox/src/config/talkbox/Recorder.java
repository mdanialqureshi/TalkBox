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
	private static final long serialVersionUID = 1L;
// Creating the Recorder sector of the TalkBox Configuration Application.
	public Recorder() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -34, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -69, SpringLayout.EAST, this);
		add(progressBar);
		
		JButton record = new JButton();
		springLayout.putConstraint(SpringLayout.NORTH, record, -207, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.WEST, record, 365, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, record, -57, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, record, 515, SpringLayout.WEST, this);
		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordAudio();
			}
		});

		// getting image from its package and making a new ImageIcon
		Image mic = new ImageIcon(this.getClass().getResource("/mic-icon.png")).getImage(); 

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
