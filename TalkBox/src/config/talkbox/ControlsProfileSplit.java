package config.talkbox;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ControlsProfileSplit extends JSplitPane {
	
	
	
	public ControlsProfileSplit(int width, int height) { //receives width and height from talkBoxConfig dimensions.
		setDividerLocation((int) (0.74 * width));
		JSplitPane simRecorderSplit = new SimRecorderSplit(height); //simRecorderSplit object is made which adds the recorder and simulator to this jSplitPane
		JPanel profile = new JPanel();
		ProfilesPanel.setupProfiles(profile); //makes the profiles panel using static method call
		setRightComponent(profile); //adds the profile panel to the right side of the jframe
		setLeftComponent(simRecorderSplit); //adds a JSplitPane to the left of the JFrame. This jSplitPane contains the talkbox simulator and recorder.

	}
}
