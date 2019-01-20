package config.talkbox;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import sim.talkbox.ButtonPanel;

public class SimRecorderSplit extends JSplitPane {
	public SimRecorderSplit(int height) {
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		setDividerLocation((int) (0.5 * height));
		JPanel simPreview = new ButtonPanel();
		JPanel recorder = new Recorder();
		setTopComponent(simPreview);
		setBottomComponent(recorder);
	}
}
