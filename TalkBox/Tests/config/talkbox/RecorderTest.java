package config.talkbox;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecorderTest {

	Recorder recObj;
	
	@BeforeEach
	void setUp() throws Exception {
		recObj = new Recorder();
		
	}

	@Test
	void testInitialFields() {
		assertEquals(false, recObj.isRecording);
		assertEquals(null,Recorder.filePath);
		assertEquals("Begin recording?",recObj.recordInfo.getText());		
	}
	
	@Test
	void testButtons() throws InterruptedException {
		Thread.sleep(1000);
		recObj.launchSimulator.doClick();
		recObj.recordBtn.doClick();
		recObj.recordBtn.doClick();
		
		
	}
	
	
	
	
	

}
