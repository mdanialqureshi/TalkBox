package sim.talkbox;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.talkbox.TalkBoxConfig;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class TalkBoxSim extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TalkBoxSim frame = new TalkBoxSim();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		TalkBoxConfig g = new TalkBoxConfig();
		g.run();
	}

	/**
	 * Constructor calls buildGUI method which builds the frame and GUI components
	 * for TalkBoxSim
	 */
	public TalkBoxSim() {
		buildGUI();
	}

	/**
	 * GUI for TalkBox Simulator is created.
	 */
	private void buildGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("TalkBox");
		lblNewLabel.setBounds(506, 21, 177, 64);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Chalkboard", Font.PLAIN, 50));
		lblNewLabel.setForeground(Color.WHITE);

		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Button 1");
		btnNewButton.addActionListener(new ActionListener() {
			// button 1 has an actionListener which calls PlaySound Method and plays sound of the file. (When button is clicked)
			public void actionPerformed(ActionEvent e) {
				String helloFile = "hello.wav";
				playSound(helloFile);
			}

		});
		btnNewButton.setBounds(45, 112, 137, 41);
		btnNewButton.setFont(new Font("Chalkboard", Font.PLAIN, 25));

		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Button 2");
		btnNewButton_1.setBounds(194, 112, 142, 41);
		btnNewButton_1.setFont(new Font("Chalkboard", Font.PLAIN, 25));

		btnNewButton_1.addActionListener(new ActionListener() {
			// button 2 has an actionListener which calls PlaySound Method and plays sound of the file. (When button is clicked)
			public void actionPerformed(ActionEvent e) {
				playSound("button2test.wav"); // file name must be passed in as a String parameter.
			}
		});

		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Button 3");
		btnNewButton_2.setBounds(352, 112, 142, 41);
		btnNewButton_2.setFont(new Font("Chalkboard", Font.PLAIN, 25));
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Button 4");
		btnNewButton_3.setBounds(695, 112, 142, 41);
		btnNewButton_3.setFont(new Font("Chalkboard", Font.PLAIN, 25));
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Button 5");
		btnNewButton_4.setBounds(849, 112, 142, 41);
		btnNewButton_4.setFont(new Font("Chalkboard", Font.PLAIN, 25));
		contentPane.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Button 6");
		btnNewButton_5.setBounds(1003, 112, 142, 41);
		btnNewButton_5.setFont(new Font("Chalkboard", Font.PLAIN, 25));
		contentPane.add(btnNewButton_5);

		JButton btnConfigureB = new JButton();
		btnConfigureB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfigureB.setBounds(1016, 276, 129, 96);

		Image mic = new ImageIcon(this.getClass().getResource("/mic-icon.png")).getImage(); // getting image from its package and making a new ImageIcon
																							
		btnConfigureB.setIcon(new ImageIcon(mic)); // setting button Icon to the image
		btnConfigureB.setForeground(Color.DARK_GRAY);
		contentPane.add(btnConfigureB);

	}

	/**
	 * ActionListeners of the buttons call playSound() method which plays the sound
	 * of the button. The Argument being passed in is the name of the Audio file
	 * which the button will play.
	 * 
	 * @param soundName
	 *            name of audio file associated with the respective button
	 */

	public void playSound(String soundName) {
		try {
			File file = new File(this.getClass().getResource("/" + soundName).getFile()); // gets the file from its package using file name
																							 
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start(); // allows audio clip to be played
		} catch (Exception e) {
			System.err.println(e.getMessage()); // Respective error message is output onto the console
		}
	}
}
