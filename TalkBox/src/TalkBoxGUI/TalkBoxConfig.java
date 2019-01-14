package TalkBoxGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class TalkBoxConfig extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public void run() {
		try {
			TalkBoxConfig frame = new TalkBoxConfig();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	/**
	 * Create the frame.
	 */
	public TalkBoxConfig() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnTalk = new JButton("Talk");
		contentPane.add(btnTalk);
	}

}
