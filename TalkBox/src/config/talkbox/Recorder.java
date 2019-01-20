package config.talkbox;

import java.awt.Button;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

public class Recorder extends JPanel {
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
	}
}
