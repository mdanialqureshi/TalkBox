package sim.talkbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import config.talkbox.TalkBoxSerializer;


public class TalkBoxDeserializer {
	
	public static int numAudButtons;
	public static int numAudSets, numSwapButtons;
	public static Path path;
	public static String[][] audFileNames;
	
	public static void main(String[] args) {	
	TalkBoxSerializer e = new TalkBoxSerializer();
	
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
	
	public static int setNumAudButtons() {
		TalkBoxSerializer e = new TalkBoxSerializer();
		try {
			FileInputStream fileIn = new FileInputStream("TalkBoxData.tbc");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (TalkBoxSerializer) in.readObject();
			in.close();
			fileIn.close();
		}
		
		catch (IOException i) {
			i.printStackTrace();
			return 0;
		}
		
		catch (ClassNotFoundException c) {
			System.out.println("TalkBoxConfig class not found");
			c.printStackTrace();
			return 0;
		}
		
		return e.getNumberOfAudioButtons();
	}
}
	