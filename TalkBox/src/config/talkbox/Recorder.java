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
	public JLabel recordInfo;
	public JButton launchSimulator;
	public JTextField txtNumberOfButtons;
	public SpringLayout springLayout;
	public JProgressBar progressBar;
	public static JFileChooser fileChooser;
	public static String filePath;
	JButton updateNumberOfButtons;

// Creating the Recorder sector of the TalkBox Configuration Application.
	public Recorder() {
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

				TalkBoxSim.main(new String[] {});

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
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateNumberOfButtons, 0,
				SpringLayout.HORIZONTAL_CENTER, txtNumberOfButtons);
		springLayout.putConstraint(SpringLayout.NORTH, updateNumberOfButtons, 60, SpringLayout.NORTH,
				txtNumberOfButtons);
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

		PlayEditToggle toggle = new PlayEditToggle();
		springLayout.putConstraint(SpringLayout.NORTH, toggle, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, toggle, 0, SpringLayout.WEST, launchSimulator);
		springLayout.putConstraint(SpringLayout.SOUTH, toggle, 38, SpringLayout.SOUTH, txtNumberOfButtons);
		springLayout.putConstraint(SpringLayout.EAST, toggle, 18, SpringLayout.EAST, launchSimulator);
		add(toggle);

	}

	protected void updateButtons() {
		try {
			TalkBoxConfig.numAudButtons = Integer.parseInt(txtNumberOfButtons.getText());
			SimRecorderSplit.updateSimPreview(TalkBoxConfig.numAudButtons);
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid number format entered for button count update.");
		}
	}

	public static void JFileChooserSave() {
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Choose a directory to save your file: ");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setVisible(true);

		int returnValue = fileChooser.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().toString();
			SoundRecorder.fileLocation = filePath;
			System.out.println(filePath);
		}

	}

	private void recordAudio() {
		if (isRecording) {
			recorder.finish();
			resetRecordBtn();
			isRecording = false;
			// multiple recordings file name counter
			SoundRecorder.counter++;
		} else {
			Recorder.JFileChooserSave();
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
