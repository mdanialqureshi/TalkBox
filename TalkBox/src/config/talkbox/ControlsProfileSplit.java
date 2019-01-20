package config.talkbox;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ControlsProfileSplit extends JSplitPane {
	public ControlsProfileSplit(int width, int height) {
		setDividerLocation((int) (0.74 * width));

		JSplitPane simRecorderSplit = new SimRecorderSplit(height);
		JPanel profile = new JPanel();
		ProfilesPanel.setupProfiles(profile);
		setRightComponent(profile);
		setLeftComponent(simRecorderSplit);

	}
}
