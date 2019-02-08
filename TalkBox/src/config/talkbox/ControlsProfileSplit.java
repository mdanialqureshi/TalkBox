package config.talkbox;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ControlsProfileSplit extends JSplitPane {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControlsProfileSplit(int width, int height) { //receives width and height from talkBoxConfig dimensions.
		setDividerLocation((int) (0.74 * width));
		setOneTouchExpandable(true);
		setResizeWeight(1);
		JSplitPane simRecorderSplit = new SimRecorderSplit(height); //simRecorderSplit object is made which adds the recorder and simulator to this jSplitPane
		JPanel profile = new ProfilesPanel();
		setRightComponent(profile); //adds the profile panel to the right side of the jframe
		setLeftComponent(simRecorderSplit); //adds a JSplitPane to the left of the JFrame. This jSplitPane contains the talkbox simulator and recorder.

	}
}
