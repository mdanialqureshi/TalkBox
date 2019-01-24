package sim.talkbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import config.talkbox.TalkBoxSerializer;


public class TalkBoxDeserializer {
	
	public int numAudButtons;
	public int numAudSets, numSwapButtons;
	public Path path;
	public String[][] audFileNames;
	TalkBoxSerializer e = new TalkBoxSerializer();

	
	public void setUpDeserializer() {	
	
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
		
		numAudButtons = e.getNumberOfAudioButtons();
		numAudSets = e.getNumberOfAudioSets();
		numSwapButtons = e.getTotalNumberOfButtons();
		path = e.getRelativePathToAudioFiles();
		audFileNames = e.getAudioFileNames();
		
		System.out.println("TalkBox was deserialized. Number of audio buttons is: " + e.numAudButtons);
		
	}
	
	public int getNumAudioButtons() {
		return e.getNumberOfAudioButtons();
	}
}
	