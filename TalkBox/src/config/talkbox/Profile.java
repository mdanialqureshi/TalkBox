package config.talkbox;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {

	private static final long serialVersionUID = 5064938306897688957L;
	
	protected String profileName;
	protected ArrayList<String> audioFileNames;

	public Profile(String profileName) {
		this.profileName = profileName;
		audioFileNames = new ArrayList<String>();
	}

	public ArrayList<String> getAudioFileNames() {
		return audioFileNames;
	}
}
