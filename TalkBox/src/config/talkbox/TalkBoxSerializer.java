package config.talkbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TalkBoxSerializer implements TalkBoxConfiguration, Serializable {

	private static final long serialVersionUID = 8857840839980976375L;
	private int numAudioButtons;
	private int numAudioSets;
	private int numSwapButtons;
	private String relativePathToAudioFiles;
	public String[][] audioFileNames;
	private File talkBoxDataPath;
	private File talkBoxData;
	private HashMap<Integer, String> buttonsMap;
	private ProfileList profilesList;

	public TalkBoxSerializer() {

		buttonsMap = TalkBoxConfig.buttonsMap;
		numAudioButtons = TalkBoxConfig.numAudButtons;
		numAudioSets = TalkBoxConfig.numAudSets;
		numSwapButtons = TalkBoxConfig.numSwapButtons;
		relativePathToAudioFiles = TalkBoxConfig.talkBoxDataPath.toString();

		profilesList = TalkBoxConfig.profilesList;
		audioFileNames = profilesList.toArrayMatrix();
	}

	public TalkBoxSerializer(File talkBoxDataPath) {
		this();

		this.talkBoxDataPath = talkBoxDataPath;
		this.talkBoxData = new File(talkBoxDataPath, "TalkBoxData.tbc");
	}

	public void init() {
		try {
			if (!talkBoxData.exists()) {
				talkBoxData.getParentFile().mkdirs();
				talkBoxData.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(talkBoxData);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(this);
				out.close();
				fileOut.close();
				System.out.println("TalkBox was serialized. Number of audio buttons is: " + this.numAudioButtons);
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream(talkBoxData);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("TalkBox was serialized. Number of audio buttons is: " + this.numAudioButtons);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public ProfileList getProfilesList() {
		return profilesList;
	}

	public HashMap<Integer, String> getButtonsMap() {
		return buttonsMap;
	}

	public int getNumberOfAudioButtons() {
		return numAudioButtons;
	}

	public int getNumberOfAudioSets() {
		return numAudioSets;
	}

	public int getTotalNumberOfButtons() {
		return numAudioButtons + numSwapButtons;
	}

	public Path getRelativePathToAudioFiles() {
		return Paths.get(relativePathToAudioFiles);
	}

	public String[][] getAudioFileNames() {
		return audioFileNames;
	}
}
