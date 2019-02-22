package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import sim.talkbox.TalkBoxSim;

public class Recorder extends JPanel {

	private static final long serialVersionUID = 1L;
	private SoundRecorder recorder = new SoundRecorder();
	boolean isRecording = false;
	JButton recordBtn;
	private ImageIcon micOff;
	private ImageIcon micOn;
	private ImageIcon infoIcon;
	JLabel recordInfo;
	JButton launchSimulator;
	JTextField txtNumberOfButtons;
	private SpringLayout springLayout;
	private JProgressBar progressBar;
	protected JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	String filePath;
	JButton updateNumberOfButtons;
	private SimPreview simPreview;
	boolean isCancelled = false;
	private JTextField fileLbl;
	protected PlayEditToggle toggle;

	// Creating the Recorder sector of the TalkBox Configuration Application.
	public Recorder(SimPreview simPreview) {
		this.simPreview = simPreview;

		springLayout = new SpringLayout();
		setLayout(springLayout);
		setMinimumSize(new Dimension(300, 200));

		progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -69, SpringLayout.EAST, this);
		add(progressBar);

		recordBtn = new JButton();
		springLayout.putConstraint(SpringLayout.NORTH, recordBtn, -207, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, recordBtn, 0, SpringLayout.HORIZONTAL_CENTER,
				progressBar);
		springLayout.putConstraint(SpringLayout.SOUTH, recordBtn, -57, SpringLayout.NORTH, progressBar);
		recordBtn.setEnabled(false);
		recordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordAudio();
			}
		});

		// getting image from its package and making a new ImageIcon
		java.net.URL micOffURL = Recorder.class.getResource("images/mic-off-icon.png");
		micOff = new ImageIcon(micOffURL);
		java.net.URL micOnURL = Recorder.class.getResource("images/mic-on-icon.png");
		micOn = new ImageIcon(micOnURL);
		recordBtn.setIcon(micOff); // setting button Icon to the image
		recordBtn.setForeground(Color.DARK_GRAY);
		add(recordBtn);

		infoIcon = new ImageIcon("images/info-icon.png");

		recordInfo = new JLabel("Switch to edit mode to begin recording.", SwingConstants.CENTER);
		recordInfo.setIcon(infoIcon);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, recordInfo, 0, SpringLayout.HORIZONTAL_CENTER,
				recordBtn);
		springLayout.putConstraint(SpringLayout.NORTH, recordInfo, 6, SpringLayout.SOUTH, recordBtn);
		springLayout.putConstraint(SpringLayout.SOUTH, recordInfo, 26, SpringLayout.SOUTH, recordBtn);
		add(recordInfo);

		/*
		 * launching the simulator from the configuration application
		 */
		launchSimulator = new JButton("Launch Simulator");
		launchSimulator.setPreferredSize(new Dimension(100, 60));
		springLayout.putConstraint(SpringLayout.WEST, launchSimulator, 23, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, launchSimulator, -10, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, launchSimulator, 173, SpringLayout.WEST, this);
		add(launchSimulator);

		launchSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] talkBoxSimArgs = {TalkBoxConfig.talkBoxDataPath.toString()};
				TalkBoxSim.main(talkBoxSimArgs);
			}
		});

		/*
		 * adding textfeild to allow user to change the number of buttons
		 */
		txtNumberOfButtons = new JTextField("Number of buttons");
		springLayout.putConstraint(SpringLayout.NORTH, txtNumberOfButtons, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtNumberOfButtons, -238, SpringLayout.SOUTH, this);
		txtNumberOfButtons.setToolTipText("Input number of buttons and press Enter key to update");
		updateNumberOfButtons = new JButton("Update Buttons");
		springLayout.putConstraint(SpringLayout.NORTH, updateNumberOfButtons, 70, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateNumberOfButtons, 0,
				SpringLayout.HORIZONTAL_CENTER, txtNumberOfButtons);
		add(updateNumberOfButtons);
		updateNumberOfButtons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButtons();
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, txtNumberOfButtons, -162, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, txtNumberOfButtons, -32, SpringLayout.EAST, this);
		add(txtNumberOfButtons);
		txtNumberOfButtons.setColumns(10);

		txtNumberOfButtons.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				txtNumberOfButtons.setText("");
			}

			public void focusLost(FocusEvent e) {
				if (txtNumberOfButtons.getText().isEmpty()) {
					txtNumberOfButtons.setText("Number of Buttons");
				}
			}
		});
		txtNumberOfButtons.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateButtons();
			}
		});

		toggle = new PlayEditToggle(simPreview, this);
		springLayout.putConstraint(SpringLayout.NORTH, toggle, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, toggle, 0, SpringLayout.WEST, launchSimulator);
		springLayout.putConstraint(SpringLayout.SOUTH, toggle, -70, SpringLayout.NORTH, launchSimulator);
		springLayout.putConstraint(SpringLayout.EAST, toggle, 18, SpringLayout.EAST, launchSimulator);
		add(toggle);

		JButton saveSettings = new JButton("Save Settings");
		springLayout.putConstraint(SpringLayout.SOUTH, saveSettings, -6, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, saveSettings, 0, SpringLayout.EAST, progressBar);
		saveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettings();
			}
		});
		add(saveSettings);

	}

	protected void saveSettings() {
		TalkBoxSerializer tbs = new TalkBoxSerializer(TalkBoxConfig.talkBoxDataPath);
		tbs.serialize();
	}

	protected void updateButtons() {
		try {
			TalkBoxConfig.numAudButtons = Integer.parseInt(txtNumberOfButtons.getText());
			simPreview.updateButtons(TalkBoxConfig.numAudButtons);
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid number format entered for button count update.");
		}
	}

	protected void recordAudio() {
		if (isRecording) {
			recorder.finish();
			resetRecordBtn();
			isRecording = false;
			// multiple recordings file name counter
		} else {
			if (!isCancelled) {
				Thread stopper = new Thread(new Runnable() {
					public void run() {
						try {
							isRecording = true;
							recordInfo.setText("Recording in progress.");
							recordBtn.setIcon(micOn);
							recorder.start(simPreview.currentBtn);
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

	}

	protected void resetRecordBtn() {
		recordBtn.setIcon(micOff);
		recordInfo.setText("Begin recording?");
	}
}
