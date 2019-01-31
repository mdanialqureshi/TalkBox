package config.talkbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;

public class TalkBoxSerializer implements TalkBoxConfiguration, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	public int numAudioButtons;
	public int numAudioSets;
	public int numSwapButtons;
	public Path path;
	public String[][] audioFileNames;
	String talkBoxDataPath = "bin/TalkBoxData/";
	File talkBoxData = new File(talkBoxDataPath + "TalkBoxData.tbc");
	
	
	public TalkBoxSerializer() {
				
		numAudioButtons = TalkBoxConfig.numAudButtons;
		numAudioSets = TalkBoxConfig.numAudSets;
		numSwapButtons = TalkBoxConfig.numSwapButtons;
		path = TalkBoxConfig.path;
		audioFileNames = TalkBoxConfig.audFileNames;

		try {
			talkBoxData.getParentFile().mkdirs();
			talkBoxData.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(talkBoxData);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
	 	out.close();
	 	fileOut.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		
		System.out.println("TalkBox was serialized. Number of audio buttons is: " + this.numAudioButtons);
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
