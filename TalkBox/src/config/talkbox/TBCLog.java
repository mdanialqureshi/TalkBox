package config.talkbox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileSystemView;

public class TBCLog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	File[] logFiles;
	int currentLogFile = 0;
	private static final Dimension MINIMUM_SIZE = new Dimension(460, 300);
	String talkBoxDataPath;
	static JFileChooser fileChooser;

	public static void main(String[] args) {

		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Please choose the parent directory of the TalkBoxData directory.");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setVisible(true);
		// only show directories
		fileChooser.setAcceptAllFileFilterUsed(false);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File talkBoxDataParentDir = fileChooser.getSelectedFile();
			TBCLog logApp = new TBCLog((new File(talkBoxDataParentDir, "TalkBoxData").toString()));
			logApp.setVisible(true);
		} else if (returnValue == JFileChooser.CANCEL_OPTION) {
			System.exit(1);
		}

	}

	public TBCLog(String talkBoxDataPath) {

		this.talkBoxDataPath = talkBoxDataPath;
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.WEST, textArea, 42, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -53, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, -59, SpringLayout.EAST, getContentPane());
		getContentPane().add(textArea);

		textArea.setEditable(false);
		textArea.setLineWrap(true);

		JLabel lblTalkboxConfigurationLogs = new JLabel("TalkBox Configuration Logs");
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 6, SpringLayout.SOUTH, lblTalkboxConfigurationLogs);
		springLayout.putConstraint(SpringLayout.SOUTH, lblTalkboxConfigurationLogs, -247, SpringLayout.SOUTH,
				getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblTalkboxConfigurationLogs, 108, SpringLayout.WEST,
				getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblTalkboxConfigurationLogs, -117, SpringLayout.EAST,
				getContentPane());
		lblTalkboxConfigurationLogs.setFont(new Font("Chalkboard", Font.PLAIN, 16));
		getContentPane().add(lblTalkboxConfigurationLogs);

		readLogs();
		setMinimumSize(MINIMUM_SIZE);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnPreviousLog = new JButton("Previous Log");
		springLayout.putConstraint(SpringLayout.NORTH, btnPreviousLog, 6, SpringLayout.SOUTH, textArea);
		btnPreviousLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("prev");
			}
		});
		getContentPane().add(btnPreviousLog);

		JButton btnNextLog = new JButton("Next Log");
		springLayout.putConstraint(SpringLayout.NORTH, btnNextLog, 6, SpringLayout.SOUTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, btnNextLog, 182, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnPreviousLog, -21, SpringLayout.WEST, btnNextLog);
		btnNextLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logFiles != null && logFiles.length > 0)
					readCurrentLog("next");
			}
		});
		getContentPane().add(btnNextLog);

		JButton btnLoadLog = new JButton("Load Log");
		springLayout.putConstraint(SpringLayout.NORTH, btnLoadLog, 6, SpringLayout.SOUTH, textArea);
		springLayout.putConstraint(SpringLayout.WEST, btnLoadLog, 23, SpringLayout.EAST, btnNextLog);

		btnLoadLog.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fileChooser.setVisible(true);
				fileChooser.setCurrentDirectory(new File(talkBoxDataPath, "config-logs"));
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File loadedLog = fileChooser.getSelectedFile();
					loadLogFile(loadedLog);
				} else if (returnValue == JFileChooser.CANCEL_OPTION) {
					
				}
			}
		});

		getContentPane().add(btnLoadLog);

	}

	protected void loadLogFile(File loadedLog) {

		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(loadedLog)));
			textArea.read(input, "Reading Selected Log File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void readCurrentLog(String target) {
		if (logFiles.length > 0) {
			if (target.equals("prev") && currentLogFile < logFiles.length - 1) {
				currentLogFile++;
			} else if (target.equals("next") && currentLogFile > 0) {
				currentLogFile--;
			}
		}
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(new FileInputStream(logFiles[currentLogFile])));
			textArea.read(input, "Reading Log File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void readLogs() {
		File configLogs = new File(this.talkBoxDataPath, "config-logs");
		if (configLogs.isDirectory())
			logFiles = configLogs.listFiles();
		if (logFiles != null && logFiles.length > 0) {
			Arrays.sort(logFiles, Collections.reverseOrder());
			readCurrentLog("current");
		}

	}
}
