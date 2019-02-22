package config.talkbox;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class ProfileList extends LinkedList<Profile> implements Serializable {

	private static final long serialVersionUID = -1677747622460303106L;
	private int currentProfile = 0;

	public ProfileList() {
		super();
	}

	public int getCurrentProfile() {
		return currentProfile;
	}

	public File getCurrentProfileFolder() {
		String profileFolder = String.format("/profile-%d", currentProfile + 1);
		return new File(TalkBoxConfig.talkBoxDataPath, profileFolder);
	}

	public void setCurrentProfile(int currentProfile) {
		this.currentProfile = currentProfile;
	}

	public ArrayList<String> getAudioFilesOfCurrentProfile() {
		return this.get(currentProfile).getAudioFileNames();
	}

	public String getAudioFileAtIndexOfCurrentProfile(int idx) {
		return this.get(currentProfile).getAudioFileNames().get(idx);
	}

	public void setAudioFileAtIndexOfCurrentProfile(int idx, String audioFile) {
		this.get(currentProfile).getAudioFileNames().set(idx, audioFile);
	}

	protected String[][] toArrayMatrix() {
		String[][] audioFileNames = new String[TalkBoxConfig.numAudSets][TalkBoxConfig.numAudButtons];
		for (int i = 0; i < TalkBoxConfig.numAudSets; ++i) {
			audioFileNames[i] = this.get(i).getAudioFileNames().toArray(new String[TalkBoxConfig.numAudButtons]);
		}
		return audioFileNames;
	}
}
