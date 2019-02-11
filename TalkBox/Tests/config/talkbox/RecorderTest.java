package config.talkbox;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecorderTest {

	Recorder recObj;
	TalkBoxConfig configObj;
	
	@BeforeEach
	void setUp() throws Exception {
		recObj = new Recorder();
		configObj = new TalkBoxConfig();
	}

	@Test
	void testInitialFields() {
		assertEquals(false, recObj.isRecording);
		assertEquals("Begin recording?",recObj.recordInfo.getText());		
	}
	
	@Test
	void testRecording() throws InterruptedException {
		recObj.recordBtn.doClick();
		Thread.sleep(2000);
		recObj.recordBtn.doClick();
		String h = System.getProperty("user.home") + "/Desktop/test";
		assertEquals(h,Recorder.filePath);
		
	}
	
	@Test
	void testButtons() throws InterruptedException {
	//	Robot robot = new Robot();

//        // Simulate a mouse click
//        robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		Thread.sleep(3000);
//        robot.keyPress(KeyEvent.VK_ENTER);
//        Thread.sleep(1000);
//        robot.keyRelease(KeyEvent.VK_ENTER);
		recObj.txtNumberOfButtons.setText("30");
		recObj.updateNumberOfButtons.doClick();
		Thread.sleep(1000);
		recObj.launchSimulator.doClick();
		assertEquals(30,TalkBoxConfig.numAudButtons);
		
	}
	
	
	
	

}
