package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.Icon;

import config.talkbox.ProfileList;
import config.talkbox.TalkBoxConfiguration;
import config.talkbox.TalkBoxSerializer;

public class TalkBoxDeserializer implements TalkBoxConfiguration {

	private int numAudioButtons;
	private int numAudioSets, numSwapButtons;
	private Path path;
	public String[][] audioFileNames;
	public Icon[][] imageIcons;
	private TalkBoxSerializer config;
	private File talkBoxDataPath;
	private File talkBoxData;
	private HashMap<Integer, String> buttonsMap;
	private HashMap<Integer, Icon> iconButtonsMap;
	private ProfileList profilesList;

	/*
	 * Default settings for simulator to load on startup
	 */
	public TalkBoxDeserializer() {
		config = new TalkBoxSerializer();
		buttonsMap = config.getButtonsMap();
		iconButtonsMap = config.getIconButtonsMap();
		profilesList = config.getProfilesList();
		numAudioButtons = config.getNumberOfAudioButtons();
		numAudioSets = config.getNumberOfAudioSets();
		numSwapButtons = config.getTotalNumberOfButtons();
		path = config.getRelativePathToAudioFiles();
		audioFileNames = config.getAudioFileNames();

		System.out.println("TalkBox was deserialized. Number of audio buttons is: " + numAudioButtons);
	}

	public TalkBoxDeserializer(File talkBoxDataPath) {
		this.talkBoxDataPath = talkBoxDataPath;
		this.talkBoxData = new File(talkBoxDataPath, "TalkBoxData.tbc");

		try {
			FileInputStream fileIn = new FileInputStream(talkBoxData);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			config = (TalkBoxSerializer) in.readObject();
			buttonsMap = config.getButtonsMap();
			iconButtonsMap = config.getIconButtonsMap();
			profilesList = config.getProfilesList();
			numAudioButtons = config.getNumberOfAudioButtons();
			numAudioSets = config.getNumberOfAudioSets();
			numSwapButtons = config.getTotalNumberOfButtons();
			path = config.getRelativePathToAudioFiles();
			audioFileNames = config.getAudioFileNames();

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

	public ProfileList getProfilesList() {
		return profilesList;
	}

	public HashMap<Integer, String> getButtonsMap() {
		return buttonsMap;
	}
	
	public HashMap<Integer, Icon> getIconButtonsMap() {
		return iconButtonsMap;
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
	
	public Icon[][] getImageIcons() {
		return imageIcons;
	}

}
