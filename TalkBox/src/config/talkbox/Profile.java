package config.talkbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Profile implements Serializable {

	private static final long serialVersionUID = 5064938306897688957L;

	protected String profileName;
	protected ArrayList<String> audioFileNames;

	public Profile(String profileName) {
		this.profileName = profileName;
		audioFileNames = new ArrayList<String>(Collections.nCopies(TalkBoxConfig.numAudButtons, null));
		System.out.println("created Profile audio file count: " + audioFileNames.size());
	}

	public Profile(String profileName, Collection<String> audioFileNames) {
		this(profileName);
		this.audioFileNames.addAll(audioFileNames);
	}

	public void add(int idx, String audioFileName) {
		audioFileNames.add(idx, audioFileName);
	}

	public ArrayList<String> getAudioFileNames() {
		return audioFileNames;
	}
}
