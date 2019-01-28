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
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Creating the Recorder sector of the TalkBox Configuration Application. 
	public Recorder() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JProgressBar progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 70, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -34, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -69, SpringLayout.EAST, this);
		add(progressBar);
		
		JButton btnConfigureB = new JButton();
		springLayout.putConstraint(SpringLayout.NORTH, btnConfigureB, 105, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnConfigureB, 365, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnConfigureB, -58, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, btnConfigureB, 515, SpringLayout.WEST, this);
		btnConfigureB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// getting image from its package and making a new ImageIcon
		Image mic = new ImageIcon(this.getClass().getResource("/mic-icon.png")).getImage(); 

		btnConfigureB.setIcon(new ImageIcon(mic)); // setting button Icon to the image
		btnConfigureB.setForeground(Color.DARK_GRAY);
		add(btnConfigureB);

	}
}
