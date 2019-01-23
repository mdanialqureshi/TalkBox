package config.talkbox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TalkBoxSerializer implements TalkBoxConfiguration, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	public int numAudButtons;
	public int numAudSets;
	public int numSwapButtons;
	public Path path;
	public String[][] audFileNames;
	
	public static void main(String[] args) {
		TalkBoxSerializer e = new TalkBoxSerializer();
		
		e.numAudButtons = TalkBoxConfig.numAudButtons;
		e.numAudSets = TalkBoxConfig.numAudSets;
		e.numSwapButtons = TalkBoxConfig.numSwapButtons;
		e.path = TalkBoxConfig.path;
		e.audFileNames = TalkBoxConfig.audFileNames;

		try {
			FileOutputStream fileOut = new FileOutputStream("TalkBoxData.tbc");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
	 	out.close();
	 	fileOut.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		
		System.out.println("TalkBox was serialized. Number of audio buttons is: " + e.numAudButtons);
	}
	
	public int getNumberOfAudioButtons() {
		return TalkBoxConfig.numAudButtons;
	}
	
	public int getNumberOfAudioSets() {
		return TalkBoxConfig.numAudSets;
	}
	
	public int getTotalNumberOfButtons() {
		return TalkBoxConfig.numAudButtons + TalkBoxConfig.numSwapButtons;
	}
	
	public Path getRelativePathToAudioFiles() {
		return TalkBoxConfig.path;
	}
	
	public String[][] getAudioFileNames() {
		return TalkBoxConfig.audFileNames;
	}	
}
