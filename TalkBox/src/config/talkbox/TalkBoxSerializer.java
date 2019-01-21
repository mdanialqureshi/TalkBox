package config.talkbox;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TalkBoxSerializer implements TalkBoxConfiguration {

	@Override
	public int getNumberOfAudioButtons() {
		return 12;
	}

	@Override
	public int getNumberOfAudioSets() {
		return 1;
	}

	@Override
	public int getTotalNumberOfButtons() {
		// 6 audio playback buttons + 1 audio set cycle button + 1 profile load button
		return 8;
	}

	@Override
	public Path getRelativePathToAudioFiles() {
		return Paths.get("TalkBoxData/audio");
	}

	@Override
	public String[][] getAudioFileNames() {
		return null;
	}

}
