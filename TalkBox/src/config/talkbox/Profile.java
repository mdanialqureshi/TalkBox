package config.talkbox;

import java.util.ArrayList;

public class Profile {
	
	
	protected String profileName;
	protected ArrayList<String> audioFileNames;
	
	/**
	 * Default no parameter constructor
	 */
	public Profile(String profileName) {
		this.profileName = profileName;
		audioFileNames = new ArrayList<String>();
		
	}
	
	
	

}
