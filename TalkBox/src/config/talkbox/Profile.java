package config.talkbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.Icon;

public class Profile implements Serializable {

	private static final long serialVersionUID = 5064938306897688957L;

	protected String profileName;
	protected ArrayList<String> audioFileNames;
	protected ArrayList<Icon> imageIcons;

	public Profile(String profileName) {
		this.profileName = profileName;
		audioFileNames = new ArrayList<String>(Collections.nCopies(TalkBoxConfig.numAudButtons, null));
		imageIcons = new ArrayList<Icon>(Collections.nCopies(TalkBoxConfig.numAudButtons, null));
		System.out.println("created Profile audio file count: " + audioFileNames.size());
	}

	public Profile(String profileName, Collection<String> audioFileNames, Collection<Icon> imageIcons) {
		this(profileName);
		this.audioFileNames.addAll(audioFileNames);
		this.imageIcons.addAll(imageIcons);
	}

	public void add(int idx, String audioFileName) {
		audioFileNames.add(idx, audioFileName);
	}
	
	public void addIcon(int idx, Icon imageIcon) {
		imageIcons.add(idx, imageIcon);
	}

	public ArrayList<String> getAudioFileNames() {
		return audioFileNames;
	}
	
	public ArrayList<Icon> getImageIcons() {
		return imageIcons;
	}
}
