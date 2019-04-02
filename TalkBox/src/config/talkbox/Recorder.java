package config.talkbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import sim.talkbox.TalkBoxSim;

public class Recorder extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getGlobal();
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
	protected JFileChooser imageFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	String filePath;
	JButton updateNumberOfButtons;
	private SimPreview simPreview;
	boolean isCancelled = false;
	private JTextField fileLbl;
	protected PlayEditToggle toggle;
	protected JButton btnUploadAudio;
	protected JButton btnUploadImage;

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
				logger.log(Level.INFO, "Pressed Record Audio button");
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

		java.net.URL infoIconURL = Recorder.class.getResource("images/info-icon.png");
		infoIcon = new ImageIcon(infoIconURL);

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
		launchSimulator.setMargin(new Insets(0, 0, 0, 0));
		launchSimulator.setPreferredSize(new Dimension(100, 60));
		springLayout.putConstraint(SpringLayout.WEST, launchSimulator, 23, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, launchSimulator, -10, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, launchSimulator, 173, SpringLayout.WEST, this);
		add(launchSimulator);

		launchSimulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Launch Simulator button");
				if (!TalkBoxConfig.testmode) {
					String ObjButtons[] = { "Launch Simulator", "Cancel" };
					int PromptResult = JOptionPane.showOptionDialog(null,
							"Warning: Any unsaved changes will be saved and existing settings will be overwritten. Proceed?",
							"Exit warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
							ObjButtons[1]);

					if (PromptResult == JOptionPane.YES_OPTION) {
						saveSettings();
						String[] talkBoxSimArgs = { TalkBoxConfig.talkBoxDataPath.toString() };
						TalkBoxSim.main(talkBoxSimArgs);
					}
				} else {
					saveSettings();
					String[] talkBoxSimArgs = { TalkBoxConfig.talkBoxDataPath.toString() };
					TalkBoxSim.main(talkBoxSimArgs);
				}
			}
		});

		/*
		 * adding textfield to allow user to change the number of buttons
		 */
		txtNumberOfButtons = new JTextField("Number of buttons");
		springLayout.putConstraint(SpringLayout.NORTH, txtNumberOfButtons, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtNumberOfButtons, -238, SpringLayout.SOUTH, this);
		txtNumberOfButtons.setToolTipText("Input number of buttons and press Enter key to update");
		updateNumberOfButtons = new JButton("Update Buttons");
		updateNumberOfButtons.setMargin(new Insets(0, 0, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, updateNumberOfButtons, 70, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateNumberOfButtons, 0,
				SpringLayout.HORIZONTAL_CENTER, txtNumberOfButtons);
		updateNumberOfButtons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Update Number of Buttons button");
				updateButtons();
			}
		});
		add(updateNumberOfButtons);

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
				logger.log(Level.INFO, "Pressed Enter key with focus on Update Number of Buttons text field");
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
		saveSettings.setMargin(new Insets(0, 0, 0, 0));
		springLayout.putConstraint(SpringLayout.SOUTH, saveSettings, -6, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, saveSettings, 0, SpringLayout.EAST, progressBar);
		saveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Save Settings button");
				saveSettings();
			}
		});
		add(saveSettings);

		JLabel lblRecordToButtons = new JLabel("Record to buttons :");
		springLayout.putConstraint(SpringLayout.WEST, lblRecordToButtons, 0, SpringLayout.WEST, recordBtn);
		springLayout.putConstraint(SpringLayout.SOUTH, lblRecordToButtons, -6, SpringLayout.NORTH, recordBtn);
		add(lblRecordToButtons);

		btnUploadAudio = new JButton("Upload Audio");
		springLayout.putConstraint(SpringLayout.NORTH, btnUploadAudio, 6, SpringLayout.SOUTH, updateNumberOfButtons);
		springLayout.putConstraint(SpringLayout.WEST, btnUploadAudio, -136, SpringLayout.EAST, updateNumberOfButtons);
		springLayout.putConstraint(SpringLayout.EAST, btnUploadAudio, 0, SpringLayout.EAST, updateNumberOfButtons);
		btnUploadAudio.setEnabled(false);
		btnUploadAudio.setMargin(new Insets(0, 0, 0, 0));

		btnUploadAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Upload Audio button");
				try {
					audioJFileChooser();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		add(btnUploadAudio);

		btnUploadImage = new JButton("Upload Image");
		springLayout.putConstraint(SpringLayout.NORTH, btnUploadImage, 36, SpringLayout.SOUTH, updateNumberOfButtons);
		springLayout.putConstraint(SpringLayout.WEST, btnUploadImage, -136, SpringLayout.EAST, updateNumberOfButtons);
		springLayout.putConstraint(SpringLayout.EAST, btnUploadImage, 0, SpringLayout.EAST, updateNumberOfButtons);
		btnUploadImage.setEnabled(false);
		btnUploadImage.setMargin(new Insets(0, 0, 0, 0));
		btnUploadImage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				logger.log(Level.INFO, "Pressed Upload Image button");
				try {
					imageJFileChooser();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(btnUploadImage);

	}

	public void imageJFileChooser() throws IOException {
		imageFileChooser.setDialogTitle("Choose the image you wish to add to this button.");
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		imageFileChooser.setAcceptAllFileFilterUsed(false);
		imageFileChooser.setVisible(true);
		int returnValue = imageFileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File image = imageFileChooser.getSelectedFile();
			simPreview.setButtonImage(image);
		}
	}

	public void audioJFileChooser() throws IOException {
		fileChooser.setDialogTitle("Choose the audio file you wish to add to this button.");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setVisible(true);
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File audio = fileChooser.getSelectedFile();
			simPreview.setButtonAudio(audio);
		}
	}

	private void createFile(File file) {
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void saveSettings() {
		TalkBoxSerializer tbs = new TalkBoxSerializer(TalkBoxConfig.talkBoxDataPath);
		tbs.serialize();
		logger.log(Level.INFO, "Settings file saved");
	}

	protected void updateButtons() {
		try {
			TalkBoxConfig.numAudButtons = Integer.parseInt(txtNumberOfButtons.getText());
			simPreview.updateButtons(TalkBoxConfig.numAudButtons);
			logger.log(Level.INFO, "Number of buttons updated to {0}", new Object[] { TalkBoxConfig.numAudButtons });
		} catch (NumberFormatException nfe) {
			logger.log(Level.WARNING, "Invalid number format entered for button count update.");
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
							logger.log(Level.INFO, "Recording in progress");
							recordBtn.setIcon(micOn);
							recorder.start(simPreview.currentBtn);
						} catch (LineUnavailableException lue) {
							logger.log(Level.INFO, "Line not supported. Recording not started.");
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
