package config.talkbox;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ProfileList extends LinkedList<Profile> implements Serializable {

	private static final long serialVersionUID = -1677747622460303106L;
	private int currentProfile = 0;
	private File talkBoxDataPath;

	public void setTalkBoxDataPath(File talkBoxDataPath) {
		this.talkBoxDataPath = talkBoxDataPath;
	}

	public ProfileList(File talkBoxDataPath) {
		super();
		this.talkBoxDataPath = talkBoxDataPath;
	}

	public int getCurrentProfile() {
		return currentProfile;
	}

	public File getCurrentProfileFolder() {
		String profileFolder = String.format("/profile-%d", currentProfile + 1);
		return new File(talkBoxDataPath, profileFolder);
	}

	public void setCurrentProfile(int currentProfile) {
		this.currentProfile = currentProfile;
	}

	public ArrayList<String> getAudioFilesOfCurrentProfile() {
		return this.get(currentProfile).getAudioFileNames();
	}
	
	public ArrayList<Icon> getImageIconsOfCurrentProfile() {
		return this.get(currentProfile).getImageIcons();
	}

	public String getAudioFileAtIndexOfCurrentProfile(int idx) {
		return getAudioFilesOfCurrentProfile().get(idx);
	}

	public void setAudioFileAtIndexOfCurrentProfile(int idx, String audioFile) {
		getAudioFilesOfCurrentProfile().set(idx, audioFile);
	}
	

	protected String[][] toArrayMatrix() {
		String[][] audioFileNames = new String[this.size()][TalkBoxConfig.numAudButtons];
		for (int i = 0; i < this.size(); ++i) {
			audioFileNames[i] = this.get(i).getAudioFileNames().toArray(new String[TalkBoxConfig.numAudButtons]);
		}
		return audioFileNames;
	}
	
}
