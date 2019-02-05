package sim.talkbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;

import config.talkbox.TalkBoxSerializer;

public class TalkBoxDeserializer {

	public int numAudioButtons;
	public int numAudioSets, numSwapButtons;
	public Path path;
	public String[][] audioFileNames;
	TalkBoxSerializer config;
	File talkBoxDataPath;
	File talkBoxData;

	/*
	 * Default settings for simulator to load on startup
	 */
	public TalkBoxDeserializer() {
		config = new TalkBoxSerializer();
		numAudioButtons = config.getNumberOfAudioButtons();
		numAudioSets = config.getNumberOfAudioSets();
		numSwapButtons = config.getTotalNumberOfButtons();
		path = config.getRelativePathToAudioFiles();
		audioFileNames = config.getAudioFileNames();

		System.out.println("TalkBox was deserialized. Number of audio buttons is: " + numAudioButtons);
	}

	public TalkBoxDeserializer(File talkBoxDataPath) {
		this.talkBoxDataPath = talkBoxDataPath;
		this.talkBoxData = new File(talkBoxDataPath + "/TalkBoxData.tbc");

		try {
			FileInputStream fileIn = new FileInputStream(talkBoxData);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			config = (TalkBoxSerializer) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("TalkBoxConfig class not found");
			c.printStackTrace();
			return;
		}
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
		return path;
	}

	public String[][] getAudioFileNames() {
		return audioFileNames;
	}

}
