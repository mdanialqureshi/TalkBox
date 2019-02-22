package config.talkbox;

import java.io.Serializable;
import java.util.LinkedList;

public class ProfileList extends LinkedList<Profile> implements Serializable {

	private static final long serialVersionUID = -1677747622460303106L;

	public ProfileList() {
		super();
	}

	protected String[][] toArrayMatrix() {

		String[][] audioFileNames = new String[TalkBoxConfig.numAudSets][TalkBoxConfig.numAudButtons];
		for (int i = 0; i < TalkBoxConfig.numAudSets; ++i) {
			audioFileNames[i] = this.get(i).getAudioFileNames().toArray(new String[TalkBoxConfig.numAudButtons]);
		}
		return audioFileNames;
	}

}
