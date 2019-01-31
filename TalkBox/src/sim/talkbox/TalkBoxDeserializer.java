package sim.talkbox;

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
	TalkBoxSerializer e;

	
	public TalkBoxDeserializer() {
		
		numAudioButtons = e.getNumberOfAudioButtons();
		numAudioSets = e.getNumberOfAudioSets();
		numSwapButtons = e.getTotalNumberOfButtons();
		path = e.getRelativePathToAudioFiles();
		audioFileNames = e.getAudioFileNames();
	
		try {
			FileInputStream fileIn = new FileInputStream("TalkBoxData.tbc");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (TalkBoxSerializer) in.readObject();
			in.close();
			fileIn.close();
		}
		
		catch (IOException i) {
			i.printStackTrace();
			return;
		}
		
		catch (ClassNotFoundException c) {
			System.out.println("TalkBoxConfig class not found");
			c.printStackTrace();
			return;
		}
		
		System.out.println("TalkBox was deserialized. Number of audio buttons is: " + numAudioButtons);
		
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
	