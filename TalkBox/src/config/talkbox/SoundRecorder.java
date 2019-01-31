package config.talkbox;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class SoundRecorder {
	// in milliseconds
	static final long RECORD_TIME = 60_000;

	File wavFile = new File("bin/TalkBoxData/audio/test1.wav");

	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	TargetDataLine line;

	AudioFormat getAudioFormat() {
		float sampleRate = 16_000;
		int sampleSizeInBits = 16;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	void start() throws LineUnavailableException {
		createFile();

		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

			// checks if system supports the data line
			if (!AudioSystem.isLineSupported(info)) {
				throw new LineUnavailableException();
			}
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();

			AudioInputStream ais = new AudioInputStream(line);

			System.out.println("Audio recording started...");

			// start recording
			AudioSystem.write(ais, fileType, wavFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		//adding audio file to TalkBoxConfig field
		TalkBoxConfig.audFileNames[0][0] = wavFile.getName();
	}

	private void createFile() {
		try {
			wavFile.getParentFile().mkdirs();
			wavFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	void finish() {
		if (line != null) {
			line.stop();
			line.close();
			System.out.println("Recording complete and saved.");

		}
	}

	
	
}