package config.talkbox;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SimRecorderSplit extends JSplitPane {
	
	
	
	public SimRecorderSplit(int height) {
		setOrientation(JSplitPane.VERTICAL_SPLIT); //verticle split has to be set because default is horizontal
		setDividerLocation((int) (0.5 * height));
		JPanel simPreview = new SimPreview(); //creates talkBox simulator object
		JPanel recorder = new Recorder(); //creates a recorder object
		setTopComponent(simPreview); /* adds talkBox Simulator object to this JSplitPane. This JSplitPane is then set to
		the left side of another JSplitPane in ControlsProfileSplit class */
		setBottomComponent(recorder); //adds recording sector to the bottom of this JSplitPane.
	}
}

